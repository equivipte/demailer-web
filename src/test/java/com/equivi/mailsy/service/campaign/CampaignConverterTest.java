package com.equivi.mailsy.service.campaign;

import com.equivi.mailsy.data.dao.CampaignDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void testConvertToCampaignEntity() {
        //TBT
    }
}