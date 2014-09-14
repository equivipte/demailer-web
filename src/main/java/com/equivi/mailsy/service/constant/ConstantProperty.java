package com.equivi.mailsy.service.constant;


public enum ConstantProperty {

    DATE_TIME_FORMAT("dd-MM-YYYY HH:mm"),
    DATE_FORMAT("dd-MM-yyyy"),
    TIME_FORMAT("HH:mm");

    private String value;

    private ConstantProperty(String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
