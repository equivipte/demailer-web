package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.service.emailverifier.ExcelEmailReader;
import com.equivi.mailsy.util.EmailVerifierQuotaUtil;
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

    @Resource
    private EmailVerifierQuotaUtil emailVerifierQuotaUtil;

    @RequestMapping(value = "/main/merchant/emailverifier/imports/upload", method = RequestMethod.POST)
    public String handleFileUpload(final @RequestParam("file") MultipartFile file, final HttpServletRequest servletRequest, Model model) {
        if (!file.isEmpty()) {
            try {
                List<String> emailAddressList = excelEmailReader.getEmailAddressList(file);

                model = emailVerifierQuotaUtil.updateEmailVerificationModel(model, emailAddressList);

            } catch (Exception e) {
                model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_MESSAGE);
            }
        } else {
            model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_FILE_EMPTY_MESSAGE);
        }

        return "emailVerifierPage";
    }


}
