package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.service.mailgun.MailgunService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UnsubscribeControllerTest {

    @Mock
    private MailgunService mailgunService;

    @Mock
    private Model model;

    @InjectMocks
    private UnsubscribeController unsubscribeController;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = mock(Model.class);
    }

    @Test
    public void goToUnsubscribePage() {
        assertEquals(UnsubscribeController.UNSUBSCRIBED_PAGE, unsubscribeController.goToUnsubscribedPage("test@email.com", "equivi.com", model));
    }

    @Test
    public void sendUnsubscribeRequestToMailgun() {

        //Given
        String domainAddress = "abc@domain.com";
        String emailAddress = "test@email.com";

        //When
        unsubscribeController.sendUnsubscribeRequest(domainAddress, emailAddress, model);

        //Then
        verify(mailgunService).registerUnsubscribe(domainAddress, emailAddress);
    }
}