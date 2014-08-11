package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.dao.SubscriberDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.GenericStatus;
import com.equivi.mailsy.data.entity.QSubscriberEntity;
import com.equivi.mailsy.data.entity.QSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.SubscribeStatus;
import com.equivi.mailsy.data.entity.SubscriberEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class SubscriberGroupServiceImpl implements SubscriberGroupService {

    @Resource
    private SubscriberGroupDao subscriberGroupDao;

    @Resource
    private SubscriberDao subscriberDao;

    @Resource
    private SubscriberGroupValidator subscriberGroupValidator;

    @Override
    @Transactional(readOnly = true)
    public Page<SubscriberGroupEntity> listSubscriberGroup(Map<SubscriberGroupSearchFilter, String> searchFilter, int pageNumber, int maxRecords) {

        Pageable pageable = getPageable(pageNumber - 1, maxRecords);
        Predicate subscribeGroupQueryPredicate = getSubscribeGroupQueryPredicate(searchFilter);

        Page requestedPage = subscriberGroupDao.findAll(subscribeGroupQueryPredicate, pageable);

        return requestedPage;
    }

    @Override
    public SubscriberGroupEntity getSubscriberGroup(Long subscriberGroupId) {
        return subscriberGroupDao.findOne(subscriberGroupId);
    }

    @Override
    public void deleteSubscriberGroup(Long subscriberGroupId) {
        subscriberGroupDao.delete(subscriberGroupId);
    }

    @Override
    public SubscriberGroupEntity saveSubscriberGroup(SubscriberGroupDTO subscriberGroupDTO) {
        SubscriberGroupEntity subscriberGroupEntity = convertToEntity(subscriberGroupDTO);

        subscriberGroupValidator.validate(subscriberGroupEntity,subscriberGroupDTO);

        subscriberGroupEntity.setLastUpdatedDateTime(new Date());
        return subscriberGroupDao.save(subscriberGroupEntity);
    }

    private Pageable getPageable(int page, int maxRecords) {
        Sort sort = new Sort(Sort.Direction.ASC, "groupName");
        return new PageRequest(page, maxRecords, sort);
    }


    private Predicate getSubscribeGroupQueryPredicate(Map<SubscriberGroupSearchFilter, String> filterMap) {

        QSubscriberGroupEntity qSubscriberGroupEntity = QSubscriberGroupEntity.subscriberGroupEntity;
        BooleanBuilder booleanMerchantPredicateBuilder = new BooleanBuilder();

        if (filterMap.get(SubscriberGroupSearchFilter.SUBSCRIBER_GROUP_NAME) != null) {
            booleanMerchantPredicateBuilder.or(qSubscriberGroupEntity.groupName.like("%" + filterMap.get(SubscriberGroupSearchFilter.SUBSCRIBER_GROUP_NAME) + "%"));

        }

        if (filterMap.get(SubscriberGroupSearchFilter.EMAIL_ADDRESS) != null) {
            QSubscriberEntity qSubscriberEntity = QSubscriberEntity.subscriberEntity;
            Predicate emailPredicate = qSubscriberEntity.emailAddress.like("%" + filterMap.get(SubscriberGroupSearchFilter.EMAIL_ADDRESS) + "%");

            Iterable<SubscriberEntity> subscriberEntityList = subscriberDao.findAll(emailPredicate);

            if (subscriberEntityList.iterator().hasNext()) {
                for (SubscriberEntity subscriberEntity : subscriberEntityList) {
                    booleanMerchantPredicateBuilder.and(qSubscriberGroupEntity.subscribeEntityList.contains(subscriberEntity));
                }
            } else {
                booleanMerchantPredicateBuilder.and(qSubscriberGroupEntity.subscribeEntityList.isEmpty());
            }

        }

        return booleanMerchantPredicateBuilder;
    }

    private SubscriberGroupEntity convertToEntity(SubscriberGroupDTO subscriberGroupDTO) {
        SubscriberGroupEntity subscriberGroupEntity;
        if (subscriberGroupDTO.getId() == null) {
            subscriberGroupEntity = new SubscriberGroupEntity();
        } else {
            subscriberGroupEntity = subscriberGroupDao.findOne(subscriberGroupDTO.getId());
        }

        subscriberGroupEntity.setGroupName(subscriberGroupDTO.getSubscriberGroupName());
        subscriberGroupEntity.setStatus(GenericStatus.getStatusByDescription(subscriberGroupDTO.getSubscriberGroupStatus()));

        List<SubscriberEntity> subscriberEntityList = getSubscriberList(subscriberGroupEntity, subscriberGroupDTO.getSubscriberList());

        subscriberGroupEntity.setSubscribeEntityList(subscriberEntityList);

        return subscriberGroupEntity;
    }

    private List<SubscriberEntity> getSubscriberList(SubscriberGroupEntity subscriberGroupEntity, List<String> emailAddressList) {

        List<SubscriberEntity> subscriberEntityList = new ArrayList<>();
        if (emailAddressList != null && !emailAddressList.isEmpty()) {
            for (String emailAddress : emailAddressList) {
                SubscriberEntity subscriberEntity = getSubscriberByEmailAddress(emailAddress);
                if (subscriberEntity != null) {
                    subscriberEntityList.add(subscriberEntity);
                } else {
                    subscriberEntityList.add(createNewSubscriber(subscriberGroupEntity, emailAddress));
                }
            }
        }
        return subscriberEntityList;
    }

    private SubscriberEntity getSubscriberByEmailAddress(String emailAddress) {

        QSubscriberEntity qSubscriberEntity = QSubscriberEntity.subscriberEntity;
        return subscriberDao.findOne(qSubscriberEntity.emailAddress.eq(emailAddress));

    }

    private SubscriberEntity createNewSubscriber(SubscriberGroupEntity subscriberGroupEntity, String emailAddress) {
        SubscriberEntity subscriberEntity = new SubscriberEntity();
        subscriberEntity.setEmailAddress(emailAddress);
        subscriberEntity.setDomainName(getDomainName(emailAddress));
        subscriberEntity.setSubscriberGroupEntity(subscriberGroupEntity);
        subscriberEntity.setSubscribeStatus(SubscribeStatus.SUBSCRIBED);
        return subscriberEntity;
    }

    private String getDomainName(String emailAddress) {
        String[] splitEmailDomain = emailAddress.split("@");
        return splitEmailDomain[1];
    }
}
