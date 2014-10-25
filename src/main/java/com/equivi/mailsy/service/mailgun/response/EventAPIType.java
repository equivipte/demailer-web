package com.equivi.mailsy.service.mailgun.response;


public enum EventAPIType {

    ACCEPTED("accepted"),
    REJECTED("rejected"),
    DELIVERED("delivered"),
    FAILED("failed"),
    OPENED("opened"),
    CLICKED("clicked"),
    UNSUBSCRIBED("unsubscribed"),
    COMPLAINED("complained"),
    STORED("stored");


    private String eventApi;

    EventAPIType(String eventApi) {
        this.eventApi = eventApi;
    }

    public String getEventApiDescription() {
        return eventApi;
    }
}
