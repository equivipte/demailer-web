package com.equivi.mailsy.service.campaign;


import com.equivi.mailsy.data.dao.CampaignDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.CampaignStatus;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.equivi.mailsy.service.constant.ConstantProperty;
import org.apache.commons.lang3.StringUtils;
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

        campaignEntity.setCampaignName(campaignDTO.getCampaignName());
        campaignEntity.setEmailContent(campaignDTO.getEmailContent());
        campaignEntity.setEmaiSubject(campaignDTO.getEmailSubject());
        campaignEntity.setCampaignStatus(CampaignStatus.getStatusByDescription(campaignDTO.getCampaignStatus()));

        SubscriberGroupEntity subscriberGroupEntity = subscriberGroupDao.findOne(campaignDTO.getSubscriberGroupId());
        campaignEntity.setSubscriberGroupEntity(subscriberGroupEntity);

        if (!StringUtils.isEmpty(campaignDTO.getScheduledSendDate())) {
            try {
                campaignEntity.setScheduledSendDate(sdf.parse(campaignDTO.getScheduledSendDate()));
            } catch (ParseException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return campaignEntity;
    }

    public CampaignDTO convertToCampaignDTO(CampaignEntity campaignEntity) {

        CampaignDTO campaignDTO = new CampaignDTO();

        campaignDTO.setId(campaignEntity.getId());
        campaignDTO.setCampaignName(campaignEntity.getCampaignName());
        campaignDTO.setCampaignStatus(campaignEntity.getCampaignStatus().getCampaignStatusDescription());
        campaignDTO.setEmailContent(campaignEntity.getEmailContent());
        campaignDTO.setEmailSubject(campaignEntity.getEmaiSubject());
        campaignDTO.setSubscriberGroupId(campaignEntity.getSubscriberGroupEntity().getId());

        if (campaignEntity.getScheduledSendDate() != null) {
            campaignDTO.setScheduledSendDate(sdf.format(campaignEntity.getScheduledSendDate()));
        }

        return campaignDTO;
    }
}
