package com.equivi.mailsy.service.campaign.tracker;


import com.equivi.mailsy.data.entity.CampaignTrackerEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

public interface CampaignTrackerService {


    @Async
    void createCampaignTracker(String externalMessageId, Long campaignId, String recipient);

    CampaignTrackerEntity getCampaignTrackerEntity(Long campaignTrackerId);

    void saveCampaignTrackerEntity(CampaignTrackerEntity campaignTrackerEntity);

    List<CampaignTrackerEntity> getCampaignTrackerEntityList(Map<CampaignTrackerSearchFilter, String> campaignTrackerSearchFilterStringMap);
}
