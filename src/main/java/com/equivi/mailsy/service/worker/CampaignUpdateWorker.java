package com.equivi.mailsy.service.worker;


import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import com.equivi.mailsy.service.campaign.queue.QueueCampaignService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CampaignUpdateWorker {

    @Resource
    private QueueCampaignService queueCampaignService;

    @Resource
    private CampaignMailSender campaignMailSender;

    //Run every 15 seconds
    @Scheduled(fixedDelay = 15000)
    public void collectCampaignQueueAndSendMail() {

        List<QueueCampaignMailerEntity> emailQueueToSend = queueCampaignService.getEmailQueueToSend();

        if(emailQueueToSend!=null && !emailQueueToSend.isEmpty()){
            campaignMailSender.sendEmail(emailQueueToSend);
        }
    }
}
