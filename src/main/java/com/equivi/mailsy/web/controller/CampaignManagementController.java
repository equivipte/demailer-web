package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.CampaignStatus;
import com.equivi.mailsy.data.entity.SubscriberContactEntity;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import com.equivi.mailsy.service.campaign.CampaignManagementService;
import com.equivi.mailsy.service.campaign.CampaignSearchFilter;
import com.equivi.mailsy.service.constant.ConstantProperty;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class CampaignManagementController extends AbstractController {

    @Resource
    private WebConfiguration webConfiguration;

    @Resource
    private ErrorMessage errorMessage;

    @Resource
    private CampaignManagementService campaignManagementService;

    @Resource
    private SubscriberGroupService subscriberGroupService;

    private static final String EMAIL_CONTENT_PAGE = "campaignManagementEmailContentPage";

    private static final String DELIVERY_PAGE = "campaignManagementEmailDeliveryPage";

    private static SimpleDateFormat sdf;

    static {
        sdf = new SimpleDateFormat(ConstantProperty.DATE_TIME_FORMAT.getValue());
    }


    @RequestMapping(value = "/main/merchant/campaignmanagement", method = RequestMethod.GET)
    public String getCampaignManagementPage() {
        return "campaignManagementPage";
    }

    @RequestMapping(value = "/main/merchant/campaign_management/{pageNumber}", method = RequestMethod.GET)
    public String getCampaignManagementPage(@PathVariable Integer pageNumber, final HttpServletRequest request, Model model) {

        Page<CampaignEntity> campaignPage = campaignManagementService.listCampaignEntity(buildMapFilter(request), pageNumber, webConfiguration.getMaxRecordsPerPage());

        setCampaignList(model, campaignPage);

        setPagination(model, campaignPage);

        return "campaignManagementPage";
    }

    @RequestMapping(value = "/main/merchant/campaign_management/create", method = RequestMethod.GET)
    public ModelAndView goToCreateCampaign(ModelAndView modelAndView) {

        CampaignDTO campaignDTO = new CampaignDTO();

        setPredefinedData(modelAndView, campaignDTO);

        modelAndView.setViewName("campaignManagementAddPage");
        return modelAndView;
    }

    @RequestMapping(value = "/main/merchant/campaign_management/saveAddCampaign", method = RequestMethod.POST)
    public ModelAndView saveAddCampaign(@Valid CampaignDTO campaignDTO, BindingResult result, Locale locale) {

        ModelAndView modelAndView;
        try {
            if (result.hasErrors()) {
                modelAndView = new ModelAndView("campaignManagementAddPage");
                modelAndView.addObject("errors", errorMessage.buildFormValidationErrorMessages(result, locale));
            } else {

                campaignDTO.setCampaignStatus(CampaignStatus.DRAFT.getCampaignStatusDescription());
                campaignDTO = campaignManagementService.saveCampaign(campaignDTO);

                modelAndView = new ModelAndView();
                String redirectData = "redirect:" + campaignDTO.getId().toString() + "/" + EMAIL_CONTENT_PAGE;

                modelAndView.setViewName(redirectData);
                return modelAndView;
            }
        } catch (InvalidDataException idex) {
            modelAndView = new ModelAndView("campaignManagementAddPage");

            modelAndView.addObject("errors", errorMessage.buildServiceValidationErrorMessages(idex, locale));
        }

        return modelAndView;
    }

    @RequestMapping(value = "/main/merchant/campaign_management/{campaignId}/saveCampaignEmailContent", method = RequestMethod.POST)
    @ResponseBody
    public String saveUpdateEmailContent(@RequestBody String emailContent, @PathVariable String campaignId) {

        CampaignDTO campaignDTO = campaignManagementService.getCampaign(Long.valueOf(campaignId));
        campaignDTO.setEmailContent(emailContent);

        campaignManagementService.saveCampaign(campaignDTO);

        return "SUCCESS";
    }


    @RequestMapping(value = "/main/merchant/campaign_management/{campaignId}/{pageName}", method = RequestMethod.GET)
    public ModelAndView goToEditCampaignPage(ModelAndView modelAndView, @PathVariable Long campaignId, @PathVariable String pageName, final HttpServletRequest httpServletRequest) {
        CampaignDTO campaignDTO = campaignManagementService.getCampaign(campaignId);

        setPredefinedData(modelAndView, campaignDTO);

        modelAndView.setViewName(pageName);
        return modelAndView;
    }

    @RequestMapping(value = "/main/merchant/campaign_management/{campaignId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCampaign(@PathVariable Long campaignId) {

        try {
            campaignManagementService.deleteCampaign(campaignId);
        } catch (Exception ex) {
            return "general.exception.delete";
        }

        return "SUCCESS";
    }


    @RequestMapping(value = "/main/merchant/campaign_management/email_content/{campaignId}", method = RequestMethod.GET)
    @ResponseBody
    public String getEmailContent(@PathVariable Long campaignId) {

        try {
            CampaignDTO campaignDTO = campaignManagementService.getCampaign(campaignId);
            return campaignDTO.getEmailContent();
        } catch (Exception ex) {
            return "general.exception.delete";
        }
    }

    private void setPredefinedData(ModelAndView modelAndView, CampaignDTO campaignDTO) {
        modelAndView.addObject("campaignDTO", campaignDTO);
        modelAndView.addObject("subscriberGroupDTOList", getSubscriberGroupDTOList());
    }

    private List<SubscriberGroupDTO> getSubscriberGroupDTOList() {
        Page<SubscriberGroupEntity> subscriberContactEntities = subscriberGroupService.listSubscriberGroup(new HashMap<SubscriberGroupSearchFilter, String>(), 1, webConfiguration.getMaxRecordsPerPage());

        return convertToSubscribeGroupDTOList(subscriberContactEntities.getContent());
    }

    //TODO: move duplicated code in SubscriberManagementController to converter
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


    private Map<CampaignSearchFilter, String> buildMapFilter(HttpServletRequest request) {

        String campaignSubjectName = request.getParameter(CampaignSearchFilter.CAMPAIGN_SUBJECT.getFilterName());


        Map<CampaignSearchFilter, String> filterMap = new HashMap<>();
        if (!StringUtils.isBlank(campaignSubjectName)) {
            filterMap.put(CampaignSearchFilter.CAMPAIGN_SUBJECT, campaignSubjectName);
        }


        return filterMap;
    }

    private void setCampaignList(Model model, Page<CampaignEntity> subscriberGroupEntityPage) {
        model.addAttribute("campaignDTOList", convertToCampaignDTO(subscriberGroupEntityPage.getContent()));
    }

    private List<CampaignDTO> convertToCampaignDTO(List<CampaignEntity> content) {
        List<CampaignDTO> campaignDTOList = new ArrayList<>();
        if (content != null && !content.isEmpty()) {
            for (CampaignEntity campaignEntity : content) {
                CampaignDTO campaignDTO = new CampaignDTO();
                campaignDTO.setId(campaignEntity.getId());
                campaignDTO.setCampaignName(campaignEntity.getCampaignName());
                campaignDTO.setEmailSubject(campaignEntity.getEmaiSubject());
                campaignDTO.setCampaignStatus(campaignEntity.getCampaignStatus().getCampaignStatusDescription());
                if (campaignEntity.getLastUpdatedDateTime() != null) {
                    campaignDTO.setLastUpdateDate(sdf.format(campaignEntity.getLastUpdatedDateTime()));
                }
                campaignDTOList.add(campaignDTO);
            }
        }
        return campaignDTOList;
    }

}
