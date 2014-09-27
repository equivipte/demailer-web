package com.equivi.mailsy.service.campaign.tracker;


import org.springframework.scheduling.annotation.Async;

public interface CampaignTrackerService {


    @Async
    void createCampaignTracker(String externalMessageId, Long campaignId, String recipient);
}
