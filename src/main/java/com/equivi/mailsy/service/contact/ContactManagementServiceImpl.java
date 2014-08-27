package com.equivi.mailsy.service.contact;

import com.equivi.mailsy.data.dao.ContactDao;
import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.QContactEntity;
import com.equivi.mailsy.data.entity.SubscribeStatus;
import com.equivi.mailsy.dto.contact.ContactDTO;
import com.equivi.mailsy.service.subsriber.SubscriberGroupSearchFilter;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ContactManagementServiceImpl implements ContactManagementService {

    @Resource
    private ContactDao contactDao;

    @Override
    public Page<ContactEntity> listContactEntity(Map<SubscriberGroupSearchFilter, String> searchFilter, int pageNumber, int maxRecords) {
        return null;
    }

    @Override
    public ContactEntity getContactEntityByEmailAddress(String emailAddress) {
        Predicate contactEntityPredicate = getContactPredicateByEmailAddress(emailAddress);

        return contactDao.findOne(contactEntityPredicate);
    }

    @Override
    public ContactEntity saveContactEntity(ContactDTO contactDTO) {
        ContactEntity contactEntity = null;
        if (contactDTO.getContactId() != null) {
            contactEntity = contactDao.findOne(contactDTO.getContactId());
        } else {
            contactEntity = new ContactEntity();
        }

        contactEntity.setEmailAddress(contactDTO.getEmailAddress());
        contactEntity.setFirstName(contactDTO.getFirstName());
        contactEntity.setLastName(contactDTO.getLastName());
        contactEntity.setAddress1(contactDTO.getAddress1());
        contactEntity.setAddress2(contactDTO.getAddress2());
        contactEntity.setAddress3(contactDTO.getAddress3());
        contactEntity.setCompanyName(contactDTO.getCompanyName());
        contactEntity.setZipCode(contactDTO.getZipCode());
        contactEntity.setCity(contactDTO.getCity());
        contactEntity.setFacebookAccount(contactDTO.getFacebookAccount());
        contactEntity.setTwitterAccount(contactDTO.getTwitterAccount());
        contactEntity.setCountry(contactDTO.getCountry());
        contactEntity.setPathAccount(contactDTO.getPathAccount());
        contactEntity.setSubscribeStatus(contactDTO.getSubscribeStatus());
        contactEntity.setZipCode(contactDTO.getZipCode());
        contactEntity.setPhone(contactDTO.getPhone());
        contactEntity.setSubscribeStatus(SubscribeStatus.SUBSCRIBED);
        return contactDao.save(contactEntity);
    }

    @Override
    public void subscribeUnsubscribeContact(final Long contactId, final SubscribeStatus subscribeStatus) {
        ContactEntity contactEntity = contactDao.findOne(contactId);
        contactEntity.setSubscribeStatus(subscribeStatus);

        contactDao.save(contactEntity);
    }


    private Predicate getContactPredicateByEmailAddress(String emailAddress) {
        QContactEntity contactPredicate = QContactEntity.contactEntity;

        return contactPredicate.emailAddress.eq(emailAddress);
    }
}
