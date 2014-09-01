package com.equivi.mailsy.service.campaign;


public enum CampaignSearchFilter {

    CAMPAIGN_SUBJECT("campaignSubject"),
    CAMPAIGN_DATE("campaignDate");


    private String filterName;

    CampaignSearchFilter(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

}
