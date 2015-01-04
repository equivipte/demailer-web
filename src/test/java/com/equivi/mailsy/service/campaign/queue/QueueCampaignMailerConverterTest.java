package com.equivi.mailsy.service.campaign.queue;

import com.equivi.mailsy.service.contact.ContactManagementService;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.equivi.mailsy.service.subsriber.SubscriberGroupService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class QueueCampaignMailerConverterTest {

    @Mock
    private SubscriberGroupService subscriberGroupService;

    @Mock
    private ContactManagementService contactManagementService;

    @Mock
    private MailgunService mailgunService;

    @InjectMocks
    private QueueCampaignMailerConverter queueCampaignMailerConverter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuildUnsubscribeURL() throws Exception {

    }
}