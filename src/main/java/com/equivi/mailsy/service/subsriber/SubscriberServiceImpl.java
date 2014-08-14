package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.dao.SubscriberContactDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.QSubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.google.common.collect.Lists;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class SubscriberServiceImpl implements SubscriberService {

    @Resource
    private SubscriberContactDao subscriberContactDao;

    @Resource
    private SubscriberGroupDao subscriberGroupDao;

    @Override
    public Page<ContactEntity> getSubscriberEntityPageBySubscriberGroupId(Long subscriberGroupId, int pageNumber, int maxRecords) {
        Pageable pageable = getPageable(pageNumber, maxRecords);

        Predicate subscribeGroupQueryPredicate = getSubscriberPredicate(subscriberGroupId);

        Page requestedPage = subscriberContactDao.findAll(subscribeGroupQueryPredicate, pageable);
        return requestedPage;
    }

    @Override
    public List<ContactEntity> getSubscriberEntityListBySubscriberGroupId(Long subscriberGroupId) {
        Predicate subscribeGroupQueryPredicate = getSubscriberPredicate(subscriberGroupId);

        List<SubscriberContactEntity> subscriberContactEntities = Lists.newArrayList(subscriberContactDao.findAll(subscribeGroupQueryPredicate));

        List<ContactEntity> contactEntityList = new ArrayList<>();
        for (SubscriberContactEntity subscriberContactEntity : subscriberContactEntities) {
            contactEntityList.add(subscriberContactEntity.getContactEntity());
        }
        return contactEntityList;
    }

    private Predicate getSubscriberPredicate(Long subscriberGroupId) {
        QSubscriberContactEntity qSubscriberContactEntity = QSubscriberContactEntity.subscriberContactEntity;

        SubscriberGroupEntity subscriberGroupEntity = subscriberGroupDao.findOne(subscriberGroupId);

        return qSubscriberContactEntity.subscriberGroupEntity.eq(subscriberGroupEntity);
    }

    private Pageable getPageable(int page, int maxRecords) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return new PageRequest(page, maxRecords, sort);
    }
}
