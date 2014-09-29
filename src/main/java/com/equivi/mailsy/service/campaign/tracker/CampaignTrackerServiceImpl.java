package com.equivi.mailsy.service.campaign.tracker;


import com.equivi.mailsy.data.dao.CampaignTrackerDao;
import com.equivi.mailsy.data.entity.CampaignTrackerEntity;
import com.equivi.mailsy.data.entity.QCampaignTrackerEntity;
import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CampaignTrackerServiceImpl implements CampaignTrackerService {

    @Resource
    private CampaignTrackerDao campaignTrackerDao;

    private Logger LOG = LoggerFactory.getLogger(CampaignTrackerServiceImpl.class);

    @Override
    @Transactional(readOnly = false)
    public void createCampaignTracker(String externalMessageId, Long campaignId, String recipient) {

        LOG.error("Creating campaign tracker:"+externalMessageId+" "+campaignId);
        CampaignTrackerEntity campaignTrackerEntity = new CampaignTrackerEntity();
        campaignTrackerEntity.setCampaignId(campaignId);

        externalMessageId = StringUtils.replace(externalMessageId, "<", "");
        externalMessageId = StringUtils.replace(externalMessageId, ">", "");

        campaignTrackerEntity.setCampaignMailerMessageId(externalMessageId);
        campaignTrackerEntity.setRecipient(recipient);

        campaignTrackerDao.save(campaignTrackerEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public CampaignTrackerEntity getCampaignTrackerEntity(Long campaignTrackerId) {
        return campaignTrackerDao.findOne(campaignTrackerId);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveCampaignTrackerEntity(CampaignTrackerEntity campaignTrackerEntity) {
        campaignTrackerDao.save(campaignTrackerEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignTrackerEntity> getCampaignTrackerEntityList(Map<CampaignTrackerSearchFilter, String> campaignTrackerSearchFilterStringMap) {

        Predicate campaignTrackerPredicate = getCampaignTrackerPredicate(campaignTrackerSearchFilterStringMap);

        Iterable<CampaignTrackerEntity> campaignTrackerEntities = campaignTrackerDao.findAll(campaignTrackerPredicate);

        if (campaignTrackerEntities.iterator().hasNext()) {
            return Lists.newArrayList(campaignTrackerEntities);
        }
        return null;
    }

    private Predicate getCampaignTrackerPredicate(Map<CampaignTrackerSearchFilter, String> campaignTrackerSearchFilterStringMap) {

        QCampaignTrackerEntity qCampaignTrackerEntity = QCampaignTrackerEntity.campaignTrackerEntity;

        BooleanBuilder booleanBuilderCampaignTrackerEntity = new BooleanBuilder();

        if (campaignTrackerSearchFilterStringMap != null && campaignTrackerSearchFilterStringMap.size() > 0) {
            if (campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.CAMPAIGN_ID) != null) {
                booleanBuilderCampaignTrackerEntity.or(qCampaignTrackerEntity.campaignId.eq(Long.valueOf(campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.CAMPAIGN_ID.getFilterName()))));
            }
            if (campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.CAMPAIGN_MAILER_MESSAGE_ID) != null) {
                booleanBuilderCampaignTrackerEntity.or(qCampaignTrackerEntity.campaignMailerMessageId.eq(campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.CAMPAIGN_MAILER_MESSAGE_ID)));
            }
            if (campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.RECIPIENT) != null) {
                booleanBuilderCampaignTrackerEntity.or(qCampaignTrackerEntity.campaignMailerMessageId.eq(campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.RECIPIENT)));
            }
        }

        return booleanBuilderCampaignTrackerEntity;
    }
}
