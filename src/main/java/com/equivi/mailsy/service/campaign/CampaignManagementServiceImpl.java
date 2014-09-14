package com.equivi.mailsy.service.campaign;

import com.equivi.mailsy.data.dao.CampaignDao;
import com.equivi.mailsy.data.dao.CampaignSubscriberGroupDao;
import com.equivi.mailsy.data.dao.SubscriberGroupDao;
import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.CampaignStatus;
import com.equivi.mailsy.data.entity.CampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.QCampaignEntity;
import com.equivi.mailsy.data.entity.QCampaignSubscriberGroupEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CampaignManagementServiceImpl implements CampaignManagementService {

    @Resource
    private CampaignDao campaignDao;

    @Resource
    private CampaignSubscriberGroupDao campaignSubscriberGroupDao;

    @Resource
    private SubscriberGroupDao subscriberGroupDao;

    @Resource
    private CampaignConverter campaignConverter;

    @Resource
    private CampaignValidator campaignValidator;

    @Override
    public Page<CampaignEntity> listCampaignEntity(Map<CampaignSearchFilter, String> searchFilter, int pageNumber, int maxRecords) {

        Pageable pageable = getPageable(pageNumber - 1, maxRecords);
        Predicate campaignQueryPredicate = getCampaignPredicate(searchFilter);

        Page<CampaignEntity> requestedPage = campaignDao.findAll(campaignQueryPredicate, pageable);

        return requestedPage;
    }

    private Pageable getPageable(int page, int maxRecords) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return new PageRequest(page, maxRecords, sort);
    }

    private Predicate getCampaignPredicate(Map<CampaignSearchFilter, String> filterMap) {

        QCampaignEntity qCampaignEntity = QCampaignEntity.campaignEntity;
        BooleanBuilder booleanMerchantPredicateBuilder = new BooleanBuilder();

        if (filterMap.get(CampaignSearchFilter.CAMPAIGN_SUBJECT) != null) {
            booleanMerchantPredicateBuilder.or(qCampaignEntity.campaignEntity.emaiSubject.like("%" + filterMap.get(CampaignSearchFilter.CAMPAIGN_SUBJECT) + "%"));

        }

        return booleanMerchantPredicateBuilder;
    }

    @Override
    public CampaignDTO saveCampaignDTO(CampaignDTO campaignDTO) {

        CampaignEntity campaignEntity = saveCampaignEntity(campaignDTO);

        CampaignDTO updatedCampaignDTO = campaignConverter.convertToCampaignDTO(campaignEntity);

        if (campaignEntity.getId() != null) {
            updateCampaignSubscriberGroup(campaignDTO, campaignEntity);

            updatedCampaignDTO = assignSubscriberGroupIdToDTO(updatedCampaignDTO,campaignDTO);
        }


        return updatedCampaignDTO;
    }

    @Override
    public void setCampaignReadyToSendStatus(Long campaignId) {
        CampaignEntity campaignEntity = campaignDao.findOne(campaignId);
        campaignEntity.setCampaignStatus(CampaignStatus.OUTBOX);
        campaignDao.save(campaignEntity);
    }

    private CampaignEntity saveCampaignEntity(CampaignDTO campaignDTO) {
        campaignValidator.validate(campaignDTO);

        CampaignEntity campaignEntity = campaignConverter.convertToCampaignEntity(campaignDTO);

        campaignEntity = campaignDao.save(campaignEntity);

        updateCampaignSubscriberGroup(campaignDTO, campaignEntity);
        return campaignEntity;
    }

    @Override
    public void saveCampaign(CampaignDTO campaignDTO) {
        saveCampaignEntity(campaignDTO);
    }

    private CampaignDTO assignSubscriberGroupIdToDTO(CampaignDTO updatedCampaignDTO,CampaignDTO campaignDTO) {
        if (campaignDTO.getId() != null) {
            Iterable<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntities = campaignSubscriberGroupDao.findAll(buildCampaignSubscriberGroupPredicate(campaignDTO.getId()));
            List<Long> subscriberGroupIds = new ArrayList<>();
            for (CampaignSubscriberGroupEntity campaignSubscriberGroupEntity : campaignSubscriberGroupEntities) {

                Long subscriberGroupId = campaignSubscriberGroupEntity.getSubscriberGroupEntity().getId();
                subscriberGroupIds.add(subscriberGroupId);

            }
            updatedCampaignDTO.setSubscriberGroupIds(subscriberGroupIds);
        }
        return updatedCampaignDTO;
    }

    private void updateCampaignSubscriberGroup(CampaignDTO campaignDTO, CampaignEntity campaignEntity) {
        if (campaignDTO.getSubscriberGroupIds() != null && !campaignDTO.getSubscriberGroupIds().isEmpty()) {
            //Delete existing campaign subscriber group for this Campaign Entity
            deleteCampaignSubscriberGroup(campaignDTO);

            //Save new relation campaign subscriber group
            saveCampaignSubcriberGroup(campaignDTO, campaignEntity);
        }
    }

    private void deleteCampaignSubscriberGroup(CampaignDTO campaignDTO) {
        Predicate getCampaignSubscriberGroupPredicate = buildCampaignSubscriberGroupPredicate(campaignDTO.getId());
        Iterable<CampaignSubscriberGroupEntity> campaignSubscriberGroupEntities = campaignSubscriberGroupDao.findAll(getCampaignSubscriberGroupPredicate);
        campaignSubscriberGroupDao.delete(campaignSubscriberGroupEntities);
    }

    private Predicate buildCampaignSubscriberGroupPredicate(Long campaignId) {
        if (campaignId != null) {
            CampaignEntity campaignEntity = campaignDao.findOne(campaignId);

            QCampaignSubscriberGroupEntity qCampaignSubscriberGroupEntity = QCampaignSubscriberGroupEntity.campaignSubscriberGroupEntity;

            return qCampaignSubscriberGroupEntity.campaignEntity.eq(campaignEntity);

        }
        return null;
    }

    private void saveCampaignSubcriberGroup(CampaignDTO campaignDTO, CampaignEntity campaignEntity) {
        for (Long subscriberGroupId : campaignDTO.getSubscriberGroupIds()) {
            SubscriberGroupEntity subscriberGroupEntity = subscriberGroupDao.findOne(subscriberGroupId);

            CampaignSubscriberGroupEntity campaignSubscriberGroupEntity = new CampaignSubscriberGroupEntity();
            campaignSubscriberGroupEntity.setCampaignEntity(campaignEntity);
            campaignSubscriberGroupEntity.setSubscriberGroupEntity(subscriberGroupEntity);
            campaignSubscriberGroupDao.save(campaignSubscriberGroupEntity);
        }
    }

    @Override
    public CampaignDTO getCampaign(Long campaignId) {

        CampaignDTO campaignDTO = campaignConverter.convertToCampaignDTO(campaignDao.findOne(campaignId));

        assignSubscriberGroupIdToDTO(campaignDTO,campaignDTO);

        return campaignDTO;
    }

    @Override
    public void deleteCampaign(Long campaignId) {

        campaignDao.delete(campaignId);
    }
}
