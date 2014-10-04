package com.equivi.mailsy.service.mailgun;


public enum MailgunParameters {

    FROM("from"),
    TO("to"),
    CC("cc"),
    BCC("bcc"),
    SUBJECT("subject"),
    TEXT("text"),
    HTML("html"),
    ATTACHMENT("attachment"),
    CAMPAIGN_ID("o:campaign"),
    DELIVERY_TIME("o:deliverytime"),
    TRACKING("o:tracking"),
    TRACKING_CLICKS("o:tracking-clicks"),
    TRACKING_OPEN("o:tracking-opens"),
    MESSAGE_ID("message-id"),
    EVENT("event"),
    ADDRESS("address"),
    TAG("tag");

    private String value;

    MailgunParameters(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
