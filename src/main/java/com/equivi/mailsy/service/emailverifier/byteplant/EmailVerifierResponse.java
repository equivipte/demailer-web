package com.equivi.mailsy.service.emailverifier.byteplant;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.stereotype.Component;

@Component
public class EmailVerifierResponse {

    @JsonIgnore
    private String emailAddress;

    private int status;

    private String info;

    private String details;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
