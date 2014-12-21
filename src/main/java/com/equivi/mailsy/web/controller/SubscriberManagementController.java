package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.data.entity.GenericStatus;
import com.equivi.mailsy.data.entity.SubscribeStatus;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import com.equivi.mailsy.service.constant.ConstantProperty;
import com.equivi.mailsy.service.contact.ContactManagementService;
import com.equivi.mailsy.service.exception.InvalidDataException;
import com.equivi.mailsy.service.subsriber.SubscriberGroupSearchFilter;
import com.equivi.mailsy.service.subsriber.SubscriberGroupService;
import com.equivi.mailsy.web.constant.PageConstant;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.equivi.mailsy.web.message.ErrorMessage;
import gnu.trove.map.hash.THashMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class SubscriberManagementController extends AbstractController {

    @Resource
    private WebConfiguration webConfiguration;
    @Resource
    private ErrorMessage errorMessage;
    @Resource
    private SubscriberGroupService subscriberService;
    @Resource
    private ContactManagementService contactManagementService;

    @RequestMapping(value = "/main/merchant/subscriber_management/{pageNumber}", method = RequestMethod.GET)
    public String getSubscriberManagementPage(@PathVariable Integer pageNumber, final HttpServletRequest request, Model model) {

        Page<SubscriberGroupEntity> subscriberGroupPage = subscriberService.listSubscriberGroup(buildMapFilter(request), pageNumber, webConfiguration.getMaxRecordsPerPage());

        setSubscribeGroupList(model, subscriberGroupPage);

        setPagination(model, subscriberGroupPage);

        return "subscriberManagementPage";
    }

    @RequestMapping(value = "/main/merchant/subscriber_management/create", method = RequestMethod.GET)
    public ModelAndView goToCreateSubscribeGroup(ModelAndView modelAndView) {

        SubscriberGroupDTO subscriberGroupDTO = new SubscriberGroupDTO();

        setPredefinedData(modelAndView, subscriberGroupDTO);

        modelAndView.setViewName("subscriberManagementAddPage");
        return modelAndView;
    }

    @RequestMapping(value = "/main/merchant/subscriber_management/subscriber_list/{subscriberGroupId}/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView goToEditSubscribeGroup(ModelAndView modelAndView, @PathVariable Long subscriberGroupId, @PathVariable Integer pageNumber, final HttpServletRequest httpServletRequest) {

        String nextPage = httpServletRequest.getParameter(PageConstant.NEXT_PAGE.getPageName());


        //Temporary enable no paging service
        SubscriberGroupDTO subscriberGroupDTO = subscriberService.getSubscriberGroupAndSubscriberList(subscriberGroupId);
        setPredefinedData(modelAndView, subscriberGroupDTO);

        modelAndView.addObject("mainScreen", nextPage);
        modelAndView.addObject("subscriberDTOList", subscriberGroupDTO.getSubscriberList());
        modelAndView.addObject("subscriberGroupDTO", subscriberGroupDTO);
        modelAndView.setViewName("subscriberManagementEditPage");
        return modelAndView;
    }


    @RequestMapping(value = "/main/merchant/subscriber_management/change_subscribe_status/{subscribeStatus}/{subscriberId}/{subscriberGroupId}", method = RequestMethod.PUT)
    @ResponseBody
    public String unsubscribeContact(@PathVariable String subscribeStatus, @PathVariable String subscriberId, @PathVariable String subscriberGroupId) {

        contactManagementService.subscribeUnsubscribeContact(Long.valueOf(subscriberId), SubscribeStatus.getStatusByDescription(subscribeStatus));

        return "SUCCESS";
    }

    @RequestMapping(value = "/main/merchant/subscriber_management/delete_subscriber/{subscriberId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteContact(@PathVariable String subscriberId) {

        try {
            contactManagementService.deleteSubscriberGroup(Long.valueOf(subscriberId));
        } catch (IllegalArgumentException iex) {
            //Get Error message from service
            return iex.getMessage();
        }

        return "SUCCESS";
    }

    @RequestMapping(value = "/main/merchant/subscriber_management/saveAddSubscriberGroup", method = RequestMethod.POST)
    public ModelAndView saveAddSubscriberGroup(@Valid SubscriberGroupDTO subscriberGroupDTO, BindingResult result, Locale locale) {

        ModelAndView modelAndView;
        try {
            if (result.hasErrors()) {
                modelAndView = new ModelAndView("subscriberManagementAddPage");
                modelAndView.addObject("errors", errorMessage.buildFormValidationErrorMessages(result, locale));
            } else {

                setDefaultValue(subscriberGroupDTO);
                SubscriberGroupEntity subscriberGroupEntity = subscriberService.saveSubscriberGroup(subscriberGroupDTO);

                modelAndView = new ModelAndView();
                String redirectData = "redirect:subscriber_list/" + subscriberGroupEntity.getId().toString() + "/1?nextPage=SUBSCRIBER_GROUP";

                modelAndView.setViewName(redirectData);
                return modelAndView;
            }
        } catch (InvalidDataException idex) {
            modelAndView = new ModelAndView("subscriberManagementAddPage");

            modelAndView.addObject("errors", errorMessage.buildServiceValidationErrorMessages(idex, locale));
        }

        return modelAndView;
    }

    @RequestMapping(value = "/main/merchant/subscriber_management/saveUpdateSubscriberGroup", method = RequestMethod.POST)
    public ModelAndView saveUpdateSubscriberGroup(@Valid SubscriberGroupDTO subscriberGroupDTO, HttpServletRequest httpServletRequest, BindingResult result, Locale locale) {

        ModelAndView modelAndView;
        try {
            if (result.hasErrors()) {
                modelAndView = new ModelAndView("subscriberManagementEditPage");
                modelAndView.addObject("errors", errorMessage.buildFormValidationErrorMessages(result, locale));
            } else {

                String active = httpServletRequest.getParameter("subscribe-status");

                if ("on".equals(active)) {
                    subscriberGroupDTO.setSubscriberGroupStatus(GenericStatus.ACTIVE.getStatusDescription());
                } else {
                    subscriberGroupDTO.setSubscriberGroupStatus(GenericStatus.INACTIVE.getStatusDescription());
                }
                subscriberService.saveSubscriberGroup(subscriberGroupDTO);

                modelAndView = new ModelAndView();
                String redirectData = "redirect:/main/merchant/subscriber_management/1";

                modelAndView.setViewName(redirectData);
                return modelAndView;
            }
        } catch (InvalidDataException idex) {
            modelAndView = new ModelAndView("subscriberManagementEditPage");

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

        SimpleDateFormat sdf = new SimpleDateFormat(ConstantProperty.DATE_TIME_FORMAT.getValue());
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


        Map<SubscriberGroupSearchFilter, String> filterMap = new THashMap<>();
        if (!StringUtils.isBlank(groupName)) {
            filterMap.put(SubscriberGroupSearchFilter.SUBSCRIBER_GROUP_NAME, groupName);
        }

        if (!StringUtils.isBlank(emailAddress)) {
            filterMap.put(SubscriberGroupSearchFilter.EMAIL_ADDRESS, emailAddress);
        }

        return filterMap;
    }
}
