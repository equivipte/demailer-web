package com.equivi.mailsy.dto.emailer;

import java.io.Serializable;


public class EmailVerifierResult implements Serializable {
    private static final long serialVersionUID = -6518779742111363067L;


    private String emailAddress;

    private String status;

    private String statusDescription;

    private String infoDetails;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EmailVerifierResult setEmailAddressResult(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public EmailVerifierResult setStatusResult(String status) {
        this.status = status;
        return this;
    }

    public EmailVerifierResult setInfoDetailResult(String infoDetailResult) {
        this.infoDetails = infoDetailResult;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfoDetails() {
        return infoDetails;
    }

    public void setInfoDetails(String infoDetails) {
        this.infoDetails = infoDetails;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
