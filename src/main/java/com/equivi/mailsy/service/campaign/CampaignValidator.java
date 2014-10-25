package com.equivi.mailsy.service.campaign;

import com.equivi.mailsy.data.dao.CampaignDao;
import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.QCampaignEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.equivi.mailsy.service.exception.InvalidDataException;
import com.mysema.query.types.Predicate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CampaignValidator {

    private static final String DUPLICATE_CAMPAIGN_NAME = "campaign.duplicate.name";

    @Resource
    private CampaignDao campaignDao;

    public void validate(CampaignDTO campaignDTO) {

        CampaignEntity campaignEntity = campaignDao.findOne(getCampaignPredicate(campaignDTO.getCampaignName()));

        if (campaignDTO.getId() == null) {
            if (campaignEntity != null) {
                throw new InvalidDataException(DUPLICATE_CAMPAIGN_NAME);
            }
        } else {
            if(campaignEntity!=null){
                if (!campaignDTO.getId().equals(campaignEntity.getId())) {
                    throw new InvalidDataException(DUPLICATE_CAMPAIGN_NAME);
                }
            }
        }
    }

    private Predicate getCampaignPredicate(String campaignName) {
        QCampaignEntity qCampaignEntity = QCampaignEntity.campaignEntity;

        return qCampaignEntity.campaignName.eq(campaignName);
    }


}
