package com.equivi.mailsy.service.subsriber;


public enum SubscriberGroupSearchFilter {

    SUBSCRIBER_GROUP_NAME("subscriberGroupName"),
    EMAIL_ADDRESS("emailAddress");


    private String filterName;

    SubscriberGroupSearchFilter(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

}
