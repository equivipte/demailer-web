package com.equivi.mailsy.service.worker;


import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import com.equivi.mailsy.service.campaign.queue.QueueCampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CampaignUpdateWorker {

    @Resource
    private QueueCampaignService queueCampaignService;

    @Resource
    private CampaignActivityService campaignActivityService;

    private static final Logger LOG = LoggerFactory.getLogger(CampaignUpdateWorker.class);

    //Run every 30 seconds
    @Scheduled(fixedDelay = 30000)
    public void collectCampaignQueueAndSendMail() {

        LOG.info("Collect Campaign Queue and send email");
        List<QueueCampaignMailerEntity> emailQueueToSend = queueCampaignService.getEmailQueueToSend();

        if (emailQueueToSend != null && !emailQueueToSend.isEmpty()) {
            campaignActivityService.sendEmail(emailQueueToSend);
        }
    }

    //Run every 2 minute
    @Scheduled(fixedDelay = 45000)
    public void updateTracker() {
        LOG.info("Campaign Update tracker");
        try {
            campaignActivityService.updateTracker();
        } catch (HibernateOptimisticLockingFailureException hex) {
            LOG.error("Stale Object State exception");
        }
    }
}
