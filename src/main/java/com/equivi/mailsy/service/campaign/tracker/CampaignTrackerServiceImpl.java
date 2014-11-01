package com.equivi.mailsy.service.campaign.tracker;


import com.equivi.mailsy.data.dao.CampaignTrackerDao;
import com.equivi.mailsy.data.entity.CampaignTrackerEntity;
import com.equivi.mailsy.data.entity.QCampaignTrackerEntity;
import com.equivi.mailsy.dto.campaign.CampaignStatisticDTO;
import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CampaignTrackerServiceImpl implements CampaignTrackerService {

    @Resource
    private CampaignTrackerDao campaignTrackerDao;

    private Logger LOG = LoggerFactory.getLogger(CampaignTrackerServiceImpl.class);

    @Override
    @Transactional(readOnly = false)
    public void createCampaignTracker(String externalMessageId, Long campaignId, String recipient) {

        LOG.error("Creating campaign tracker:" + externalMessageId + " " + campaignId);
        CampaignTrackerEntity campaignTrackerEntity = new CampaignTrackerEntity();
        campaignTrackerEntity.setCampaignId(campaignId);

        externalMessageId = StringUtils.replace(externalMessageId, "<", "");
        externalMessageId = StringUtils.replace(externalMessageId, ">", "");

        campaignTrackerEntity.setCampaignMailerMessageId(externalMessageId);
        campaignTrackerEntity.setRecipient(recipient);

        campaignTrackerDao.save(campaignTrackerEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public CampaignTrackerEntity getCampaignTrackerEntity(Long campaignTrackerId) {
        return campaignTrackerDao.findOne(campaignTrackerId);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveCampaignTrackerEntity(CampaignTrackerEntity campaignTrackerEntity) {
        campaignTrackerDao.save(campaignTrackerEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignTrackerEntity> getCampaignTrackerEntityList(Map<CampaignTrackerSearchFilter, String> campaignTrackerSearchFilterStringMap) {

        Predicate campaignTrackerPredicate = getCampaignTrackerPredicate(campaignTrackerSearchFilterStringMap);

        Iterable<CampaignTrackerEntity> campaignTrackerEntities = campaignTrackerDao.findAll(campaignTrackerPredicate);

        if (campaignTrackerEntities.iterator().hasNext()) {
            return Lists.newArrayList(campaignTrackerEntities);
        }
        return null;
    }

    @Override
    public CampaignStatisticDTO getCampaignStatistic(Long campaignId) {

        QCampaignTrackerEntity qCampaignTrackerEntity = QCampaignTrackerEntity.campaignTrackerEntity;
        Iterable<CampaignTrackerEntity> campaignTrackerEntities = campaignTrackerDao.findAll((qCampaignTrackerEntity.campaignId.eq(campaignId)));

        if (campaignTrackerEntities != null && campaignTrackerEntities.iterator().hasNext()) {
            CampaignStatisticDTO campaignStatisticDTO = buildCampaignStatisticDTO(campaignTrackerEntities);
            campaignStatisticDTO.setCampaignId(campaignId);
            return campaignStatisticDTO;
        }

        return new CampaignStatisticDTO();
    }

    private CampaignStatisticDTO buildCampaignStatisticDTO(Iterable<CampaignTrackerEntity> campaignTrackerEntities) {
        CampaignStatisticDTO campaignStatisticDTO = new CampaignStatisticDTO();

        Long totalOpen = 0L;
        Long totalDelivered = 0L;
        Long totalBounced = 0L;
        Long totalUnsubscribed = 0L;
        Long totalSent = Long.valueOf(Lists.newArrayList(campaignTrackerEntities).size());

        Long totalOpenUsingMobile = 0L;
        Long totalOpenUsingDesktop = 0L;
        Long totalOpenUsingTablet = 0L;
        Long totalOpenUsingUnknownDevice = 0L;


        if (campaignTrackerEntities != null && campaignTrackerEntities.iterator().hasNext()) {
            for (CampaignTrackerEntity campaignTrackerEntity : campaignTrackerEntities) {
                if (campaignTrackerEntity.isOpened()) {
                    totalOpen += 1;
                    String clientDeviceType = campaignTrackerEntity.getClientDeviceType();
                    if (isClientDevice(clientDeviceType, ClientDeviceType.MOBILE)) {
                        totalOpenUsingMobile += 1;
                    } else if (isClientDevice(clientDeviceType, ClientDeviceType.DESKTOP)) {
                        totalOpenUsingDesktop += 1;
                    } else if (isClientDevice(clientDeviceType, ClientDeviceType.TABLET)) {
                        totalOpenUsingTablet += 1;
                    } else {
                        totalOpenUsingUnknownDevice += 1;
                    }
                }
                if (campaignTrackerEntity.isBounced()) {
                    totalBounced += 1L;
                }
                if (campaignTrackerEntity.isUnsubscribed()) {
                    totalUnsubscribed += 1L;
                }
                if (campaignTrackerEntity.isDelivered()) {
                    totalDelivered += 1L;
                }
            }
        }


        campaignStatisticDTO.setTotalOpened(totalOpen);
        campaignStatisticDTO.setTotalDelivered(totalDelivered);
        campaignStatisticDTO.setTotalFailed(totalBounced);
        campaignStatisticDTO.setTotalOpenUsingMobile(totalOpenUsingMobile);
        campaignStatisticDTO.setTotalOpenUsingDesktop(totalOpenUsingDesktop);
        campaignStatisticDTO.setTotalOpenUsingTablet(totalOpenUsingTablet);
        campaignStatisticDTO.setTotalOpenUsingOthers(totalOpenUsingUnknownDevice);
        campaignStatisticDTO.setTotalUnsubscribed(totalUnsubscribed);
        campaignStatisticDTO.setTotalSent(totalSent);

        //Set Percentage
        Double openPercentage = 0D;
        if (totalDelivered > 0) {
            openPercentage = Double.valueOf(totalOpen * 100 / totalDelivered);
        }
        Double failurePercentage = Double.valueOf(totalBounced * 100 / totalSent);
        Double deliverPercentage = Double.valueOf(totalDelivered * 100 / totalSent);

        Double desktopPercentage = 0D;
        Double mobilePercentage = 0D;
        Double tabletPercentage = 0D;
        Double unknownPercentage = 0D;

        if (totalOpen != null && totalOpen > 0L) {
            desktopPercentage = Double.valueOf(totalOpenUsingDesktop * 100 / totalOpen);
            mobilePercentage = Double.valueOf(totalOpenUsingMobile * 100 / totalOpen);
            tabletPercentage = Double.valueOf(totalOpenUsingTablet * 100 / totalOpen);
            unknownPercentage = Double.valueOf(totalOpenUsingUnknownDevice * 100 / totalOpen);
        }

        campaignStatisticDTO.setOpenUsingDesktopPercentage(desktopPercentage);
        campaignStatisticDTO.setOpenUsingMobilePercentage(mobilePercentage);
        campaignStatisticDTO.setOpenUsingTabletPercentage(tabletPercentage);
        campaignStatisticDTO.setOpenUsingOtherPercentage(unknownPercentage);

        campaignStatisticDTO.setOpenMailPercentage(openPercentage);
        campaignStatisticDTO.setFailedMailPercentage(failurePercentage);
        campaignStatisticDTO.setDeliverMailPercentage(deliverPercentage);

        adjustFailPercentageIfAllDeliverSuccesfully(campaignStatisticDTO);

        return campaignStatisticDTO;
    }

    //Adjust Fail percentage if all deliver succesfully eventually
    private void adjustFailPercentageIfAllDeliverSuccesfully(CampaignStatisticDTO campaignStatisticDTO) {
        if (campaignStatisticDTO.getDeliverMailPercentage() == 100D) {
            campaignStatisticDTO.setTotalFailed(0L);
            campaignStatisticDTO.setFailedMailPercentage(0D);
        }
    }

    private boolean isClientDevice(String clientDeviceType, ClientDeviceType deviceType) {
        return !StringUtils.isEmpty(clientDeviceType) && clientDeviceType.equalsIgnoreCase(deviceType.getClientDeviceType());
    }

    private Predicate getCampaignTrackerPredicate(Map<CampaignTrackerSearchFilter, String> campaignTrackerSearchFilterStringMap) {

        QCampaignTrackerEntity qCampaignTrackerEntity = QCampaignTrackerEntity.campaignTrackerEntity;

        BooleanBuilder booleanBuilderCampaignTrackerEntity = new BooleanBuilder();

        if (campaignTrackerSearchFilterStringMap != null && campaignTrackerSearchFilterStringMap.size() > 0) {
            if (campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.CAMPAIGN_ID) != null) {
                booleanBuilderCampaignTrackerEntity.or(qCampaignTrackerEntity.campaignId.eq(Long.valueOf(campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.CAMPAIGN_ID.getFilterName()))));
            }
            if (campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.CAMPAIGN_MAILER_MESSAGE_ID) != null) {
                booleanBuilderCampaignTrackerEntity.or(qCampaignTrackerEntity.campaignMailerMessageId.eq(campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.CAMPAIGN_MAILER_MESSAGE_ID)));
            }
            if (campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.RECIPIENT) != null) {
                booleanBuilderCampaignTrackerEntity.or(qCampaignTrackerEntity.campaignMailerMessageId.eq(campaignTrackerSearchFilterStringMap.get(CampaignTrackerSearchFilter.RECIPIENT)));
            }
        }

        return booleanBuilderCampaignTrackerEntity;
    }
}
