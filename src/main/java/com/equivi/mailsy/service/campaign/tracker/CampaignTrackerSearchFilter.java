package com.equivi.mailsy.service.campaign.tracker;


public enum CampaignTrackerSearchFilter {

    CAMPAIGN_ID("campaignId"),
    CAMPAIGN_MAILER_MESSAGE_ID("campaignMailerMessageId"),
    RECIPIENT("recipient");


    private String filterName;

    CampaignTrackerSearchFilter(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

}
