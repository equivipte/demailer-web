package com.equivi.mailsy.service.contact;

import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.dto.contact.ContactDTO;
import org.springframework.stereotype.Component;

@Component
public class ContactConverter {

    public ContactEntity convertToEntity(ContactDTO contactDTO) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setEmailAddress(contactDTO.getEmailAddress());
        contactEntity.setFirstName(contactDTO.getFirstName());
        contactEntity.setLastName(contactDTO.getLastName());
        contactEntity.setCity(contactDTO.getCity());
        contactEntity.setCountry(contactDTO.getCountry());
        contactEntity.setPhone(contactDTO.getPhone());
        contactEntity.setAddress1(contactDTO.getAddress1());
        contactEntity.setAddress2(contactDTO.getAddress2());
        contactEntity.setAddress3(contactDTO.getAddress3());
        contactEntity.setFacebookAccount(contactDTO.getFacebookAccount());
        contactEntity.setTwitterAccount(contactDTO.getTwitterAccount());
        contactEntity.setPathAccount(contactDTO.getPathAccount());
        contactEntity.setZipCode(contactDTO.getZipCode());
        return contactEntity;
    }
}
