package com.equivi.mailsy.service.worker;


import com.equivi.mailsy.data.dao.QueueCampaignMailerDao;
import com.equivi.mailsy.data.entity.MailDeliveryStatus;
import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import com.equivi.mailsy.service.campaign.tracker.CampaignTrackerService;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CampaignMailSenderToMailgun implements CampaignMailSender {

    @Resource
    private MailgunService mailgunService;

    @Resource
    private CampaignTrackerService campaignTrackerService;

    @Resource
    private QueueCampaignMailerDao queueCampaignMailerDao;

    private static final String DOMAIN = "sandbox80dd6c12cf4c4f99bdfa256bfea7cfeb.mailgun.org";

    @Override
    public void sendEmail(List<QueueCampaignMailerEntity> queueCampaignMailerEntityList) {

        for (QueueCampaignMailerEntity queueCampaignMailerEntity : queueCampaignMailerEntityList) {
            String messageId = mailgunService.sendMessage(queueCampaignMailerEntity.getCampaignId().toString(), DOMAIN, queueCampaignMailerEntity.getEmailFrom(), Lists.newArrayList(queueCampaignMailerEntity.getRecipient()), null, null, queueCampaignMailerEntity.getSubject(), queueCampaignMailerEntity.getContent());

            if (!StringUtils.isEmpty(messageId)) {

                //Update mail delivery status
                updateQueueCampaignStatus(queueCampaignMailerEntity);

                campaignTrackerService.createCampaignTracker(messageId, queueCampaignMailerEntity.getCampaignId(), queueCampaignMailerEntity.getRecipient());
            }
        }
    }

    private void updateQueueCampaignStatus(QueueCampaignMailerEntity queueCampaignMailerEntity) {
        QueueCampaignMailerEntity queueCampaignMailUpdated = queueCampaignMailerDao.findOne(queueCampaignMailerEntity.getId());
        queueCampaignMailUpdated.setMailDeliveryStatus(MailDeliveryStatus.SUCCESS);
        queueCampaignMailerDao.save(queueCampaignMailUpdated);
    }
}
