package com.equivi.mailsy.dto.campaign;


import java.io.Serializable;

public class CampaignDTO implements Serializable {
    private static final long serialVersionUID = -1517953677545275306L;

    private Long id;

    private String emaiSubject;

    private String emailContent;

    private String campaignStatus;

    private String scheduledSendDate;

    private String lastUpdateDate;

    private Long subscriberGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmaiSubject() {
        return emaiSubject;
    }

    public void setEmaiSubject(String emaiSubject) {
        this.emaiSubject = emaiSubject;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getScheduledSendDate() {
        return scheduledSendDate;
    }

    public void setScheduledSendDate(String scheduledSendDate) {
        this.scheduledSendDate = scheduledSendDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getSubscriberGroupId() {
        return subscriberGroupId;
    }

    public void setSubscriberGroupId(Long subscriberGroupId) {
        this.subscriberGroupId = subscriberGroupId;
    }

    public String getCampaignStatus() {
        return campaignStatus;
    }

    public void setCampaignStatus(String campaignStatus) {
        this.campaignStatus = campaignStatus;
    }
}
