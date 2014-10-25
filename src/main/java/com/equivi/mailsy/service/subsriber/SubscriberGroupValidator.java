package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.QSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import com.equivi.mailsy.service.exception.InvalidDataException;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SubscriberGroupValidator {

    @Resource
    private SubscriberGroupDao subscriberGroupDao;

    private static final String SUBSCRIBE_GROUP_NAME_ALREADY_EXIST = "label.error.save.subscribe_group.already_exist";

    public void validate(SubscriberGroupEntity subscriberGroupEntity, SubscriberGroupDTO subscriberGroupDTO) {
        SubscriberGroupEntity subscriberGroupEntityFromDB = subscriberGroupDao.findOne(getSubscribeGroupQueryPredicateByGroupName(subscriberGroupDTO.getSubscriberGroupName()));

        if (subscriberGroupEntityFromDB != null) {
            if (subscriberGroupEntityFromDB.getId() != subscriberGroupEntity.getId()) {
                throw new InvalidDataException(SUBSCRIBE_GROUP_NAME_ALREADY_EXIST);
            }
        }
    }

    private Predicate getSubscribeGroupQueryPredicateByGroupName(String groupName) {

        QSubscriberGroupEntity qSubscriberGroupEntity = QSubscriberGroupEntity.subscriberGroupEntity;
        BooleanBuilder booleanMerchantPredicateBuilder = new BooleanBuilder();

        booleanMerchantPredicateBuilder.and(qSubscriberGroupEntity.groupName.eq(groupName));

        return booleanMerchantPredicateBuilder;
    }
}
