package com.equivi.mailsy.dto.emailer;

import java.io.Serializable;


public class EmailVerifier implements Serializable {
    private static final long serialVersionUID = -6518779742111363067L;


    private String emailAddress;

    private String status;

    private String statusDescription;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
