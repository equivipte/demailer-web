package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.dao.SubscriberDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.QSubscriberEntity;
import com.equivi.mailsy.data.entity.SubscriberEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.google.common.collect.Lists;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SubscriberServiceImpl implements SubscriberService {

    @Resource
    private SubscriberDao subscriberDao;

    @Resource
    private SubscriberGroupDao subscriberGroupDao;

    @Override
    public Page<SubscriberEntity> getSubscriberEntityPageBySubscriberGroupId(Long subscriberGroupId, int pageNumber, int maxRecords) {
        Pageable pageable = getPageable(pageNumber, maxRecords);

        Predicate subscribeGroupQueryPredicate = getSubscriberPredicate(subscriberGroupId);

        Page requestedPage = subscriberDao.findAll(subscribeGroupQueryPredicate, pageable);
        return requestedPage;
    }

    @Override
    public List<SubscriberEntity> getSubscriberEntityListBySubscriberGroupId(Long subscriberGroupId) {
        Predicate subscribeGroupQueryPredicate = getSubscriberPredicate(subscriberGroupId);

        return Lists.newArrayList(subscriberDao.findAll(subscribeGroupQueryPredicate));
    }

    private Predicate getSubscriberPredicate(Long subscriberGroupId) {
        QSubscriberEntity qSubscriberEntity = QSubscriberEntity.subscriberEntity;

        SubscriberGroupEntity subscriberGroupEntity = subscriberGroupDao.findOne(subscriberGroupId);

        return qSubscriberEntity.subscriberGroupEntity.eq(subscriberGroupEntity);
    }

    private Pageable getPageable(int page, int maxRecords) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return new PageRequest(page, maxRecords, sort);
    }
}
