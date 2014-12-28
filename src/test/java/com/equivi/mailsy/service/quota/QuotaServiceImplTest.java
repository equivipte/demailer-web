package com.equivi.mailsy.service.quota;

import com.equivi.mailsy.data.dao.QuotaDao;
import com.equivi.mailsy.data.entity.QuotaEntity;
import com.equivi.mailsy.dto.quota.QuotaDTO;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuotaServiceImplTest {

    @Mock
    private QuotaDao quotaDao;

    @InjectMocks
    private QuotaServiceImpl quotaService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetQuota() throws Exception {
        //Given
        QuotaEntity quotaEntity = getQuotaEntity();
        when(quotaDao.findAll()).thenReturn(Lists.newArrayList(quotaEntity));

        //When
        QuotaDTO quotaDTO = quotaService.getQuota();

        //Then
        assertEquals(quotaDTO.getCurrentEmailsSent(), quotaEntity.getCurrentEmailsSent());
        assertEquals(quotaDTO.getCurrentEmailsVerified(), quotaEntity.getCurrentEmailsVerified());
        assertEquals(quotaDTO.getEmailSendingQuota(), quotaEntity.getEmailSendingQuota());
        assertEquals(quotaDTO.getEmailVerifyQuota(), quotaEntity.getEmailVerifyQuota());

        verify(quotaDao).findAll();

    }

    private QuotaEntity getQuotaEntity() {
        QuotaEntity quotaEntity = new QuotaEntity();
        quotaEntity.setCurrentEmailsVerified(1);
        quotaEntity.setEmailVerifyQuota(30);
        quotaEntity.setCurrentEmailsSent(2);
        quotaEntity.setEmailSendingQuota(30);

        return quotaEntity;
    }

    @Test
    public void testGetEmailVerifierRemainingLimit() throws Exception {
        //Given
        QuotaEntity quotaEntity = getQuotaEntity();
        when(quotaDao.findAll()).thenReturn(Lists.newArrayList(quotaEntity));

        //When & Then
        assertEquals(29, quotaService.getEmailVerifierRemainingLimit().intValue());
    }
}