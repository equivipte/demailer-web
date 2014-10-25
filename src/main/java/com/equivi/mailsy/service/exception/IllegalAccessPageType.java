package com.equivi.mailsy.service.exception;


public enum IllegalAccessPageType {

    MERCHANT_JOB_IS_RUNNING("Merchant Job is Running,unable to access this page");


    private String keyName;

    IllegalAccessPageType(String keyName) {

        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
