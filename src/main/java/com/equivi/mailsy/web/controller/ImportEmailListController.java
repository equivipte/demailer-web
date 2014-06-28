package com.equivi.mailsy.web.controller;


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


@Controller
public class ImportEmailListController {


    @Resource
    private SessionUtil sessionUtil;

    @Resource
    private WebConfiguration webConfiguration;

    private static final String UPLOAD_SUCCESS_MESSAGE = "label.import.upload.success.message";

    private static final String UPLOAD_FAILED_MESSAGE = "label.import.upload.failed.message";

    private static final String UPLOAD_FAILED_FILE_EMPTY_MESSAGE = "label.import.upload.failed.file_empty.message";

    private static final String WARNING_MESSAGE_AFTER_IMPORT = "label.import.warning.message";

    private static final String SUCCESS_UPLOAD_MESSAGE_KEY = "success_upload";

    private static final String WARNING_UPLOAD_MESSAGE_KEY = "warning_upload";

    private static final String ERROR_UPLOAD_MESSAGE_KEY = "error_upload";

    @RequestMapping(value = "/main/admin/imports", method = RequestMethod.GET)
    public String importEmailVerifierController(HttpServletRequest request) {
        return "emailVerifierPage";
    }

    @RequestMapping(value = "/main/admin/imports/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, final HttpServletRequest servletRequest, Model model) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                final String fileName = file.getOriginalFilename();
                final String targetUploadFileName = getTargetFileName(servletRequest, fileName);

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(targetUploadFileName)));
                stream.write(bytes);
                stream.close();

                model.addAttribute("fileName", file.getOriginalFilename());
                model.addAttribute(SUCCESS_UPLOAD_MESSAGE_KEY, UPLOAD_SUCCESS_MESSAGE);
                model.addAttribute(WARNING_UPLOAD_MESSAGE_KEY, WARNING_MESSAGE_AFTER_IMPORT);

            } catch (Exception e) {
                model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_MESSAGE);
            }
        } else {
            model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_FILE_EMPTY_MESSAGE);
        }

        return "importManagementPage";
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
