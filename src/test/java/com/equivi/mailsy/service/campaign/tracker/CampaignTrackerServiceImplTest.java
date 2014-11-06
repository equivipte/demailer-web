package com.equivi.mailsy.service.campaign.tracker;

import com.equivi.mailsy.data.dao.CampaignTrackerDao;
import com.equivi.mailsy.data.entity.CampaignTrackerEntity;
import com.equivi.mailsy.dto.campaign.CampaignStatisticDTO;
import com.mysema.query.types.Predicate;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class CampaignTrackerServiceImplTest {

    @Mock
    private CampaignTrackerDao campaignTrackerDao;

    @InjectMocks
    private CampaignTrackerServiceImpl campaignTrackerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCampaignStatistic() throws Exception {
        //Given
        when(campaignTrackerDao.findAll((Predicate) Matchers.any())).thenReturn(getCampaignTrackerEntityIterableList());

        //When
        CampaignStatisticDTO campaignStatisticDTO = campaignTrackerService.getCampaignStatistic(1L);

        //Then
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getCampaignId());
        Assert.assertEquals((Long) 3L, campaignStatisticDTO.getTotalOpened());
        Assert.assertEquals((Long) 3L, campaignStatisticDTO.getTotalDelivered());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalFailed());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalOpenUsingDesktop());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalOpenUsingMobile());
        Assert.assertEquals((Long) 0L, campaignStatisticDTO.getTotalOpenUsingOthers());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalOpenUsingTablet());

        Assert.assertEquals((Double) 33.0,campaignStatisticDTO.getOpenUsingMobilePercentage());
        Assert.assertEquals((Double) 33.0,campaignStatisticDTO.getOpenUsingDesktopPercentage());
        Assert.assertEquals((Double) 33.0,campaignStatisticDTO.getOpenUsingTabletPercentage());
        Assert.assertEquals((Double) 0D,campaignStatisticDTO.getOpenUsingOtherPercentage());

    }


    private Iterable<CampaignTrackerEntity> getCampaignTrackerEntityIterableList() {
        List<CampaignTrackerEntity> campaignTrackerEntityList = new ArrayList<>();

        //Open true for campaign tracker 1&2
        //Campaign track 1 open with mobile
        CampaignTrackerEntity campaignTrackerEntity = new CampaignTrackerEntity();
        campaignTrackerEntity.setCampaignId(1L);
        campaignTrackerEntity.setOpened(true);
        campaignTrackerEntity.setBounced(false);
        campaignTrackerEntity.setClicked(true);
        campaignTrackerEntity.setDelivered(true);
        campaignTrackerEntity.setClientDeviceType("mobile");
        campaignTrackerEntity.setRecipient("abc@email.com");


        //Campaign track 2 open with desktop
        CampaignTrackerEntity campaignTrackerEntity2 = new CampaignTrackerEntity();
        campaignTrackerEntity2.setCampaignId(2L);
        campaignTrackerEntity2.setOpened(true);
        campaignTrackerEntity2.setBounced(false);
        campaignTrackerEntity2.setClicked(true);
        campaignTrackerEntity2.setDelivered(true);
        campaignTrackerEntity2.setClientDeviceType("desktop");
        campaignTrackerEntity2.setRecipient("def@email.com");

        //Campaign 3 failed
        CampaignTrackerEntity campaignTrackerEntity3 = new CampaignTrackerEntity();
        campaignTrackerEntity3.setCampaignId(3L);
        campaignTrackerEntity3.setOpened(false);
        campaignTrackerEntity3.setBounced(true);
        campaignTrackerEntity3.setClicked(true);
        campaignTrackerEntity3.setDelivered(false);
        campaignTrackerEntity3.setClientDeviceType("unknown");
        campaignTrackerEntity3.setRecipient("cvf@email.com");

        //Campaign 4 open with tablet
        CampaignTrackerEntity campaignTrackerEntity4 = new CampaignTrackerEntity();
        campaignTrackerEntity4.setCampaignId(4L);
        campaignTrackerEntity4.setOpened(true);
        campaignTrackerEntity4.setBounced(false);
        campaignTrackerEntity4.setClicked(true);
        campaignTrackerEntity4.setDelivered(true);
        campaignTrackerEntity4.setClientDeviceType("tablet");

        campaignTrackerEntityList.add(campaignTrackerEntity);
        campaignTrackerEntityList.add(campaignTrackerEntity2);
        campaignTrackerEntityList.add(campaignTrackerEntity3);
        campaignTrackerEntityList.add(campaignTrackerEntity4);


        return campaignTrackerEntityList;
    }
}