package com.equivi.mailsy.service.campaign.tracker;

import com.equivi.mailsy.data.dao.CampaignTrackerDao;
import com.equivi.mailsy.data.entity.CampaignTrackerEntity;
import com.equivi.mailsy.dto.campaign.CampaignStatisticDTO;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
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

import static org.junit.Assert.assertTrue;
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
        Assert.assertEquals((Long) 4L, campaignStatisticDTO.getTotalOpened());
        Assert.assertEquals((Long) 4L, campaignStatisticDTO.getTotalDelivered());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalFailed());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalOpenUsingDesktop());
        Assert.assertEquals((Long) 2L, campaignStatisticDTO.getTotalOpenUsingMobile());
        Assert.assertEquals((Long) 0L, campaignStatisticDTO.getTotalOpenUsingOthers());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalOpenUsingTablet());

        Assert.assertEquals(50.0, campaignStatisticDTO.getOpenUsingMobilePercentage());
        Assert.assertEquals(25.0, campaignStatisticDTO.getOpenUsingDesktopPercentage());
        Assert.assertEquals(25.0, campaignStatisticDTO.getOpenUsingTabletPercentage());
        Assert.assertEquals(0D, campaignStatisticDTO.getOpenUsingOtherPercentage());

    }

    @Test
    public void testStatisticOpenedEmail() throws Exception {
        //Given
        when(campaignTrackerDao.findAll((Predicate) Matchers.any())).thenReturn(getCampaignTrackerEntityIterableList());

        //When
        CampaignStatisticDTO campaignStatisticDTO = campaignTrackerService.getCampaignStatistic(1L);

        //Then
        Assert.assertEquals(4, campaignStatisticDTO.getOpenedEmailList().size());

        assertOpenedEmailList(campaignStatisticDTO);
    }

    @Test
    public void testStatisticUnsubscribedEmail() throws Exception {
        //Given
        when(campaignTrackerDao.findAll((Predicate) Matchers.any())).thenReturn(getCampaignTrackerEntityIterableList());

        //When
        CampaignStatisticDTO campaignStatisticDTO = campaignTrackerService.getCampaignStatistic(1L);

        //Then
        Assert.assertEquals(1, campaignStatisticDTO.getUnsubscriberEmailList().size());

        assertUnsubscribedEmailList(campaignStatisticDTO);
    }

    @Test
    public void testStatisticFailedEmail() throws Exception {
        //Given
        when(campaignTrackerDao.findAll((Predicate) Matchers.any())).thenReturn(getCampaignTrackerEntityIterableList());

        //When
        CampaignStatisticDTO campaignStatisticDTO = campaignTrackerService.getCampaignStatistic(1L);

        //Then
        Assert.assertEquals(1, campaignStatisticDTO.getFailedToDeliverEmailList().size());
        assertFailedEmailList(campaignStatisticDTO);
    }

    private void assertOpenedEmailList(CampaignStatisticDTO campaignStatisticDTO) {
        List<String> expectedEmailList = Lists.newArrayList("abc@email.com", "def@email.com", "xxy@email.com", "yyx@email.com");

        for (final String expectedEmail : expectedEmailList) {
            boolean contains = Iterables.find(campaignStatisticDTO.getOpenedEmailList(), new com.google.common.base.Predicate<String>() {
                @Override
                public boolean apply(final String input) {
                    return input.equals(expectedEmail);
                }
            }) != null;
            assertTrue(contains);
        }
    }

    private void assertUnsubscribedEmailList(CampaignStatisticDTO campaignStatisticDTO) {
        List<String> expectedEmailList = Lists.newArrayList("yyx@email.com");

        for (final String expectedEmail : expectedEmailList) {
            boolean contains = Iterables.find(campaignStatisticDTO.getUnsubscriberEmailList(), new com.google.common.base.Predicate<String>() {
                @Override
                public boolean apply(final String input) {
                    return input.equals(expectedEmail);
                }
            }) != null;
            assertTrue(contains);
        }
    }

    private void assertFailedEmailList(CampaignStatisticDTO campaignStatisticDTO) {
        List<String> expectedEmailList = Lists.newArrayList("cvf@email.com");

        for (final String expectedEmail : expectedEmailList) {
            boolean contains = Iterables.find(campaignStatisticDTO.getFailedToDeliverEmailList(), new com.google.common.base.Predicate<String>() {
                @Override
                public boolean apply(final String input) {
                    return input.equals(expectedEmail);
                }
            }) != null;
            assertTrue(contains);
        }
    }

    @Test
    public void testGetCampaignStatisticOpenList() throws Exception {
        //Given
        when(campaignTrackerDao.findAll((Predicate) Matchers.any())).thenReturn(getCampaignTrackerEntityIterableList());

        //When
        CampaignStatisticDTO campaignStatisticDTO = campaignTrackerService.getCampaignStatistic(1L);

        //Then
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getCampaignId());
        Assert.assertEquals((Long) 4L, campaignStatisticDTO.getTotalOpened());
        Assert.assertEquals((Long) 4L, campaignStatisticDTO.getTotalDelivered());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalFailed());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalOpenUsingDesktop());
        Assert.assertEquals((Long) 2L, campaignStatisticDTO.getTotalOpenUsingMobile());
        Assert.assertEquals((Long) 0L, campaignStatisticDTO.getTotalOpenUsingOthers());
        Assert.assertEquals((Long) 1L, campaignStatisticDTO.getTotalOpenUsingTablet());

        Assert.assertEquals(50.0, campaignStatisticDTO.getOpenUsingMobilePercentage());
        Assert.assertEquals(25.0, campaignStatisticDTO.getOpenUsingDesktopPercentage());
        Assert.assertEquals(25.0, campaignStatisticDTO.getOpenUsingTabletPercentage());
        Assert.assertEquals(0D, campaignStatisticDTO.getOpenUsingOtherPercentage());

    }


    private Iterable<CampaignTrackerEntity> getCampaignTrackerEntityIterableList() {
        List<CampaignTrackerEntity> campaignTrackerEntityList = new ArrayList<>();

        //Open true for campaign tracker 1&2
        //Campaign track 1 open with mobile
        CampaignTrackerEntity campaignTrackerEntity1 = new CampaignTrackerEntity();
        campaignTrackerEntity1.setCampaignId(1L);
        campaignTrackerEntity1.setOpened(true);
        campaignTrackerEntity1.setBounced(false);
        campaignTrackerEntity1.setClicked(true);
        campaignTrackerEntity1.setDelivered(true);
        campaignTrackerEntity1.setClientDeviceType("mobile");
        campaignTrackerEntity1.setRecipient("abc@email.com");

        CampaignTrackerEntity campaignTrackerEntity2 = new CampaignTrackerEntity();
        campaignTrackerEntity2.setCampaignId(1L);
        campaignTrackerEntity2.setOpened(true);
        campaignTrackerEntity2.setBounced(false);
        campaignTrackerEntity2.setClicked(true);
        campaignTrackerEntity2.setDelivered(true);
        campaignTrackerEntity2.setClientDeviceType("mobile");
        campaignTrackerEntity2.setRecipient("def@email.com");


        //Campaign track 3 open with desktop
        CampaignTrackerEntity campaignTrackerEntity3 = new CampaignTrackerEntity();
        campaignTrackerEntity3.setCampaignId(1L);
        campaignTrackerEntity3.setOpened(true);
        campaignTrackerEntity3.setBounced(false);
        campaignTrackerEntity3.setClicked(true);
        campaignTrackerEntity3.setDelivered(true);
        campaignTrackerEntity3.setClientDeviceType("desktop");
        campaignTrackerEntity3.setRecipient("xxy@email.com");

        //Campaign 4 failed
        CampaignTrackerEntity campaignTrackerEntity4 = new CampaignTrackerEntity();
        campaignTrackerEntity4.setCampaignId(1L);
        campaignTrackerEntity4.setOpened(false);
        campaignTrackerEntity4.setBounced(true);
        campaignTrackerEntity4.setClicked(false);
        campaignTrackerEntity4.setDelivered(false);
        campaignTrackerEntity4.setClientDeviceType("unknown");
        campaignTrackerEntity4.setRecipient("cvf@email.com");

        //Campaign 5 open with tablet,but unsubscribed
        CampaignTrackerEntity campaignTrackerEntity5 = new CampaignTrackerEntity();
        campaignTrackerEntity5.setCampaignId(1L);
        campaignTrackerEntity5.setOpened(true);
        campaignTrackerEntity5.setBounced(false);
        campaignTrackerEntity5.setClicked(true);
        campaignTrackerEntity5.setDelivered(true);
        campaignTrackerEntity5.setUnsubscribed(true);
        campaignTrackerEntity5.setClientDeviceType("tablet");
        campaignTrackerEntity5.setRecipient("yyx@email.com");

        campaignTrackerEntityList.add(campaignTrackerEntity1);
        campaignTrackerEntityList.add(campaignTrackerEntity2);
        campaignTrackerEntityList.add(campaignTrackerEntity3);
        campaignTrackerEntityList.add(campaignTrackerEntity4);
        campaignTrackerEntityList.add(campaignTrackerEntity5);


        return campaignTrackerEntityList;
    }
}