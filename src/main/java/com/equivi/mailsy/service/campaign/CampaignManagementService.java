package com.equivi.mailsy.service.campaign;


import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CampaignManagementService {


    /**
     * @param searchFilter
     * @param pageNumber
     * @param maxRecords
     * @return Page<CampaignEntity>
     */
    Page<CampaignEntity> listCampaignEntity(Map<CampaignSearchFilter, String> searchFilter, int pageNumber, int maxRecords);

    /**
     * @param campaignDTO
     */
    CampaignDTO saveCampaignDTO(CampaignDTO campaignDTO);

    /**
     *
     * @param campaignId
     */
    void setCampaignReadyToSendStatus(Long campaignId);

    /**
     *
     * @param campaignDTO
     */
    void saveCampaign(CampaignDTO campaignDTO);

    /**
     * @param campaignId
     * @return CampaignEntity
     */
    CampaignDTO getCampaign(Long campaignId);

    /**
     * @param campaignId
     */
    void deleteCampaign(Long campaignId);


}
