package com.equivi.mailsy.dto.quota;

import java.io.Serializable;

public class QuotaDTO implements Serializable {
    private static final long serialVersionUID = 77453603352814971L;

    private long emailVerifyQuota;

    private long emailSendingQuota;

    private long currentEmailsVerified;

    private long currentEmailsSent;

    public long getEmailVerifyQuota() {
        return emailVerifyQuota;
    }

    public void setEmailVerifyQuota(long emailVerifyQuota) {
        this.emailVerifyQuota = emailVerifyQuota;
    }

    public long getEmailSendingQuota() {
        return emailSendingQuota;
    }

    public void setEmailSendingQuota(long emailSendingQuota) {
        this.emailSendingQuota = emailSendingQuota;
    }

    public long getCurrentEmailsVerified() {
        return currentEmailsVerified;
    }

    public void setCurrentEmailsVerified(long currentEmailsVerified) {
        this.currentEmailsVerified = currentEmailsVerified;
    }

    public long getCurrentEmailsSent() {
        return currentEmailsSent;
    }

    public void setCurrentEmailsSent(long currentEmailsSent) {
        this.currentEmailsSent = currentEmailsSent;
    }
}
