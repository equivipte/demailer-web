package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.entity.SubscriberEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubscriberService {


    /**
     * @param subscriberGroupId
     * @param pageNumber
     * @param maxRecords
     * @return
     */
    Page<SubscriberEntity> getSubscriberEntityPageBySubscriberGroupId(Long subscriberGroupId, int pageNumber, int maxRecords);

    /**
     *
     * @param subscriberGroupId
     * @return
     */
    List<SubscriberEntity> getSubscriberEntityListBySubscriberGroupId(Long subscriberGroupId);

}
