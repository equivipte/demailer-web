package com.equivi.mailsy.util;


import com.equivi.mailsy.dto.emailer.EmailVerifierResult;
import com.equivi.mailsy.dto.quota.QuotaDTO;
import com.equivi.mailsy.service.quota.QuotaService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EmailVerifierQuotaUtil {

    @Resource
    private QuotaService quotaService;

    public Model updateEmailVerificationModel(Model model, List<String> emailAddressList) {
        validateExceedQuota(model);

        boolean partiallyVerified = checkIfPartiallyVerified(emailAddressList.size());

        List<String> emailsToBeVerified = getEmailToBeVerified(emailAddressList, partiallyVerified);

        List<EmailVerifierResult> emailVerifierResponses = buildEmailVerifierResponse(emailsToBeVerified);

        QuotaDTO quotaDTO = saveEmailVerifyQuotaRemaining(emailsToBeVerified);

        model.addAttribute("quota", quotaDTO);
        model.addAttribute("emailVerifierList", emailVerifierResponses);
        model.addAttribute("partiallyVerified", partiallyVerified);

        return model;
    }

    public Model validateExceedQuota(Model model) {
        QuotaDTO quota = quotaService.getQuota();
        boolean quotaExceeded = quotaService.getEmailVerifierRemainingLimit() <= 0;

        if (quotaExceeded) {
            model.addAttribute("quotaExceeded", quotaExceeded);
        }
        model.addAttribute("quota", quota);
        return model;
    }


    private List<EmailVerifierResult> buildEmailVerifierResponse(List<String> emailsToBeVerified) {
        List<EmailVerifierResult> emailVerifierResponses = Lists.newArrayList();

        for (String emailAddress : emailsToBeVerified) {
            emailVerifierResponses.add(new EmailVerifierResult().
                    setEmailAddressResult(emailAddress).
                    setStatusResult("UNAVAILABLE").
                    setInfoDetailResult("Unavailable"));
        }
        return emailVerifierResponses;
    }

    private QuotaDTO saveEmailVerifyQuotaRemaining(List<String> emailsToBeVerified) {
        QuotaDTO quotaDTO = new QuotaDTO();
        quotaDTO.setCurrentEmailsVerified(emailsToBeVerified.size());

        quotaDTO = quotaService.saveQuotaEntity(quotaDTO);
        return quotaDTO;
    }

    private List<String> getEmailToBeVerified(List<String> emailAddressList, boolean partiallyVerified) {
        List<String> emailsToBeVerified;
        if (partiallyVerified) {
            emailsToBeVerified = emailAddressList.subList(0, quotaService.getEmailVerifierRemainingLimit().intValue());
        } else {
            emailsToBeVerified = emailAddressList;
        }
        return emailsToBeVerified;
    }

    private boolean checkIfPartiallyVerified(int emailAddressToVerify) {
        Long remainingEmailsToBeVerified = quotaService.getEmailVerifierRemainingLimit();

        return remainingEmailsToBeVerified < emailAddressToVerify;
    }

}
