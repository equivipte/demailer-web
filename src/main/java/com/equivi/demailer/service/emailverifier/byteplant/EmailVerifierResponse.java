package com.equivi.demailer.service.emailverifier.byteplant;


import org.springframework.stereotype.Component;

@Component
public class EmailVerifierResponse {

    private int status;

    private String info;

    private String details;

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
