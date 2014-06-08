package com.equivi.demailer.dto.emailer;


import java.io.Serializable;

public class EmailCollector implements Serializable {
    private static final long serialVersionUID = 3303844035621083559L;


    private String emailAddress;

    private String site;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
