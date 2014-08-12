package com.equivi.mailsy.service.subsriber;

import com.equivi.mailsy.data.entity.SubscriberEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.subscriber.SubscriberDTO;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriberConverter {


    public SubscriberGroupDTO convertToSubscribeGroupDTO(SubscriberGroupEntity subscriberGroupEntity, List<SubscriberEntity> subscriberEntityList) {
        SubscriberGroupDTO subscriberGroupDTO = new SubscriberGroupDTO();

        subscriberGroupDTO.setSubscriberGroupName(subscriberGroupEntity.getGroupName());
        subscriberGroupDTO.setSubscriberGroupStatus(subscriberGroupEntity.getStatus().getStatusDescription());
        subscriberGroupDTO.setSubscriberList(convertToSubscriberDTOList(subscriberEntityList));

        return subscriberGroupDTO;
    }

    private List<SubscriberDTO> convertToSubscriberDTOList(List<SubscriberEntity> subscriberEntityList) {
        List<SubscriberDTO> subscriberDTOList = new ArrayList<>();
        if (subscriberEntityList != null && !subscriberEntityList.isEmpty()) {
            for (SubscriberEntity subscriberEntity : subscriberEntityList) {
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
