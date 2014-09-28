package com.equivi.mailsy.service.mailgun.response;


import java.io.Serializable;
import java.util.List;

public class MailgunResponseEventMessage implements Serializable {

    private static final long serialVersionUID = 981175730103294918L;

    private List<MailgunResponseItem> items;

    public List<MailgunResponseItem> getItems() {
        return items;
    }

    public void setItems(List<MailgunResponseItem> items) {
        this.items = items;
    }

    public boolean hasEventType(EventAPIType eventAPIType) {
        if (items != null && !items.isEmpty()) {
            for (MailgunResponseItem mailgunResponseItem : items) {
                if (mailgunResponseItem.isEventType(eventAPIType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public MailgunResponseItem getResponseItem(EventAPIType eventAPIType) {
        if (items != null && !items.isEmpty()) {
            for (MailgunResponseItem mailgunResponseItem : items) {
                if (mailgunResponseItem.isEventType(eventAPIType)) {
                    return mailgunResponseItem;
                }
            }
        }
        return null;
    }
}
