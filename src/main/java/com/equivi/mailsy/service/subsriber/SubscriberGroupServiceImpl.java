package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.dao.ContactDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.*;
import com.equivi.mailsy.dto.subscriber.SubscriberDTO;
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
    private ContactDao subscriberDao;

    @Resource
    private SubscriberService subscriberService;

    @Resource
    private SubscriberConverter subscriberConverter;

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
    public SubscriberGroupDTO getSubscriberGroupAndSubscriberList(Long subscriberGroupId, int pageNumber, int maxRecords) {
        SubscriberGroupEntity subscriberGroupEntity = subscriberGroupDao.findOne(subscriberGroupId);
        Page<ContactEntity> subscriberEntityList = subscriberService.getSubscriberEntityPageBySubscriberGroupId(subscriberGroupId, pageNumber, maxRecords);
        return subscriberConverter.convertToSubscribeGroupDTO(subscriberGroupEntity, subscriberEntityList.getContent());
    }

    @Override
    public void deleteSubscriberGroup(Long subscriberGroupId) {
        subscriberGroupDao.delete(subscriberGroupId);
    }

    @Override
    public SubscriberGroupEntity saveSubscriberGroup(SubscriberGroupDTO subscriberGroupDTO) {
        SubscriberGroupEntity subscriberGroupEntity = convertToEntity(subscriberGroupDTO);

        subscriberGroupValidator.validate(subscriberGroupEntity, subscriberGroupDTO);

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
            QSubscriberContactEntity qSubscriberEntity = QSubscriberContactEntity.subscriberContactEntity;
            Predicate emailPredicate = qSubscriberEntity.contactEntity.emailAddress.like("%" + filterMap.get(SubscriberGroupSearchFilter.EMAIL_ADDRESS) + "%");

            Iterable<ContactEntity> subscriberEntityList = subscriberDao.findAll(emailPredicate);

            if (subscriberEntityList.iterator().hasNext()) {
                for (ContactEntity subscriberEntity : subscriberEntityList) {
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

        List<ContactEntity> subscriberEntityList = getSubscriberList(subscriberGroupEntity, subscriberGroupDTO.getSubscriberList());

        subscriberGroupEntity.setSubscribeEntityList(subscriberEntityList);

        return subscriberGroupEntity;
    }

    private List<ContactEntity> getSubscriberList(SubscriberGroupEntity subscriberGroupEntity, List<SubscriberDTO> subscriberDTOList) {

        List<ContactEntity> subscriberEntityList = new ArrayList<>();
        if (subscriberDTOList != null && !subscriberDTOList.isEmpty()) {
            for (SubscriberDTO subscriberDTO : subscriberDTOList) {
                String emailAddress = subscriberDTO.getEmailAddress();
                ContactEntity subscriberEntity = getSubscriberByEmailAddress(emailAddress);
                if (subscriberEntity != null) {
                    subscriberEntityList.add(subscriberEntity);
                } else {
                    subscriberEntityList.add(createNewSubscriber(subscriberGroupEntity, emailAddress));
                }
            }
        }
        return subscriberEntityList;
    }

    private ContactEntity getSubscriberByEmailAddress(String emailAddress) {

        QContactEntity qContactEntity = QContactEntity.contactEntity;
        return subscriberDao.findOne(qContactEntity.emailAddress.eq(emailAddress));

    }

    private ContactEntity createNewSubscriber(SubscriberGroupEntity subscriberGroupEntity, String emailAddress) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setEmailAddress(emailAddress);
        contactEntity.setDomainName(getDomainName(emailAddress));
        contactEntity.setSubscribeStatus(SubscribeStatus.SUBSCRIBED);
        return contactEntity;
    }

    private String getDomainName(String emailAddress) {
        String[] splitEmailDomain = emailAddress.split("@");
        return splitEmailDomain[1];
    }


}
