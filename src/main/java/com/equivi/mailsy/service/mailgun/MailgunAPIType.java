package com.equivi.mailsy.service.mailgun;


public enum MailgunAPIType {

    MESSAGES("messages"),
    UNSUBSCRIBES("unsubscribes"),
    CAMPAIGNS("campaigns"),
    BOUNCES("bounces"),
    EVENTS("events"),
    COMPLAINTS("complaints");

    private String value;

    MailgunAPIType(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
