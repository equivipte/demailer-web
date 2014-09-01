package com.equivi.mailsy.service.campaign;


import com.equivi.mailsy.data.dao.CampaignDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.equivi.mailsy.service.constant.ConstantProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class CampaignConverter {

    @Resource
    private CampaignDao campaignDao;

    @Resource
    private SubscriberGroupDao subscriberGroupDao;

    private static final Logger LOG = LoggerFactory.getLogger(CampaignConverter.class);

    private static SimpleDateFormat sdf = null;

    static {
        sdf = new SimpleDateFormat(ConstantProperty.DATE_TIME_FORMAT.getValue());
    }

    public CampaignEntity convertToCampaignEntity(CampaignDTO campaignDTO) {

        CampaignEntity campaignEntity;
        if (campaignDTO.getId() != null) {
            campaignEntity = campaignDao.findOne(campaignDTO.getId());
        } else {
            campaignEntity = new CampaignEntity();
        }

        campaignEntity.setEmailContent(campaignDTO.getEmailContent());
        campaignEntity.setEmaiSubject(campaignDTO.getEmaiSubject());

        SubscriberGroupEntity subscriberGroupEntity = subscriberGroupDao.findOne(campaignDTO.getSubscriberGroupId());
        campaignEntity.setSubscriberGroupEntity(subscriberGroupEntity);
        try {
            campaignEntity.setScheduledSendDate(sdf.parse(campaignDTO.getScheduledSendDate()));
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }

        return campaignEntity;
    }
}
