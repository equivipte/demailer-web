package com.equivi.mailsy.dto.contact;


public class ContactDTO {

    private String contactGroupName;

    private String contactGroupStatus;

    private String lastUpdatedDate;

    public String getContactGroupName() {
        return contactGroupName;
    }

    public void setContactGroupName(String contactGroupName) {
        this.contactGroupName = contactGroupName;
    }

    public String getContactGroupStatus() {
        return contactGroupStatus;
    }

    public void setContactGroupStatus(String contactGroupStatus) {
        this.contactGroupStatus = contactGroupStatus;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
