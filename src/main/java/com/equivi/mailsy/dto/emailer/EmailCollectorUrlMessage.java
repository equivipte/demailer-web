package com.equivi.mailsy.dto.emailer;

import java.util.Objects;

/**
 * Created by tsagita on 26/7/14.
 */
public class EmailCollectorUrlMessage {
    private String url;

    public EmailCollectorUrlMessage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int hashCode() {
        return Objects.hash(this.url);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        final EmailCollectorUrlMessage other = (EmailCollectorUrlMessage) obj;
        return Objects.equals(this.url, other.url);
    }
}
