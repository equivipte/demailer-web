package com.equivi.mailsy.service.campaign;

import com.equivi.mailsy.data.dao.CampaignDao;
import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.QCampaignEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.equivi.mailsy.service.exception.InvalidDataException;
import com.mysema.query.types.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Component
public class CampaignValidator {

    static final String DUPLICATE_CAMPAIGN_NAME = "campaign.duplicate.name";
    static final String INVALID_EMAIL_ADDRESS = "invalid.email.address";

    private static final Logger LOG = Logger.getLogger(CampaignValidator.class);

    @Resource
    private CampaignDao campaignDao;

    public void validate(CampaignDTO campaignDTO) {

        if (!StringUtils.isEmpty(campaignDTO.getEmailFrom())) {
            validateEmailAddress(campaignDTO.getEmailFrom());
        }
        CampaignEntity campaignEntity = campaignDao.findOne(getCampaignPredicate(campaignDTO.getCampaignName()));

        if (campaignDTO.getId() == null) {
            if (campaignEntity != null) {
                throw new InvalidDataException(DUPLICATE_CAMPAIGN_NAME);
            }
        } else {
            if (campaignEntity != null) {
                if (!campaignDTO.getId().equals(campaignEntity.getId())) {
                    throw new InvalidDataException(DUPLICATE_CAMPAIGN_NAME);
                }
            }
        }
    }

    void validateEmailAddress(String emailFrom) {
        try {
            InternetAddress internetAddress = new InternetAddress(emailFrom);
            internetAddress.validate();

        } catch (AddressException e) {
            LOG.error("Invalid email address:" + emailFrom, e);
            throw new InvalidDataException(INVALID_EMAIL_ADDRESS);
        }

    }

    private Predicate getCampaignPredicate(String campaignName) {
        QCampaignEntity qCampaignEntity = QCampaignEntity.campaignEntity;

        return qCampaignEntity.campaignName.eq(campaignName);
    }


}
