package com.equivi.mailsy.service.contact;

import com.equivi.mailsy.data.dao.CampaignSubscriberGroupDao;
import com.equivi.mailsy.data.dao.ContactDao;
import com.equivi.mailsy.data.dao.SubscriberContactDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.CampaignStatus;
import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.GenericStatus;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.service.campaign.CampaignSubscriberGroupPredicate;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.mysema.query.types.Predicate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ContactManagementServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ContactDao contactDao;
    @Mock
    private SubscriberContactDao subscriberContactDao;
    @Mock
    private ContactPredicate contactPredicate;
    @Mock
    private CampaignSubscriberGroupPredicate campaignSubscriberGroupPredicate;
    @Mock
    private SubscriberGroupDao subscriberGroupDao;
    @Mock
    private CampaignSubscriberGroupDao campaignSubscriberGroupDao;
    @Mock
    private MailgunService mailgunService;
    @InjectMocks
    private ContactManagementServiceImpl contactManagementService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteSubscriberContact() throws Exception {
        //Given
        //Set campaign subscriber group entities null
        List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntities = new ArrayList<>();
        List<SubscriberContactEntity> subscriberContactEntities = getSubscriberContactList();

        when(campaignSubscriberGroupDao.findAll(any(Predicate.class))).thenReturn(campaignSubscriberGroupEntities);
        when(subscriberContactDao.findAll(any(Predicate.class))).thenReturn(subscriberContactEntities);

        //When
        contactManagementService.deleteSubscriberGroup(1L);

        //Then
        Mockito.verify(subscriberContactDao).delete(subscriberContactEntities);
        Mockito.verify(subscriberGroupDao).delete(1L);

    }

    @Test
    public void testDeleteSubscriberContactFailedDueToSubscriberHasBeenUsedByCampaign() throws Exception {
        //Then
        expectedException.expectMessage(ContactManagementServiceImpl.ERROR_DELETE_SUBSCRIBER_GROUP_MSG_USED_BY_CAMPAIGN);
        expectedException.expect(IllegalArgumentException.class);

        //Given
        List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntities = getCampaignSubscriberGroupList();
        List<SubscriberContactEntity> subscriberContactEntities = getSubscriberContactList();

        when(campaignSubscriberGroupDao.findAll(any(Predicate.class))).thenReturn(campaignSubscriberGroupEntities);
        when(subscriberContactDao.findAll(any(Predicate.class))).thenReturn(subscriberContactEntities);

        //When
        contactManagementService.deleteSubscriberGroup(1L);

    }

    private List<CampaignSubscriberGroupEntity> getCampaignSubscriberGroupList() {
        SubscriberGroupEntity subscriberGroupEntity = new SubscriberGroupEntity();
        subscriberGroupEntity.setId(1L);
        subscriberGroupEntity.setGroupName("Group 1");
        subscriberGroupEntity.setStatus(GenericStatus.ACTIVE);

        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setId(1L);
        campaignEntity.setCampaignName("Campaign 123");
        campaignEntity.setCampaignStatus(CampaignStatus.SEND);

        List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntities = new ArrayList<>();

        CampaignSubscriberGroupEntity campaignSubscriberGroupEntity = new CampaignSubscriberGroupEntity();
        campaignSubscriberGroupEntity.setId(1L);
        campaignSubscriberGroupEntity.setSubscriberGroupEntity(subscriberGroupEntity);
        campaignSubscriberGroupEntity.setCampaignEntity(campaignEntity);

        campaignSubscriberGroupEntities.add(campaignSubscriberGroupEntity);
        return campaignSubscriberGroupEntities;
    }

    private List<SubscriberContactEntity> getSubscriberContactList() {
        List<SubscriberContactEntity> subscriberContactEntities = new ArrayList<>();

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setFirstName("Contact 1");
        contactEntity.setLastName("ABC");
        contactEntity.setEmailAddress("contact1@equivi.com");

        ContactEntity contactEntity2 = new ContactEntity();
        contactEntity2.setFirstName("Contact 2");
        contactEntity2.setLastName("ABC");
        contactEntity2.setEmailAddress("contact2@equivi.com");

        SubscriberGroupEntity subscriberGroupEntity = new SubscriberGroupEntity();
        subscriberGroupEntity.setId(1L);
        subscriberGroupEntity.setGroupName("Group 1");
        subscriberGroupEntity.setStatus(GenericStatus.ACTIVE);

        SubscriberContactEntity subscriberContactEntity = new SubscriberContactEntity();
        subscriberContactEntity.setContactEntity(contactEntity);
        subscriberContactEntity.setSubscriberGroupEntity(subscriberGroupEntity);

        SubscriberContactEntity subscriberContactEntity2 = new SubscriberContactEntity();
        subscriberContactEntity2.setContactEntity(contactEntity2);
        subscriberContactEntity2.setSubscriberGroupEntity(subscriberGroupEntity);

        subscriberContactEntities.add(subscriberContactEntity);
        subscriberContactEntities.add(subscriberContactEntity2);

        return subscriberContactEntities;
    }
}