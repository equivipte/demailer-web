package com.equivi.mailsy.service.contact;

import com.equivi.mailsy.data.dao.CampaignSubscriberGroupDao;
import com.equivi.mailsy.data.dao.ContactDao;
import com.equivi.mailsy.data.dao.SubscriberContactDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.QContactEntity;
import com.equivi.mailsy.data.entity.SubscribeStatus;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.dto.contact.ContactDTO;
import com.equivi.mailsy.service.campaign.CampaignSubscriberGroupPredicate;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.equivi.mailsy.service.subsriber.SubscriberGroupSearchFilter;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ContactManagementServiceImpl implements ContactManagementService {

    static final String ERROR_DELETE_SUBSCRIBER_GROUP_MSG_USED_BY_CAMPAIGN = "Unable to delete subscriber group id, there's already campaign using this subscriber group";
    @Resource
    private ContactDao contactDao;
    @Resource
    private CampaignSubscriberGroupPredicate campaignSubscriberGroupPredicate;
    @Resource
    private CampaignSubscriberGroupDao campaignSubscriberGroupDao;
    @Resource
    private SubscriberGroupDao subscriberGroupDao;
    @Resource
    private SubscriberContactDao subscriberContactDao;
    @Resource
    private ContactPredicate contactPredicate;
    @Resource(name = "mailgunRestTemplateEmailService")
    private MailgunService mailgunService;

    @Override
    public Page<ContactEntity> listContactEntity(Map<SubscriberGroupSearchFilter, String> searchFilter, int pageNumber, int maxRecords) {
        return null;
    }

    @Override
    public List<ContactEntity> listContactEntity() {
        return contactDao.findAll();
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
    public ContactEntity saveContactEntity(ContactEntity contactEntity) {
        return contactDao.save(contactEntity);
    }

    @Override
    public void subscribeUnsubscribeContact(final Long contactId, final SubscribeStatus subscribeStatus) {
        ContactEntity contactEntity = contactDao.findOne(contactId);
        contactEntity.setSubscribeStatus(subscribeStatus);

        contactDao.save(contactEntity);

        updateMailgunUnsubscriberList(contactEntity);

    }

    @Override
    public void updateUnsubscribeStatusFromMailgun(String emailAddress) {
        if (mailgunService.isUnsubscribe(null, emailAddress)) {
            //Update unsubscribed in contact
            ContactEntity contactEntity = getContactEntityByEmailAddress(emailAddress);
            contactEntity.setSubscribeStatus(SubscribeStatus.UNSUBSCRIBED);

            saveContactEntity(contactEntity);
        }
    }

    private void updateMailgunUnsubscriberList(ContactEntity contactEntity) {
        if (SubscribeStatus.SUBSCRIBED == contactEntity.getSubscribeStatus()) {
            mailgunService.deleteUnsubscribe(null, contactEntity.getEmailAddress());
        } else if (SubscribeStatus.UNSUBSCRIBED == contactEntity.getSubscribeStatus()) {
            mailgunService.registerUnsubscribe(null, contactEntity.getEmailAddress());
        }
    }

    @Override
    public void deleteSubscriberGroup(Long subscriberGroupId) {

        Iterable<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntityList = campaignSubscriberGroupDao.findAll(campaignSubscriberGroupPredicate.getCampaignSubscriberGroupEntityBySubscriberGroup(subscriberGroupId));

        if (campaignSubscriberGroupEntityList != null && campaignSubscriberGroupEntityList.iterator().hasNext()) {
            throw new IllegalArgumentException(ERROR_DELETE_SUBSCRIBER_GROUP_MSG_USED_BY_CAMPAIGN);
        }

        //Delete subscriber contact entity
        Iterable<SubscriberContactEntity> subscriberContactEntities = subscriberContactDao.findAll(contactPredicate.getSubscriberContactEntity(subscriberGroupId));

        if (subscriberContactEntities != null && subscriberContactEntities.iterator().hasNext()) {
            subscriberContactDao.delete(subscriberContactEntities);
        }

        subscriberGroupDao.delete(subscriberGroupId);
    }


    private Predicate getContactPredicateByEmailAddress(String emailAddress) {
        QContactEntity contactPredicate = QContactEntity.contactEntity;

        return contactPredicate.emailAddress.eq(emailAddress);
    }
}
