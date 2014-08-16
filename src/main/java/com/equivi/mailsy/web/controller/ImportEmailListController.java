package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.service.emailverifier.EmailVerifierResponse;
import com.equivi.mailsy.service.emailverifier.ExcelEmailReader;
import com.equivi.mailsy.service.emailverifier.VerifierService;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.equivi.mailsy.web.context.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Controller
public class ImportEmailListController {


    @Resource
    private SessionUtil sessionUtil;

    @Resource
    private WebConfiguration webConfiguration;

    @Resource
    private ExcelEmailReader excelEmailReader;

    @Resource(name = "webEmailVerifierImpl")
    private VerifierService verifierService;

    private static final String UPLOAD_SUCCESS_MESSAGE = "label.import.upload.success.message";

    private static final String UPLOAD_FAILED_MESSAGE = "label.import.upload.failed.message";

    private static final String UPLOAD_FAILED_FILE_EMPTY_MESSAGE = "label.import.upload.failed.file_empty.message";

    private static final String WARNING_MESSAGE_AFTER_IMPORT = "label.import.warning.message";

    private static final String SUCCESS_UPLOAD_MESSAGE_KEY = "success_upload";

    private static final String WARNING_UPLOAD_MESSAGE_KEY = "warning_upload";

    private static final String ERROR_UPLOAD_MESSAGE_KEY = "error_upload";

    @RequestMapping(value = "/main/emailverifier/imports/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, final HttpServletRequest servletRequest, Model model) {
        if (!file.isEmpty()) {
            try {
                final String fileName = uploadFile(file, servletRequest);

                //Get Email address list from file
                List<String> emailAddressList = excelEmailReader.getEmailAddressList(fileName);

                List<EmailVerifierResponse> emailVerifierResponses = verifierService.filterValidEmail(emailAddressList);


                model.addAttribute("emailVerifierList", emailVerifierResponses);

            } catch (Exception e) {
                model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_MESSAGE);
            }
        } else {
            model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_FILE_EMPTY_MESSAGE);
        }

        return "emailVerifierPage";
    }

    private String uploadFile(MultipartFile file, HttpServletRequest servletRequest) throws IOException {
        byte[] bytes = file.getBytes();

        final String fileName = file.getOriginalFilename();
        final String targetUploadFileName = getTargetFileName(servletRequest, fileName);

        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(targetUploadFileName)));
        stream.write(bytes);
        stream.close();
        return targetUploadFileName;
    }


    private final String getTargetFileName(final HttpServletRequest request, final String fileName) {

        StringBuilder sbTargetFileName = new StringBuilder();
        sbTargetFileName.append(webConfiguration.getEmailVerifierLocation());
        sbTargetFileName.append("/");
        sbTargetFileName.append(sessionUtil.getCurrentUser(request).getUserId());
        sbTargetFileName.append("-");
        sbTargetFileName.append(System.currentTimeMillis());
        sbTargetFileName.append(fileName);
        return sbTargetFileName.toString();
    }
}
