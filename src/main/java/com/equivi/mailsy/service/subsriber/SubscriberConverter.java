package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.subscriber.SubscriberDTO;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriberConverter {


    public SubscriberGroupDTO convertToSubscribeGroupDTO(SubscriberGroupEntity subscriberGroupEntity, List<SubscriberContactEntity> subscriberEntityList) {
        SubscriberGroupDTO subscriberGroupDTO = new SubscriberGroupDTO();

        subscriberGroupDTO.setId(subscriberGroupEntity.getId());
        subscriberGroupDTO.setSubscriberGroupName(subscriberGroupEntity.getGroupName());
        subscriberGroupDTO.setSubscriberGroupStatus(subscriberGroupEntity.getStatus().getStatusDescription());
        subscriberGroupDTO.setSubscriberList(convertToSubscriberDTOList(subscriberEntityList));

        return subscriberGroupDTO;
    }

    private List<SubscriberDTO> convertToSubscriberDTOList(List<SubscriberContactEntity> contactEntityList) {
        List<SubscriberDTO> subscriberDTOList = new ArrayList<>();
        if (contactEntityList != null && !contactEntityList.isEmpty()) {
            for (SubscriberContactEntity subscriberContactEntity : contactEntityList) {
                SubscriberDTO subscriberDTO = new SubscriberDTO();
                subscriberDTO.setEmailAddress(subscriberContactEntity.getContactEntity().getEmailAddress());
                subscriberDTO.setFirstName(subscriberContactEntity.getContactEntity().getFirstName());
                subscriberDTO.setLastName(subscriberContactEntity.getContactEntity().getLastName());
                subscriberDTO.setCompanyName(subscriberContactEntity.getContactEntity().getCompanyName());
                subscriberDTO.setCity(subscriberContactEntity.getContactEntity().getCity());
                subscriberDTO.setCountry(subscriberContactEntity.getContactEntity().getCountry());
                subscriberDTO.setDomainName(subscriberContactEntity.getContactEntity().getDomainName());
                subscriberDTO.setPhone(subscriberContactEntity.getContactEntity().getPhone());
                subscriberDTO.setZipCode(subscriberContactEntity.getContactEntity().getZipCode());
                subscriberDTO.setSubscribeStatus(subscriberContactEntity.getContactEntity().getSubscribeStatus().getStatusDescription());
                subscriberDTOList.add(subscriberDTO);
            }
        }
        return subscriberDTOList;
    }

}
