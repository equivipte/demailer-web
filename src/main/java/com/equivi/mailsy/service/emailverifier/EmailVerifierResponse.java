package com.equivi.mailsy.service.emailverifier;


import org.codehaus.jackson.annotate.JsonProperty;

public class EmailVerifierResponse {

    @JsonProperty("Address")
    private String address;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("StatusCode")
    private String statusCode;

    public EmailVerifierResponse() {}

    public EmailVerifierResponse(String address, String status, String statusCode) {
        this.address = address;
        this.status = status;
        this.statusCode = statusCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
