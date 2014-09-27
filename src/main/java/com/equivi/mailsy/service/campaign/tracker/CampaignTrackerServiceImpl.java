package com.equivi.mailsy.service.campaign.tracker;


import com.equivi.mailsy.data.dao.CampaignTrackerDao;
import com.equivi.mailsy.data.entity.CampaignTrackerEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CampaignTrackerServiceImpl implements CampaignTrackerService{

    @Resource
    private CampaignTrackerDao campaignTrackerDao;

    @Override
    public void createCampaignTracker(String externalMessageId, Long campaignId,String recipient) {

        CampaignTrackerEntity campaignTrackerEntity = new CampaignTrackerEntity();
        campaignTrackerEntity.setCampaignId(campaignId);
        campaignTrackerEntity.setCampaignMailerMessageId(externalMessageId);
        campaignTrackerEntity.setRecipient(recipient);

        campaignTrackerDao.save(campaignTrackerEntity);
    }
}
