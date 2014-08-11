package com.equivi.mailsy.service.subsriber;


import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface SubscriberGroupService {

    /**
     * @param searchFilter
     * @param pageNumber
     * @param maxRecords
     * @return Page<SubscriberGroupEntity>
     */
    Page<SubscriberGroupEntity> listSubscriberGroup(Map<SubscriberGroupSearchFilter, String> searchFilter, int pageNumber, int maxRecords);

    /**
     * @param subscriberGroupId
     * @return SubscriberGroupEntity
     */
    SubscriberGroupEntity getSubscriberGroup(final Long subscriberGroupId);

    /**
     * @param subscriberGroupId
     */
    void deleteSubscriberGroup(final Long subscriberGroupId);

    /**
     *
     * @param subscriberGroupDTO
     * @return SubscriberGroupEntity
     */
    SubscriberGroupEntity saveSubscriberGroup(SubscriberGroupDTO subscriberGroupDTO);
}
