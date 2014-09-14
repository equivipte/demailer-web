package com.equivi.mailsy.dto.campaign;


import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;


public class CampaignDTO implements Serializable {
    private static final long serialVersionUID = -1517953677545275306L;

    private Long id;

    @NotEmpty(message = "campaign.name.empty")
    private String campaignName;

    @NotEmpty(message = "campaign.emailsubject.empty")
    private String emailSubject;

    private String emailContent;

    private String campaignStatus;

    private String scheduledSendDateTime;

    private String scheduledSendDate;

    private String scheduledSendTime;


    private String lastUpdateDate;

    private List<Long> subscriberGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
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

    public List<Long> getSubscriberGroupIds() {
        return subscriberGroupId;
    }

    public void setSubscriberGroupIds(List<Long> subscriberGroupIds) {
        this.subscriberGroupId = subscriberGroupIds;
    }

    public String getCampaignStatus() {
        return campaignStatus;
    }

    public void setCampaignStatus(String campaignStatus) {
        this.campaignStatus = campaignStatus;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }


    public String getScheduledSendDateTime() {
        return scheduledSendDateTime;
    }

    public void setScheduledSendDateTime(String scheduledSendDateTime) {
        this.scheduledSendDateTime = scheduledSendDateTime;
    }

    public String getScheduledSendTime() {
        return scheduledSendTime;
    }

    public void setScheduledSendTime(String scheduledSendTime) {
        this.scheduledSendTime = scheduledSendTime;
    }

    public List<Long> getSubscriberGroupId() {
        return subscriberGroupId;
    }

    public void setSubscriberGroupId(List<Long> subscriberGroupId) {
        this.subscriberGroupId = subscriberGroupId;
    }
}
