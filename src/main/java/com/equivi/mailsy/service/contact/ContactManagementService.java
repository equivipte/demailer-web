package com.equivi.mailsy.service.contact;


import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.SubscribeStatus;
import com.equivi.mailsy.dto.contact.ContactDTO;
import com.equivi.mailsy.service.subsriber.SubscriberGroupSearchFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ContactManagementService {

    /**
     * @param searchFilter
     * @param pageNumber
     * @param maxRecords
     * @return Page<SubscriberGroupEntity>
     */
    Page<ContactEntity> listContactEntity(Map<SubscriberGroupSearchFilter, String> searchFilter, int pageNumber, int maxRecords);

    /**
     *
     * @return
     */
    List<ContactEntity> listContactEntity();

    /**
     * @param emailAddress
     * @return
     */
    ContactEntity getContactEntityByEmailAddress(final String emailAddress);

    /**
     * @param contactDTO
     * @return ContactEntity
     */
    ContactEntity saveContactEntity(final ContactDTO contactDTO);


    /**
     *
     * @param contactEntity
     * @return
     */
    ContactEntity saveContactEntity(final ContactEntity contactEntity);

    /**
     * @param contactId
     */
    void subscribeUnsubscribeContact(final Long contactId, final SubscribeStatus subscribeStatus);


    /**
     *
     * @param emailAddress
     */
    void updateUnsubscribeStatusFromMailgun(String emailAddress);

    /**
     *
     * @param contactId
     */
    void deleteContact(final Long contactId);
}


