package com.equivi.mailsy.service.worker;


import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import com.equivi.mailsy.service.campaign.queue.QueueCampaignService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CampaignUpdateWorker {

    @Resource
    private QueueCampaignService queueCampaignService;

    @Resource
    private CampaignActivityService campaignActivityService;

    //Run every 30 seconds
    @Scheduled(fixedDelay = 30000)
    public void collectCampaignQueueAndSendMail() {

        List<QueueCampaignMailerEntity> emailQueueToSend = queueCampaignService.getEmailQueueToSend();

        if(emailQueueToSend!=null && !emailQueueToSend.isEmpty()){
            campaignActivityService.sendEmail(emailQueueToSend);
        }
    }

    //Run every minute
    @Scheduled(fixedDelay = 60000)
    public void updateTracker(){
         campaignActivityService.updateTracker();
    }
}
