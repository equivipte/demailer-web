package com.equivi.mailsy.service.campaign;

import com.equivi.mailsy.data.dao.CampaignDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.CampaignStatus;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class CampaignConverterTest {

    @Mock
    private CampaignDao campaignDao;

    @Mock
    private SubscriberGroupDao subscriberGroupDao;

    @InjectMocks
    private CampaignConverter campaignConverter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertToCampaignEntity() throws ParseException {
        //Given
        CampaignDTO campaignDTOToConvert = getCampaignDTO();

        //When
        CampaignEntity campaignEntity = campaignConverter.convertToCampaignEntity(campaignDTOToConvert);

        //Then
        assertEquals("zztop_aditya@yahoo.com", campaignEntity.getEmailFrom());
        assertEquals(CampaignStatus.DRAFT, campaignEntity.getCampaignStatus());
        assertEquals("Test content", campaignEntity.getEmailContent());
        assertEquals("C123", campaignEntity.getCampaignName());

        Date expectedScheduledDate = campaignConverter.sdf.parse("25-09-2014 15:38");

        assertEquals(expectedScheduledDate, campaignEntity.getScheduledSendDate());

        assertEquals("25-09-2014 15:38",campaignConverter.sdf.format(campaignEntity.getScheduledSendDate()));
    }

    private CampaignDTO getCampaignDTO() {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setEmailFrom("zztop_aditya@yahoo.com");
        campaignDTO.setScheduledSendDateTime("25-09-2014 15:38");
        campaignDTO.setCampaignStatus(CampaignStatus.DRAFT.getCampaignStatusDescription());
        campaignDTO.setEmailContent("Test content");
        campaignDTO.setCampaignName("C123");

        return campaignDTO;
    }
}