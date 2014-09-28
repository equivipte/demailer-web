package com.equivi.mailsy.service.mailgun.response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class MailgunResponseEventMessageTest {

    @InjectMocks
    private MailgunResponseItem mailgunResponseItem;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHasEventType() throws Exception {
        //Given
        List<MailgunResponseItem> responseItems = buildMailgunResponseItems();

        //When
        MailgunResponseEventMessage eventMessage = new MailgunResponseEventMessage();
        eventMessage.setItems(responseItems);

        //Then
        assertTrue(eventMessage.hasEventType(EventAPIType.ACCEPTED));
    }

    @Test
    public void testDoesntHaveEventType() throws Exception {
        //Given
        List<MailgunResponseItem> responseItems = buildMailgunResponseItems();

        //When
        MailgunResponseEventMessage eventMessage = new MailgunResponseEventMessage();
        eventMessage.setItems(responseItems);

        //Then
        assertFalse(eventMessage.hasEventType(EventAPIType.OPENED));
    }

    private List<MailgunResponseItem> buildMailgunResponseItems() {
        List<MailgunResponseItem> mailgunResponseItems = new ArrayList<>();

        MailgunResponseItem responseItem = new MailgunResponseItem();
        responseItem.setRecipient("abc@email.com");
        responseItem.setEvent(EventAPIType.DELIVERED.getEventApiDescription());

        MailgunResponseItem responseItem2 = new MailgunResponseItem();
        responseItem2.setRecipient("dfe@email.com");
        responseItem2.setEvent(EventAPIType.ACCEPTED.getEventApiDescription());

        mailgunResponseItems.add(responseItem);
        mailgunResponseItems.add(responseItem2);

        return mailgunResponseItems;
    }
}