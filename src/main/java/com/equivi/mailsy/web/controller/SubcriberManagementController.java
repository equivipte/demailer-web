package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.data.entity.GenericStatus;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import com.equivi.mailsy.service.exception.InvalidDataException;
import com.equivi.mailsy.service.subsriber.SubscriberGroupSearchFilter;
import com.equivi.mailsy.service.subsriber.SubscriberGroupService;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.equivi.mailsy.web.message.ErrorMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SubcriberManagementController extends AbstractController {

    @Resource
    private WebConfiguration webConfiguration;

    @Resource
    private ErrorMessage errorMessage;

    @Resource
    private SubscriberGroupService subscriberService;

    private static final String DATE_TIME_FORMAT = "dd/MM/YY HH:mm:ss";

    private static SimpleDateFormat sdf;

    static {
        sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
    }

    @RequestMapping(value = "/main/subscriber_management", method = RequestMethod.GET)
    public String getContactManagementPage(Model model) {

        List<SubscriberGroupDTO> subscriberGroupDTOList = new ArrayList<>();

        SubscriberGroupDTO contactDTO = new SubscriberGroupDTO();
        contactDTO.setSubscriberGroupName("Equivi Contact Group 1");
        contactDTO.setSubscriberGroupStatus("ACTIVE");
        contactDTO.setSubscriberLastUpdateDate("2014/07/30 10:00");

        SubscriberGroupDTO contactDTO2 = new SubscriberGroupDTO();
        contactDTO2.setSubscriberGroupName("Equivi Contact Group 1-1");
        contactDTO2.setSubscriberGroupStatus("ACTIVE");
        contactDTO2.setSubscriberLastUpdateDate("2014/07/30 13:00");

        SubscriberGroupDTO contactDTO3 = new SubscriberGroupDTO();
        contactDTO3.setSubscriberGroupName("Equivi Contact Group 2");
        contactDTO3.setSubscriberGroupStatus("INACTIVE");
        contactDTO3.setSubscriberLastUpdateDate("2014/07/30 11:00");

        SubscriberGroupDTO contactDTO4 = new SubscriberGroupDTO();
        contactDTO4.setSubscriberGroupName("Equivi Contact Group 3");
        contactDTO4.setSubscriberGroupStatus("DISABLED");
        contactDTO4.setSubscriberLastUpdateDate("2014/07/30 10:00");

        subscriberGroupDTOList.add(contactDTO);
        subscriberGroupDTOList.add(contactDTO2);
        subscriberGroupDTOList.add(contactDTO3);
        subscriberGroupDTOList.add(contactDTO4);

        model.addAttribute("subscriberGroupDTOList", subscriberGroupDTOList);

        return "subscriberManagementPage";
    }


    @RequestMapping(value = "/main/subscriber_management/{pageNumber}", method = RequestMethod.GET)
    public String getSubscriberManagementPage(@PathVariable Integer pageNumber, final HttpServletRequest request, Model model) {

        Page<SubscriberGroupEntity> subscriberGroupPage = subscriberService.listSubscriberGroup(buildMapFilter(request), pageNumber, webConfiguration.getMaxRecordsPerPage());

        setSubscribeGroupList(model, subscriberGroupPage);

        setPagination(model, subscriberGroupPage);

        return "subscriberManagementPage";
    }

    @RequestMapping(value = "/main/subscriber_management/create", method = RequestMethod.GET)
    public ModelAndView goToCreateSubscribeGroup(ModelAndView modelAndView) {

        SubscriberGroupDTO subscriberGroupDTO = new SubscriberGroupDTO();

        setPredefinedData(modelAndView, subscriberGroupDTO);

        modelAndView.setViewName("subscriberManagementAddPage");
        return modelAndView;
    }

    @RequestMapping(value = "/main/subscriber_management/subscriber_list/{subscriberGroupId}/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView goToEditSubscribeGroup(ModelAndView modelAndView, @PathVariable Long subscriberGroupId, @PathVariable Integer pageNumber) {

        SubscriberGroupDTO subscriberGroupDTO = subscriberService.getSubscriberGroupAndSubscriberList(subscriberGroupId,pageNumber,webConfiguration.getMaxRecordsPerPage());
        setPredefinedData(modelAndView, subscriberGroupDTO);

        modelAndView.addObject("subscriberDTOList",subscriberGroupDTO.getSubscriberList());
        modelAndView.addObject("subscriberGroupDTO",subscriberGroupDTO);
        modelAndView.setViewName("subscriberManagementEditPage");
        return modelAndView;
    }


    @RequestMapping(value = "/main/subscriber_management/saveAddSubscriberGroup", method = RequestMethod.POST)
    public ModelAndView saveAddSubscriberGroup(@Valid SubscriberGroupDTO subscriberGroupDTO, HttpServletRequest httpServletRequest, BindingResult result, Locale locale) {

        ModelAndView modelAndView;
        try {
            if (result.hasErrors()) {
                modelAndView = new ModelAndView("subscriberManagementAddPage");
                modelAndView.addObject("errors", errorMessage.buildFormValidationErrorMessages(result, locale));
            } else {

                setDefaultValue(subscriberGroupDTO);
                SubscriberGroupEntity subscriberGroupEntity = subscriberService.saveSubscriberGroup(subscriberGroupDTO);

                modelAndView = new ModelAndView();
                String redirectData = "redirect:subscriber_list/" + subscriberGroupEntity.getId().toString() + "/1";

                modelAndView.setViewName(redirectData);
                return modelAndView;
            }
        } catch (InvalidDataException idex) {
            modelAndView = new ModelAndView("subscriberManagementAddPage");

            modelAndView.addObject("errors", errorMessage.buildServiceValidationErrorMessages(idex, locale));
        }

        return modelAndView;
    }

    private SubscriberGroupDTO setDefaultValue(SubscriberGroupDTO subscriberGroupDTO) {
        subscriberGroupDTO.setSubscriberGroupStatus(GenericStatus.ACTIVE.getStatusDescription());
        return subscriberGroupDTO;
    }

    private void setPredefinedData(ModelAndView modelAndView, SubscriberGroupDTO subscriberGroupDTO) {
        modelAndView.addObject("subscriberGroupDTO", subscriberGroupDTO);
    }

    private void setSubscribeGroupList(Model model, Page<SubscriberGroupEntity> subscriberGroupEntityPage) {
        model.addAttribute("subscriberGroupDTOList", convertToSubscribeGroupDTOList(subscriberGroupEntityPage.getContent()));
    }

    private List<SubscriberGroupDTO> convertToSubscribeGroupDTOList(List<SubscriberGroupEntity> subscriberGroupEntityList) {
        List<SubscriberGroupDTO> subscriberGroupDTOList = new ArrayList<>();

        if (subscriberGroupEntityList != null && !subscriberGroupEntityList.isEmpty()) {
            for (SubscriberGroupEntity subscriberGroupEntity : subscriberGroupEntityList) {
                SubscriberGroupDTO subscriberGroupDTO = new SubscriberGroupDTO();
                subscriberGroupDTO.setId(subscriberGroupEntity.getId());
                subscriberGroupDTO.setSubscriberGroupName(subscriberGroupEntity.getGroupName());
                subscriberGroupDTO.setSubscriberGroupStatus(subscriberGroupEntity.getStatus().getStatusDescription());
                subscriberGroupDTO.setSubscriberLastUpdateDate(sdf.format(subscriberGroupEntity.getLastUpdatedDateTime()));
                subscriberGroupDTOList.add(subscriberGroupDTO);
            }
        }

        return subscriberGroupDTOList;
    }


    private Map<SubscriberGroupSearchFilter, String> buildMapFilter(HttpServletRequest request) {

        String groupName = request.getParameter(SubscriberGroupSearchFilter.SUBSCRIBER_GROUP_NAME.getFilterName());
        String emailAddress = request.getParameter(SubscriberGroupSearchFilter.EMAIL_ADDRESS.getFilterName());


        Map<SubscriberGroupSearchFilter, String> filterMap = new HashMap<>();
        if (!StringUtils.isBlank(groupName)) {
            filterMap.put(SubscriberGroupSearchFilter.SUBSCRIBER_GROUP_NAME, groupName);
        }

        if (!StringUtils.isBlank(emailAddress)) {
            filterMap.put(SubscriberGroupSearchFilter.EMAIL_ADDRESS, emailAddress);
        }

        return filterMap;
    }
}
