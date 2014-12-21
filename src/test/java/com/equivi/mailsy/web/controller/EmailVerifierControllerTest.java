package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.service.emailverifier.EmailVerifierServiceFactory;
import com.equivi.mailsy.service.emailverifier.webemailverifier.WebEmailVerifierServiceImpl;
import com.equivi.mailsy.service.quota.QuotaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class EmailVerifierControllerTest {

    private static final String EMAIL_TEST = "email.test@email.com";
    @Mock
    private EmailVerifierServiceFactory emailVerifierServiceFactory;
    @Mock
    private WebEmailVerifierServiceImpl webEmailVerifierService;
    @Mock
    private QuotaService quotaService;
    @InjectMocks
    private EmailVerifierController emailVerifierController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVerifyEmail() throws Exception {
        //Given
        when(emailVerifierServiceFactory.getEmailVerifierService(anyString())).thenReturn(webEmailVerifierService);

        //When
        webEmailVerifierService.getEmailAddressStatus(EMAIL_TEST);

        //Then
        verify(webEmailVerifierService).getEmailAddressStatus(EMAIL_TEST);

        verifyNoMoreInteractions(webEmailVerifierService);

    }
}