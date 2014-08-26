package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.dto.contact.ContactDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubscriberService {


    /**
     * @param subscriberGroupId
     * @param pageNumber
     * @param maxRecords
     * @return
     */
    Page<SubscriberContactEntity> getSubscriberEntityPageBySubscriberGroupId(Long subscriberGroupId, int pageNumber, int maxRecords);

    /**
     * @param subscriberGroupId
     * @return
     */
    List<ContactEntity> getSubscriberEntityListBySubscriberGroupId(Long subscriberGroupId);


    /**
     * @param contactDTOList
     */
    void addSubscriberList(Long subscriberGroupId, List<ContactDTO> contactDTOList);

}
