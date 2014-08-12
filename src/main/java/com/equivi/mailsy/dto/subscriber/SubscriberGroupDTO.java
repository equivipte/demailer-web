package com.equivi.mailsy.dto.subscriber;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

public class SubscriberGroupDTO implements Serializable {
    private static final long serialVersionUID = 4800669550227187925L;

    private Long id;

    @NotEmpty(message = "subscriber.group.name.empty")
    private String subscriberGroupName;

    private String subscriberGroupStatus;

    private String subscriberLastUpdateDate;

    private List<SubscriberDTO> subscriberList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriberGroupName() {
        return subscriberGroupName;
    }

    public void setSubscriberGroupName(String subscriberGroupName) {
        this.subscriberGroupName = subscriberGroupName;
    }

    public String getSubscriberGroupStatus() {
        return subscriberGroupStatus;
    }

    public void setSubscriberGroupStatus(String subscriberGroupStatus) {
        this.subscriberGroupStatus = subscriberGroupStatus;
    }

    public String getSubscriberLastUpdateDate() {
        return subscriberLastUpdateDate;
    }

    public void setSubscriberLastUpdateDate(String subscriberLastUpdateDate) {
        this.subscriberLastUpdateDate = subscriberLastUpdateDate;
    }

    public List<SubscriberDTO> getSubscriberList() {
        return subscriberList;
    }

    public void setSubscriberList(List<SubscriberDTO> subscriberList) {
        this.subscriberList = subscriberList;
    }
}
