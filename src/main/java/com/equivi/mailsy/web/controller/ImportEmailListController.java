package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.dto.emailer.EmailVerifierResult;
import com.equivi.mailsy.dto.quota.QuotaDTO;
import com.equivi.mailsy.service.emailverifier.ExcelEmailReader;
import com.equivi.mailsy.service.quota.QuotaService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ImportEmailListController {

    private static final String UPLOAD_FAILED_MESSAGE = "label.import.upload.failed.message";
    private static final String UPLOAD_FAILED_FILE_EMPTY_MESSAGE = "label.import.upload.failed.file_empty.message";
    private static final String ERROR_UPLOAD_MESSAGE_KEY = "error_upload";
    @Resource
    private ExcelEmailReader excelEmailReader;

    @Autowired
    private QuotaService quotaService;

    @RequestMapping(value = "/main/merchant/emailverifier/imports/upload", method = RequestMethod.POST)
    public String handleFileUpload(final @RequestParam("file") MultipartFile file, final HttpServletRequest servletRequest, final Model model) {
        if (!file.isEmpty()) {
            try {
                List<String> emailAddressList = excelEmailReader.getEmailAddressList(file);

                List<EmailVerifierResult> emailVerifierResponses = Lists.newArrayList();

                if(CollectionUtils.isNotEmpty(emailAddressList)) {
                    List<String> emailsToBeVerified;
                    boolean partiallyVerified = false;

                    QuotaDTO quota = quotaService.getQuota();
                    long emailVerifyQuota = quota.getEmailVerifyQuota();
                    long currentEmailsVerified = quota.getCurrentEmailsVerified();
                    int remainingEmailsToBeVerified = (int) (emailVerifyQuota - currentEmailsVerified);

                    if(remainingEmailsToBeVerified < emailAddressList.size()) {
                        partiallyVerified = true;

                        emailsToBeVerified = emailAddressList.subList(0, remainingEmailsToBeVerified);
                    } else {
                        emailsToBeVerified = emailAddressList;
                    }

                    QuotaDTO quotaDTO = new QuotaDTO();
                    quotaDTO.setCurrentEmailsVerified(emailsToBeVerified.size());

                    quotaDTO = quotaService.saveQuotaEntity(quotaDTO);

                    model.addAttribute("quota", quotaDTO);

                    for (String emailAddress : emailsToBeVerified) {
                        emailVerifierResponses.add(new EmailVerifierResult().setEmailAddressResult(emailAddress).setStatusResult("UNAVAILABLE").setInfoDetailResult("Unavailable"));
                    }

                    model.addAttribute("emailVerifierList", emailVerifierResponses);
                    model.addAttribute("partiallyVerified", partiallyVerified);
                }

            } catch (Exception e) {
                model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_MESSAGE);
            }
        } else {
            model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_FILE_EMPTY_MESSAGE);
        }

        return "emailVerifierPage";
    }
}
