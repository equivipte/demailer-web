package com.equivi.mailsy.service.worker;


import com.equivi.mailsy.data.entity.QueueCampaignMailerEntity;

import java.util.List;

public interface CampaignActivityService {


    void sendEmail(List<QueueCampaignMailerEntity> queueCampaignMailerEntityList);

    void updateTracker();
}
