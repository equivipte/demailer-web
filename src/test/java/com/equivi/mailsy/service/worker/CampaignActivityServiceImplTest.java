package com.equivi.mailsy.service.worker;

import com.equivi.mailsy.data.entity.CampaignTrackerEntity;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.equivi.mailsy.service.mailgun.response.EventAPIType;
import com.equivi.mailsy.service.mailgun.response.MailgunResponseEventMessage;
import com.equivi.mailsy.service.mailgun.response.MailgunResponseItem;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CampaignActivityServiceImplTest {

    @InjectMocks
    private CampaignActivityServiceImpl campaignActivityService;

    @Mock
    private MailgunService mailgunService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testUpdateCampaignTrackerEntityPerEvent() throws Exception {
        //Given
        CampaignTrackerEntity campaignTrackerEntity = new CampaignTrackerEntity();
        campaignTrackerEntity.setCampaignMailerMessageId("123");

        MailgunResponseEventMessage mailgunResponseEventMessage = getMailgunResponseEventMessage();

        when(mailgunService.getEventForMessageId(anyString())).thenReturn(mailgunResponseEventMessage);

        //When
        campaignTrackerEntity = campaignActivityService.updateCampaignTrackerEntityPerEvent(campaignTrackerEntity);


        //Then
        assertTrue(campaignTrackerEntity.isDelivered());
    }

    @Test
    public void testTimestamp(){
        String timestamp= "1411799353.429292";

        timestamp = StringUtils.replace(timestamp, ".", "");

        StringBuilder sbTimestamp = new StringBuilder(timestamp);
        sbTimestamp.insert(timestamp.length()-3,".");

        System.out.println(sbTimestamp.toString());

        Long ts = Double.valueOf(sbTimestamp.toString()).longValue();

        Date date = new Date(ts);

        System.out.println(date);
        System.out.println(System.currentTimeMillis());
    }

    private MailgunResponseEventMessage getMailgunResponseEventMessage() {
        MailgunResponseEventMessage mailgunResponseEventMessage = new MailgunResponseEventMessage();

        List<MailgunResponseItem> mailgunResponseItemList = new ArrayList<>();

        MailgunResponseItem mailgunResponseItem = new MailgunResponseItem();
        mailgunResponseItem.setTimestamp("1411889024.630593");
        mailgunResponseItem.setEvent(EventAPIType.DELIVERED.getEventApiDescription());

        mailgunResponseItemList.add(mailgunResponseItem);

        mailgunResponseEventMessage.setItems(mailgunResponseItemList);

        return mailgunResponseEventMessage;
    }
}