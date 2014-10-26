package com.equivi.mailsy.service.worker;

import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.GenericStatus;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.service.mailgun.MailgunService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CampaignMailSenderServiceTest {

    @Mock
    private MailgunService mailgunService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testSendCampaignMail() throws Exception {


    }

    private List<CampaignSubscriberGroupEntity> getCampaignSubscriberGroupEntityList(){

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setEmailAddress("contact1@email.com");
        contactEntity.setCompanyName("co 1");
        contactEntity.setFirstName("Mr.Co 1");

        ContactEntity contactEntity2 = new ContactEntity();
        contactEntity2.setEmailAddress("contact1@email.com");
        contactEntity2.setCompanyName("co 2");
        contactEntity2.setFirstName("Mr.Co 2");


        List<ContactEntity> contactEntityList = new ArrayList<>();
        contactEntityList.add(contactEntity);
        contactEntityList.add(contactEntity2);


        SubscriberGroupEntity subscriberGroupEntity = new SubscriberGroupEntity();
        subscriberGroupEntity.setStatus(GenericStatus.ACTIVE);

        SubscriberContactEntity subscriberContactEntity = new SubscriberContactEntity();
        subscriberContactEntity.setContactEntity(contactEntity);

        return null;
    }
}