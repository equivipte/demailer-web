package com.equivi.mailsy.service.mailgun.response;


import java.io.Serializable;

public class MailgunResponseMessage implements Serializable {

    private static final long serialVersionUID = -3395281815812131195L;

    private String id;

    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
