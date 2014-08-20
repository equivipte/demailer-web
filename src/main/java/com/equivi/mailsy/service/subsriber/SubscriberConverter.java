package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.subscriber.SubscriberDTO;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriberConverter {


    public SubscriberGroupDTO convertToSubscribeGroupDTO(SubscriberGroupEntity subscriberGroupEntity, List<ContactEntity> subscriberEntityList) {
        SubscriberGroupDTO subscriberGroupDTO = new SubscriberGroupDTO();

        subscriberGroupDTO.setId(subscriberGroupEntity.getId());
        subscriberGroupDTO.setSubscriberGroupName(subscriberGroupEntity.getGroupName());
        subscriberGroupDTO.setSubscriberGroupStatus(subscriberGroupEntity.getStatus().getStatusDescription());
        subscriberGroupDTO.setSubscriberList(convertToSubscriberDTOList(subscriberEntityList));

        return subscriberGroupDTO;
    }

    private List<SubscriberDTO> convertToSubscriberDTOList(List<ContactEntity> contactEntityList) {
        List<SubscriberDTO> subscriberDTOList = new ArrayList<>();
        if (contactEntityList != null && !contactEntityList.isEmpty()) {
            for (ContactEntity subscriberEntity : contactEntityList) {
                SubscriberDTO subscriberDTO = new SubscriberDTO();
                subscriberDTO.setEmailAddress(subscriberEntity.getEmailAddress());
                subscriberDTO.setFirstName(subscriberEntity.getFirstName());
                subscriberDTO.setLastName(subscriberEntity.getLastName());
                subscriberDTO.setCompanyName(subscriberEntity.getCompanyName());
                subscriberDTO.setCity(subscriberEntity.getCity());
                subscriberDTO.setCountry(subscriberEntity.getCountry());
                subscriberDTO.setDomainName(subscriberEntity.getDomainName());
                subscriberDTO.setPhone(subscriberEntity.getPhone());
                subscriberDTO.setZipCode(subscriberEntity.getZipCode());
                subscriberDTOList.add(subscriberDTO);
            }
        }
        return subscriberDTOList;
    }

}
