package com.equivi.mailsy.service.campaign;

import com.equivi.mailsy.data.dao.CampaignDao;
import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.QCampaignEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CampaignManagementServiceImpl implements CampaignManagementService {

    @Resource
    private CampaignDao campaignDao;

    @Resource
    private CampaignConverter campaignConverter;


    @Override
    public Page<CampaignEntity> listCampaignEntity(Map<CampaignSearchFilter, String> searchFilter, int pageNumber, int maxRecords) {

        Pageable pageable = getPageable(pageNumber - 1, maxRecords);
        Predicate campaignQueryPredicate = getCampaignPredicate(searchFilter);

        Page requestedPage = campaignDao.findAll(campaignQueryPredicate, pageable);

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
    public CampaignDTO saveCampaign(CampaignDTO campaignDTO) {

        CampaignEntity campaignEntity = campaignConverter.convertToCampaignEntity(campaignDTO);

        return campaignConverter.convertToCampaignDTO(campaignDao.save(campaignEntity));
    }

    @Override
    public CampaignDTO getCampaign(Long campaignId) {
        return campaignConverter.convertToCampaignDTO(campaignDao.findOne(campaignId));
    }

    @Override
    public void deleteCampaign(Long campaignId) {

        campaignDao.delete(campaignId);
    }
}
