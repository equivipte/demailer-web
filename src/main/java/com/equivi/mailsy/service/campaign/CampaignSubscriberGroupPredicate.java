package com.equivi.mailsy.service.campaign;

import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.QCampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.mysema.query.types.Predicate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CampaignSubscriberGroupPredicate {

    @Resource
    private SubscriberGroupDao subscriberGroupDao;

    public Predicate getCampaignSubscriberGroupEntityBySubscriberGroup(Long subscriberGroupId) {
        QCampaignSubscriberGroupEntity campaignSubscriberGroupEntity = QCampaignSubscriberGroupEntity.campaignSubscriberGroupEntity;
        SubscriberGroupEntity subscriberGroupEntity = subscriberGroupDao.findOne(subscriberGroupId);

        return campaignSubscriberGroupEntity.subscriberGroupEntity.eq(subscriberGroupEntity);
    }

}
