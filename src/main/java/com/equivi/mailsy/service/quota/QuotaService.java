package com.equivi.mailsy.service.quota;


import com.equivi.mailsy.data.entity.QuotaEntity;
import com.equivi.mailsy.dto.quota.QuotaDTO;

public interface QuotaService {
    QuotaDTO getQuota();

    QuotaDTO saveQuotaEntity(QuotaDTO quotaDTO);
}
