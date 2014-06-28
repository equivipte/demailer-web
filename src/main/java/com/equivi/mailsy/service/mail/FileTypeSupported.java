package com.equivi.mailsy.service.mail;


public enum FileTypeSupported {

    PDF("application/pdf");

    private String applicationFileName;

    FileTypeSupported(String fileName) {

        this.applicationFileName = fileName;
    }

    public String getApplicationFileName() {
        return applicationFileName;
    }
}
