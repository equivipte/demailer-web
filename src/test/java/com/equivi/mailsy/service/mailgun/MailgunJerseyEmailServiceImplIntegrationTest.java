package com.equivi.mailsy.service.mailgun;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring-service.xml")
public class MailgunJerseyEmailServiceImplIntegrationTest {

    private static final String CAMPAIGN_ID ="1";
    private static final String DOMAIN_SANDBOX = "sandbox80dd6c12cf4c4f99bdfa256bfea7cfeb.mailgun.org";
    private static final String FROM ="admin@equivitest.com";
    private static final List<String> TO_LIST_STRING = Lists.newArrayList("zztop_aditya@yahoo.com");
    private static final List<String> CC_LIST_STRING = Lists.newArrayList("zztop_aditya@yahoo.com");
    private static final List<String> BCC_LIST_STRING = Lists.newArrayList("zztop_aditya@yahoo.com");
    private static final String SUBJECT = "Test Email";
    private static final String MAIL_MESSAGE = "Test Email Content";

    @Resource(name = "mailgunJerseyService")
    private MailgunService mailgunEmailService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSendMessage() throws Exception {
        String eventMessageId = mailgunEmailService.sendMessage(CAMPAIGN_ID, DOMAIN_SANDBOX,FROM, TO_LIST_STRING, CC_LIST_STRING, BCC_LIST_STRING,SUBJECT,MAIL_MESSAGE);
    }
}