package com.equivi.mailsy.service.quota;


import com.equivi.mailsy.dto.quota.QuotaDTO;

public interface QuotaService {
    QuotaDTO getQuota();

    QuotaDTO saveQuotaEntity(QuotaDTO quotaDTO);

    Long getEmailVerifierRemainingLimit();
}
