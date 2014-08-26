package com.equivi.mailsy.service.constant;


import java.util.ArrayList;
import java.util.List;

public enum HeaderImportSubscribe {


    EMAIL_ADDRESS("Email Address"),
    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    COMPANY_NAME("Company Name"),
    ADDRESS1("Address 1"),
    ADDRESS2("Address 2"),
    ADDRESS3("Address 3"),
    COUNTRY("Country"),
    CITY("City"),
    PHONE("Phone"),
    ZIP_CODE("Zip Code");

    private String headerName;

    HeaderImportSubscribe(final String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public static List<String> getHeaderNames() {

        List<String> headerNames = new ArrayList<>();
        for (HeaderImportSubscribe headerImportSubscribe : HeaderImportSubscribe.values()) {
            headerNames.add(headerImportSubscribe.getHeaderName());
        }

        return headerNames;
    }
}
