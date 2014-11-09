package com.equivi.mailsy.service.contact;


import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.QSubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.mysema.query.types.Predicate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ContactPredicate {


    @Resource
    private SubscriberGroupDao subscriberGroupDao;

    public Predicate getSubscriberContactEntity(Long subscriberContactId) {
        QSubscriberContactEntity subscriberContactPredicate = QSubscriberContactEntity.subscriberContactEntity;
        SubscriberGroupEntity subscriberGroupEntity = subscriberGroupDao.findOne(subscriberContactId);

        return subscriberContactPredicate.subscriberGroupEntity.eq(subscriberGroupEntity);
    }

}
