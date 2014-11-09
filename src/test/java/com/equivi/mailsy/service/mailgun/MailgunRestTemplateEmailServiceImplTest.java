package com.equivi.mailsy.service.mailgun;

import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.rest.client.DemailerRestTemplate;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MailgunRestTemplateEmailServiceImplTest {

    private static final String TO_LIST = "someone@email.com";
    private static final String CC_LIST = "cclist@email.com";
    private static final String BCC_LIST = "bcclist@email.com";
    private static final String TEST_EMAIL = "Test Email";
    private static final String EMAIL_CONTENT = "This is email content test";
    private static final String MAILGUN_URL = "http://mailgun.api.com";
    private static final String MAILGUN_API_KEY = "key-123";
    private static final String CAMPAIGN_ID = "8d2651d8-702b-4d5b-8233-94f6d647464b";
    private static final String DOMAIN = "equivitest.com";
    private static final String FROM = "POST Master ADMIN";
    @Mock
    private DemailerRestTemplate demailerRestTemplate;
    @Mock
    private WebConfiguration webConfiguration;
    @InjectMocks
    private MailgunRestTemplateEmailServiceImpl mailgunEmailService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMessages() throws Exception {

        List<String> toList = Lists.newArrayList();
        toList.add(TO_LIST);

        List<String> ccList = Lists.newArrayList();
        ccList.add(CC_LIST);

        List<String> bccList = Lists.newArrayList();
        bccList.add(BCC_LIST);


        when(webConfiguration.getWebConfig(dEmailerWebPropertyKey.MAILGUN_WEB_URL)).thenReturn(MAILGUN_URL);
        when(webConfiguration.getWebConfig(dEmailerWebPropertyKey.MAILGUN_API_KEY)).thenReturn(MAILGUN_API_KEY);
        when(demailerRestTemplate.setCredentials(anyString(), anyString())).thenReturn(demailerRestTemplate);
        when(demailerRestTemplate.setHostName(anyString())).thenReturn(demailerRestTemplate);
        mailgunEmailService.sendMessage(CAMPAIGN_ID, DOMAIN, FROM, toList, ccList, bccList, TEST_EMAIL, EMAIL_CONTENT);

        verify(demailerRestTemplate);

    }
}