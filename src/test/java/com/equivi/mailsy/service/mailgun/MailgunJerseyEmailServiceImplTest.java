//package com.equivi.mailsy.service.mailgun;
//
//import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
//import com.equivi.mailsy.service.mailgun.response.MailgunResponseMessage;
//import com.equivi.mailsy.web.constant.WebConfiguration;
//import com.google.common.collect.Lists;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.rules.TemporaryFolder;
//import org.mockito.InjectMocks;
//import org.mockito.Matchers;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//
//import static junit.framework.Assert.assertNotNull;
//import static org.mockito.Matchers.anyObject;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.when;
//
//public class MailgunJerseyEmailServiceImplTest {
//
//    @InjectMocks
//    private MailgunJerseyEmailServiceImpl mailgunEmailJerseyService;
//
//    @Mock
//    private WebConfiguration webConfiguration;
//
//    @Mock
//    private MailgunJerseyClient mailgunJerseyClient;
//
//    @Mock
//    private WebResource webResource;
//
//    @Mock
//    private MailgunUtility mailgunUtility;
//
//    @Mock
//    private ClientResponse clientResponse;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    private static final String CAMPAIGN_ID = "1";
//    private static final String DOMAIN_SANDBOX = "sandbox80dd6c12cf4c4f99bdfa256bfea7cfeb.mailgun.org";
//    private static final String FROM = "admin@equivitest.com";
//    private static final List<String> TO_LIST_STRING = Lists.newArrayList("zztop_aditya@yahoo.com");
//    private static final List<String> CC_LIST_STRING = Lists.newArrayList("zztop_aditya@yahoo.com");
//    private static final List<String> BCC_LIST_STRING = Lists.newArrayList("zztop_aditya@yahoo.com");
//    private static final String SUBJECT = "Test Email";
//    private static final String MAIL_MESSAGE = "Test Email Content";
//    private static final String MESSAGE_ID="123@sandbox.com";
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    @Rule
//    public TemporaryFolder tempFolder = new TemporaryFolder();
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        when(webConfiguration.getWebConfig(dEmailerWebPropertyKey.MAILGUN_API_KEY)).thenReturn("api-key");
//        when(webConfiguration.getWebConfig(dEmailerWebPropertyKey.MAILGUN_WEB_URL)).thenReturn("https://api.mailgun.net/v2/");
//        when(objectMapper.readValue(anyString(), Matchers.<Class<Object>>anyObject())).thenReturn(getMailgunResponseMessage());
//    }
//
//    @Test
//    public void testSendMessage() throws Exception {
//        //Given & When
//        String eventMessageId = mailgunEmailJerseyService.sendMessage(CAMPAIGN_ID, DOMAIN_SANDBOX, FROM, TO_LIST_STRING, CC_LIST_STRING, BCC_LIST_STRING, SUBJECT, MAIL_MESSAGE);
//
//        //Then
//        assertNotNull(eventMessageId);
//    }
//
//
//    @Test
//    public void testSendMessageWithAttachmentSuccess() throws Exception {
//
//        //Given
//        when(mailgunUtility.sanitizeEmailContentFromEmbeddedImage(anyString())).thenReturn("email content");
//        when(mailgunUtility.getAttachmentFileList(anyString())).thenReturn(Lists.newArrayList(tempFolder.newFile("1"), tempFolder.newFile("2")));
//
//
//        //When
//        String eventMessageId = mailgunEmailJerseyService.sendMessageWithAttachment(CAMPAIGN_ID, DOMAIN_SANDBOX, FROM, TO_LIST_STRING, CC_LIST_STRING, BCC_LIST_STRING, SUBJECT, MAIL_MESSAGE);
//
//        //Then
//        assertNotNull(eventMessageId);
//
//    }
//
//    private MailgunResponseMessage getMailgunResponseMessage(){
//        MailgunResponseMessage mailgunResponseMessage = new MailgunResponseMessage();
//        mailgunResponseMessage.setId(MESSAGE_ID);
//        mailgunResponseMessage.setMessage("Queued succesfully");
//
//        return mailgunResponseMessage;
//    }
//
//}