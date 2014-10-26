package com.equivi.mailsy.service.mailgun;


import com.equivi.mailsy.service.mailgun.response.MailgunResponseEventMessage;

import java.io.File;
import java.util.List;

public interface MailgunService {


    String sendMessage(String campaignId, String domain, String from, final List<String> recipientList, final List<String> ccList, final List<String> bccList, String subject, String message);

    MailgunResponseEventMessage getEventForMessageId(String messageId);

    String sendMessageWithAttachment(String campaignId, String domain, String from, List<String> recipientList, List<String> ccList, List<String> bccList, String subject, String message);

    void deleteUnsubscribe(String domain, String emailAddress);

    void registerUnsubscribe(String domain, String emailAddress);

    List<String> getUnsubscribeList(String domain);

    boolean isUnsubscribe(String domain, String emailAddress);
}
