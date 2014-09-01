package com.equivi.mailsy.service.constant;


public enum ConstantProperty {

    DATE_TIME_FORMAT("dd/MM/YY HH:mm:ss");

    private String value;

    private ConstantProperty(String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
