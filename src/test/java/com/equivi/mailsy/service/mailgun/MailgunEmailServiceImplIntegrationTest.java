package com.equivi.mailsy.service.mailgun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring-service.xml")
public class MailgunEmailServiceImplIntegrationTest {

    @Resource(name = "mailgunEmailService")
    private MailgunService mailgunEmailService;

    @Test
    public void testSendMailPlain() throws Exception {

        List<String> recipientList = new ArrayList<>();
        recipientList.add("aditya.eldrid@gmail.com");


        String from = "admin@equivitest.com";
        String subject = "Test tracking email";
        String emailContent = "Dear Mr.xxx&lt;br&gt;&lt;br&gt;&lt;i&gt;&lt;b&gt;Bla bla&lt;br&gt;&lt;/b&gt;&lt;/i&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;&lt;br&gt;";
        String domain = "equivitest.com";
        String campaignId="8d2651d8-702b-4d5b-8233-94f6d647464b";


        mailgunEmailService.sendMessage(campaignId,domain, from, recipientList, null, null, subject, emailContent);


    }
}