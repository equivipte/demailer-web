package com.equivi.mailsy.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.List;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;


    @Resource(name = "dEmailerWebProperties")
    private Properties dEmailerWebProperties;

    @Override
    public void sendMailPlain(List<String> recipientList, List<String> ccList, List<String> bccList, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String mailFrom = (String) dEmailerWebProperties.get("mail.sender");
        mailMessage.setFrom(mailFrom);

        if (recipientList != null && recipientList.size() > 0) {
            String[] recipientArray = recipientList.toArray(new String[recipientList.size()]);
            mailMessage.setTo(recipientArray);
        }

        if (ccList != null && ccList.size() > 0) {
            String[] ccListArray = ccList.toArray(new String[ccList.size()]);
            mailMessage.setCc(ccListArray);
        }
        if (bccList != null && bccList.size() > 0) {
            String[] bccListArray = bccList.toArray(new String[bccList.size()]);
            mailMessage.setBcc(bccListArray);
        }

        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendMailPlainSync(List<String> recipientList, List<String> ccList, List<String> bccList, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String mailFrom = (String) dEmailerWebProperties.get("mail.sender");
        mailMessage.setFrom(mailFrom);

        if (recipientList != null && recipientList.size() > 0) {
            String[] recipientArray = recipientList.toArray(new String[recipientList.size()]);
            mailMessage.setTo(recipientArray);
        }

        if (ccList != null && ccList.size() > 0) {
            String[] ccListArray = ccList.toArray(new String[ccList.size()]);
            mailMessage.setCc(ccListArray);
        }
        if (bccList != null && bccList.size() > 0) {
            String[] bccListArray = bccList.toArray(new String[bccList.size()]);
            mailMessage.setBcc(bccListArray);
        }

        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendMailWithAttachment(final List<String> recipientList, final List<Attachment> attachmentList, final List<String> ccList, final List<String> bccList, final String subject, final String message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        String mailFrom = (String) dEmailerWebProperties.get("mail.sender");


        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(mailFrom);
            helper.setTo((String[]) recipientList.toArray());
            helper.setSubject(subject);
            helper.setText(message);

            for (Attachment fileAttach : attachmentList) {
                DataSource dataSource = new ByteArrayDataSource(fileAttach.getContent().toByteArray(), fileAttach.getType().getApplicationFileName());
                helper.addAttachment(fileAttach.getFileName(), dataSource);
            }

        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(mimeMessage);
    }
}
