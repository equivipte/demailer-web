package com.equivi.mailsy.service.mail;


import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface MailService {


    @Async
    void sendMailPlain(final List<String> recipientList, final List<String> ccList, final List<String> bccList, String subject, String message);

    void sendMailPlainSync(final List<String> recipientList, final List<String> ccList, final List<String> bccList, String subject, String message);

    @Async
    void sendMailWithAttachment(final List<String> recipientList, final List<Attachment> attachmentList, final List<String> ccList, final List<String> bccList, String subject, String message);

}
