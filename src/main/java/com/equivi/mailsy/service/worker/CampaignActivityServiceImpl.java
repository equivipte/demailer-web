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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
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

    private static final Logger LOG = LoggerFactory.getLogger(CampaignActivityServiceImpl.class);

    @Override
    public void sendEmail(List<QueueCampaignMailerEntity> queueCampaignMailerEntityList) {

        LOG.error("SIZE:" + queueCampaignMailerEntityList.size());
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

        LOG.info("Campaign Update Tracker");
        List<CampaignTrackerEntity> campaignTrackerEntityList = campaignTrackerService.getCampaignTrackerEntityList(null);

        if (campaignTrackerEntityList != null && !campaignTrackerEntityList.isEmpty()) {
            for (CampaignTrackerEntity campaignTrackerEntity : campaignTrackerEntityList) {

                //Refresh object from DB
                try {
                    CampaignTrackerEntity campaignTrackerEntityFromDB = campaignTrackerService.getCampaignTrackerEntity(campaignTrackerEntity.getId());

                    campaignTrackerEntityFromDB = updateCampaignTrackerEntityPerEvent(campaignTrackerEntityFromDB);

                    campaignTrackerService.saveCampaignTrackerEntity(campaignTrackerEntityFromDB);

                } catch (HibernateOptimisticLockingFailureException hex) {
                    LOG.error("Stale object exception");
                }
            }
        }

    }

    CampaignTrackerEntity updateCampaignTrackerEntityPerEvent(CampaignTrackerEntity campaignTrackerEntity) {
        MailgunResponseEventMessage mailgunResponseEventMessage = mailgunService.getEventForMessageId(campaignTrackerEntity.getCampaignMailerMessageId());

        //Update event for delivered
        if (mailgunResponseEventMessage != null) {
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
                campaignTrackerEntity.setGeoLocationCity(mailgunResponseOpenItem.getGeoLocation().getCity());
                campaignTrackerEntity.setGeoLocationCountry(mailgunResponseOpenItem.getGeoLocation().getCountry());
                campaignTrackerEntity.setGeoLocationRegion(mailgunResponseOpenItem.getGeoLocation().getRegion());

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
        try {
            QueueCampaignMailerEntity queueCampaignMailUpdated = queueCampaignMailerDao.findOne(queueCampaignMailerEntity.getId());
            queueCampaignMailUpdated.setMailDeliveryStatus(MailDeliveryStatus.SUCCESS);
            queueCampaignMailerDao.save(queueCampaignMailUpdated);
        } catch (HibernateOptimisticLockingFailureException hibernateOptimisticLockExc) {
            LOG.error("Stale object exception going it's fine");

        }
    }
}
