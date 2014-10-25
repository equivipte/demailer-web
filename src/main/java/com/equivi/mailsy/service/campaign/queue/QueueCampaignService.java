package com.equivi.mailsy.service.campaign.queue;


import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface QueueCampaignService {

    /**
     * Send campaign subscriber list to queue
     * @param campaignSubscriberGroupEntityList
     */
    @Async
    void sendCampaignToQueueMailer(List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntityList);


    /**
     *
     * @return QueueCampaignMailerEntity where MailDeliveryStatus != SUCCESS and scheduled date before or equals NOW()
     */
    List<QueueCampaignMailerEntity> getEmailQueueToSend();
}
