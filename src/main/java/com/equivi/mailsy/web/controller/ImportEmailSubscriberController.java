package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.contact.ContactDTO;
import com.equivi.mailsy.service.emailverifier.ExcelEmailReader;
import com.equivi.mailsy.service.subsriber.SubscriberService;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.equivi.mailsy.web.context.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ImportEmailSubscriberController {

    @Resource
    private SessionUtil sessionUtil;

    @Resource
    private WebConfiguration webConfiguration;

    @Resource
    private ExcelEmailReader excelEmailReader;

    @Resource
    private SubscriberService subscriberService;

    private static final String UPLOAD_FAILED_MESSAGE = "label.import.upload.failed.message";

    private static final String UPLOAD_FAILED_FILE_EMPTY_MESSAGE = "label.import.upload.failed.file_empty.message";

    private static final String ERROR_UPLOAD_MESSAGE_KEY = "error_upload";


    @RequestMapping(value = "/main/subscriber/imports/upload", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(final @RequestParam("file") MultipartFile file, final HttpServletRequest servletRequest, final Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if (!file.isEmpty()) {
            try {
                //Get ContactDTO list from file
                List<ContactDTO> contactDTOList = excelEmailReader.getContactDTOList(file);

                String subscriberGroupId = servletRequest.getParameter("subscriberGroupId");

                subscriberService.addSubscriberList(Long.valueOf(subscriberGroupId), contactDTOList);


                String redirectData = "redirect:/main/subscriber_management/subscriber_list/" + subscriberGroupId + "/1?nextPage=SUBSCRIBER_LIST";

                modelAndView.setViewName(redirectData);

            } catch (Exception e) {
                model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_MESSAGE);
            }
        } else {
            model.addAttribute(ERROR_UPLOAD_MESSAGE_KEY, UPLOAD_FAILED_FILE_EMPTY_MESSAGE);
        }

        return modelAndView;
    }

}
