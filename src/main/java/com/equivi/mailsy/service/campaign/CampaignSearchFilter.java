package com.equivi.mailsy.service.campaign;


public enum CampaignSearchFilter {

    CAMPAIGN_STATUS("campaignStatus"),
    CAMPAIGN_SUBJECT("campaignSubject"),
    CAMPAIGN_NAME("campaignName"),
    CAMPAIGN_SCHEDULED_SEND_DATE("scheduledSendDate"),
    CAMPAIGN_DATE("campaignDate");


    private String filterName;

    CampaignSearchFilter(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

}
