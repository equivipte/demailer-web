package com.equivi.mailsy.service.mailgun;


import com.equivi.mailsy.service.mail.Attachment;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface MailgunService {


    @Async
    void sendMessage(final String campaignId, final String domain, final String from, final List<String> recipientList, final List<String> ccList, final List<String> bccList, String subject, String message);

    @Async
    void sendMailWithAttachment(final List<String> recipientList, final List<Attachment> attachmentList, final List<String> ccList, final List<String> bccList, String subject, String message);

}
