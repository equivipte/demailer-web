package com.equivi.mailsy.service.worker;

import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.CampaignStatus;
import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.GenericStatus;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.service.campaign.CampaignManagementService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class EmailOutboxCollectorTest {

    @Mock
    private CampaignManagementService campaignManagementService;

    @InjectMocks
    private CampaignUpdateWorker emailOutboxCollector;

    private List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntities;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        campaignSubscriberGroupEntities = getCampaignSubscriberGroupEntityList();
    }

    @Test
    public void testCollectOutboxEmail() throws Exception {

        List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntityList = getCampaignSubscriberGroupEntityList();
        when(campaignManagementService.getEmailListToSend()).thenReturn(campaignSubscriberGroupEntityList);

        //emailOutboxCollector.collectOutboxCampaignAndCopyToCampaignTracker();
    }

    private List<CampaignSubscriberGroupEntity> getCampaignSubscriberGroupEntityList(){

        List<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntityList = new ArrayList<>();

        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setId(1L);
        campaignEntity.setCampaignName("campaign 1");
        campaignEntity.setCampaignStatus(CampaignStatus.OUTBOX);

        SubscriberGroupEntity subscriberGroupEntity = new SubscriberGroupEntity();
        subscriberGroupEntity.setGroupName("group 1");
        subscriberGroupEntity.setStatus(GenericStatus.ACTIVE);

        CampaignSubscriberGroupEntity campaignSubscriberGroupEntity = new CampaignSubscriberGroupEntity();
        campaignSubscriberGroupEntity.setCampaignEntity(campaignEntity);
        campaignSubscriberGroupEntity.setSubscriberGroupEntity(subscriberGroupEntity);

        CampaignEntity campaignEntity2 = new CampaignEntity();
        campaignEntity2.setId(2L);
        campaignEntity2.setCampaignName("campaign 2");
        campaignEntity2.setCampaignStatus(CampaignStatus.OUTBOX);

        SubscriberGroupEntity subscriberGroupEntity2 = new SubscriberGroupEntity();
        subscriberGroupEntity2.setGroupName("group 2");
        subscriberGroupEntity2.setStatus(GenericStatus.ACTIVE);

        CampaignSubscriberGroupEntity campaignSubscriberGroupEntity2 = new CampaignSubscriberGroupEntity();
        campaignSubscriberGroupEntity2.setCampaignEntity(campaignEntity2);
        campaignSubscriberGroupEntity2.setSubscriberGroupEntity(subscriberGroupEntity2);

        campaignSubscriberGroupEntityList.add(campaignSubscriberGroupEntity);
        campaignSubscriberGroupEntityList.add(campaignSubscriberGroupEntity2);

        return campaignSubscriberGroupEntityList;
    }
}