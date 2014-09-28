package com.equivi.mailsy.service.worker;


import com.equivi.mailsy.data.dao.QueueCampaignMailerDao;
import com.equivi.mailsy.data.entity.CampaignTrackerEntity;
import com.equivi.mailsy.data.entity.MailDeliveryStatus;
import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import com.equivi.mailsy.service.campaign.tracker.CampaignTrackerService;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.equivi.mailsy.service.mailgun.response.EventAPIType;
import com.equivi.mailsy.service.mailgun.response.MailgunResponseEventMessage;
import com.equivi.mailsy.service.mailgun.response.MailgunResponseItem;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CampaignActivityServiceImpl implements CampaignActivityService {

    @Resource
    private MailgunService mailgunService;

    @Resource
    private CampaignTrackerService campaignTrackerService;

    @Resource
    private QueueCampaignMailerDao queueCampaignMailerDao;

    @Override
    public void sendEmail(List<QueueCampaignMailerEntity> queueCampaignMailerEntityList) {

        for (QueueCampaignMailerEntity queueCampaignMailerEntity : queueCampaignMailerEntityList) {
            String messageId = mailgunService.sendMessage(queueCampaignMailerEntity.getCampaignId().toString(), null, queueCampaignMailerEntity.getEmailFrom(), Lists.newArrayList(queueCampaignMailerEntity.getRecipient()), null, null, queueCampaignMailerEntity.getSubject(), queueCampaignMailerEntity.getContent());

            if (!StringUtils.isEmpty(messageId)) {

                //Update mail delivery status
                updateQueueCampaignStatus(queueCampaignMailerEntity);

                campaignTrackerService.createCampaignTracker(messageId, queueCampaignMailerEntity.getCampaignId(), queueCampaignMailerEntity.getRecipient());
            }
        }
    }

    @Override
    public void updateTracker() {

        List<CampaignTrackerEntity> campaignTrackerEntityList = campaignTrackerService.getCampaignTrackerEntityList(null);

        if (campaignTrackerEntityList != null && !campaignTrackerEntityList.isEmpty()) {
            for (CampaignTrackerEntity campaignTrackerEntity : campaignTrackerEntityList) {

                //Refresh object from DB
                CampaignTrackerEntity campaignTrackerEntityFromDB = campaignTrackerService.getCampaignTrackerEntity(campaignTrackerEntity.getId());

                campaignTrackerEntityFromDB = updateCampaignTrackerEntityPerEvent(campaignTrackerEntityFromDB);

                campaignTrackerService.saveCampaignTrackerEntity(campaignTrackerEntityFromDB);
            }
        }

    }

    CampaignTrackerEntity updateCampaignTrackerEntityPerEvent(CampaignTrackerEntity campaignTrackerEntity) {
        MailgunResponseEventMessage mailgunResponseEventMessage = mailgunService.getEventForMessageId(campaignTrackerEntity.getCampaignMailerMessageId());

        //Update event for delivered
        MailgunResponseItem mailgunResponseDeliverItem = mailgunResponseEventMessage.getResponseItem(EventAPIType.DELIVERED);
        if (mailgunResponseDeliverItem != null) {
            campaignTrackerEntity.setDelivered(true);

            campaignTrackerEntity.setDeliverDate(new Date(formatTimestamp(mailgunResponseDeliverItem.getTimestamp())));
        }


        //Update event for opened
        MailgunResponseItem mailgunResponseOpenItem = mailgunResponseEventMessage.getResponseItem(EventAPIType.OPENED);
        if (mailgunResponseOpenItem != null) {

            campaignTrackerEntity.setOpened(true);
            campaignTrackerEntity.setClientUserAgent(mailgunResponseOpenItem.getClientInfo().getUserAgent());
            campaignTrackerEntity.setClientType(mailgunResponseOpenItem.getClientInfo().getClientType());
            campaignTrackerEntity.setClientDeviceName(mailgunResponseOpenItem.getClientInfo().getClientName());
            campaignTrackerEntity.setClientOs(mailgunResponseOpenItem.getClientInfo().getClientOs());
            campaignTrackerEntity.setClientDeviceType(mailgunResponseOpenItem.getClientInfo().getDeviceType());

            campaignTrackerEntity.setOpenDate(new Date(formatTimestamp(mailgunResponseOpenItem.getTimestamp())));
        }

        //Update event for failed
        MailgunResponseItem mailgunResponseFailedItem = mailgunResponseEventMessage.getResponseItem(EventAPIType.FAILED);

        if (mailgunResponseFailedItem != null) {
            campaignTrackerEntity.setBounced(true);

            campaignTrackerEntity.setBouncedDate(new Date(formatTimestamp(mailgunResponseFailedItem.getTimestamp())));
        }

        //Update event for clicked
        MailgunResponseItem mailgunResponseClickedItem = mailgunResponseEventMessage.getResponseItem(EventAPIType.CLICKED);
        if (mailgunResponseClickedItem != null) {
            campaignTrackerEntity.setClicked(true);
            campaignTrackerEntity.setClickedDate(new Date(formatTimestamp(mailgunResponseClickedItem.getTimestamp())));
        }

        return campaignTrackerEntity;
    }

    Long formatTimestamp(String mailgunTimestamp) {

        if (!StringUtils.isEmpty(mailgunTimestamp)) {
            mailgunTimestamp = StringUtils.replace(mailgunTimestamp, ".", "");

            StringBuilder sbTimestamp = new StringBuilder(mailgunTimestamp);
            sbTimestamp.insert(mailgunTimestamp.length() - 3, ".");

            Long millis = Double.valueOf(sbTimestamp.toString()).longValue();

            return millis;
        }

        return 0L;
    }

    private void updateQueueCampaignStatus(QueueCampaignMailerEntity queueCampaignMailerEntity) {
        QueueCampaignMailerEntity queueCampaignMailUpdated = queueCampaignMailerDao.findOne(queueCampaignMailerEntity.getId());
        queueCampaignMailUpdated.setMailDeliveryStatus(MailDeliveryStatus.SUCCESS);
        queueCampaignMailerDao.save(queueCampaignMailUpdated);
    }
}
