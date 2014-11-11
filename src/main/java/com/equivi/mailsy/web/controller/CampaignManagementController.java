package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.data.entity.CampaignStatus;
import com.equivi.mailsy.data.entity.ContactEntity;
import com.equivi.mailsy.data.entity.SubscribeStatus;
import com.equivi.mailsy.data.entity.SubscriberGroupEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.equivi.mailsy.dto.campaign.CampaignStatisticDTO;
import com.equivi.mailsy.dto.subscriber.SubscriberGroupDTO;
import com.equivi.mailsy.service.campaign.CampaignManagementService;
import com.equivi.mailsy.service.campaign.CampaignSearchFilter;
import com.equivi.mailsy.service.campaign.tracker.CampaignTrackerService;
import com.equivi.mailsy.service.constant.ConstantProperty;
import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.exception.InvalidDataException;
import com.equivi.mailsy.service.mailgun.MailgunService;
import com.equivi.mailsy.service.subsriber.SubscriberGroupSearchFilter;
import com.equivi.mailsy.service.subsriber.SubscriberGroupService;
import com.equivi.mailsy.util.WebConfigUtil;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.equivi.mailsy.web.message.ErrorMessage;
import com.google.common.collect.Lists;
import gnu.trove.map.hash.THashMap;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

@Controller
public class CampaignManagementController extends AbstractController {

    private static final String EMAIL_CONTENT_PAGE = "campaignManagementEmailContentPage";
    private static final String DELIVERY_PAGE = "campaignManagementEmailDeliveryPage";
    private static SimpleDateFormat dateTimeFormat;

    static {
        dateTimeFormat = new SimpleDateFormat(ConstantProperty.DATE_TIME_FORMAT.getValue());
    }
    @Resource
    private WebConfiguration webConfiguration;
    @Resource
    private ErrorMessage errorMessage;
    @Resource
    private CampaignManagementService campaignManagementService;
    @Resource
    private CampaignTrackerService campaignTrackerService;
    @Resource(name = "mailgunJerseyService")
    private MailgunService mailgunJerseyService;
    @Resource
    private SubscriberGroupService subscriberGroupService;

    @RequestMapping(value = "/main/merchant/campaignmanagement", method = RequestMethod.GET)
    public String getCampaignManagementPage() {
        return "campaignManagementPage";
    }

    @RequestMapping(value = "/main/merchant/campaign_management/{pageNumber}", method = RequestMethod.GET)
    public String getCampaignManagementPage(@PathVariable Integer pageNumber, final HttpServletRequest request, Model model) {

        Page<CampaignEntity> campaignPage = campaignManagementService.getCampaignEntityPage(buildMapFilter(request), pageNumber, webConfiguration.getMaxRecordsPerPage());

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

    @RequestMapping(value = "/main/merchant/campaign_management/goToFinishPage", method = RequestMethod.GET)
    public String goToFinishPage(ModelAndView modelAndView) {

        return "campaignManagementFinishPage";
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
                campaignDTO.setEmailContent("</br></br></br></br></br></br></br></br></br></br><a href=\"%unsubscribe_url%\">Click here</> to unsubscribe");
                campaignDTO = campaignManagementService.saveCampaignDTO(campaignDTO);

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

    @RequestMapping(value = "/main/merchant/campaign_management/{campaignId}/saveCampaignDelivery", method = RequestMethod.POST)
    @ResponseBody
    public String saveCampaignDelivery(@RequestBody String scheduleDeliveryTime, @PathVariable String campaignId) {

        CampaignDTO campaignDTO = campaignManagementService.getCampaign(Long.valueOf(campaignId));
        campaignDTO.setScheduledSendDateTime(scheduleDeliveryTime);

        campaignManagementService.saveCampaign(campaignDTO);
        campaignManagementService.sendCampaignToQueueMailer(Long.valueOf(campaignId));

        return "SUCCESS";
    }

    @RequestMapping(value = "/main/merchant/campaign_management/{campaignId}/saveSubscriberGroup", method = RequestMethod.POST)
    @ResponseBody
    public String saveSubscriberGroup(@RequestBody String subscriberGroupId, @PathVariable String campaignId) {

        CampaignDTO campaignDTO = campaignManagementService.getCampaign(Long.valueOf(campaignId));
        campaignDTO.setSubscriberGroupIds(convertIdsToLongList(subscriberGroupId));

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

    @RequestMapping(value = "/main/merchant/campaign_management/view_campaign/{campaignId}", method = RequestMethod.GET)
    public ModelAndView goToViewCampaignPage(ModelAndView modelAndView, @PathVariable Long campaignId) {
        CampaignDTO campaignDTO = campaignManagementService.getCampaign(campaignId);

        CampaignStatisticDTO campaignStatisticDTO = campaignTrackerService.getCampaignStatistic(campaignId);

        setPredefinedData(modelAndView, campaignDTO);
        modelAndView.addObject("campaignStatisticDTO", campaignStatisticDTO);

        modelAndView.addObject("recipientList", getContactDTOList(campaignDTO));
        modelAndView.setViewName("campaignManagementViewPage");
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

    @RequestMapping(value = "/main/merchant/campaign_management/test_email/{campaignId}", method = RequestMethod.POST)
    @ResponseBody
    public String sendTestEmail(@PathVariable Long campaignId, @RequestBody String emailTo) {

        CampaignDTO campaignDTO = campaignManagementService.getCampaign(campaignId);


        mailgunJerseyService.sendMessageWithAttachment(campaignId.toString(), null, campaignDTO.getEmailFrom(), Lists.newArrayList(emailTo), null, null, campaignDTO.getEmailSubject(), replaceEmailContentWithDefaultParams(campaignDTO.getEmailContent()));

        return "SUCCESS";
    }

    private String replaceEmailContentWithDefaultParams(String emailContent) {
        String emailContentWithParams = WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_CONTENT_PARAMS);
        String[] arrParams = StringUtils.split(emailContentWithParams, ',');
        String[] arrValues = new String[]{"First Name","Last Name", "Company Name"};

        return StringUtils.replaceEachRepeatedly(emailContent, arrParams, arrValues);
    }

    private void setPredefinedData(ModelAndView modelAndView, CampaignDTO campaignDTO) {
        modelAndView.addObject("campaignDTO", campaignDTO);
        modelAndView.addObject("subscriberGroupDTOList", getSubscriberGroupDTOList(campaignDTO));
    }

    private List<SubscriberGroupDTO> getSubscriberGroupDTOList(CampaignDTO campaignDTO) {
        Page<SubscriberGroupEntity> subscriberContactEntities = subscriberGroupService.listSubscriberGroup(new THashMap<SubscriberGroupSearchFilter, String>(), 1, webConfiguration.getMaxRecordsPerPage());

        return convertToSubscribeGroupDTOList(subscriberContactEntities.getContent(), campaignDTO.getSubscriberGroupIds());
    }

    private List<String> getContactDTOList(CampaignDTO campaignDTO) {
        List<ContactEntity> contactEntityList = subscriberGroupService.getContactListBySubscriberGroupIdList(campaignDTO.getSubscriberGroupIds());
        List<String> recipientList = new ArrayList<>();

        if (contactEntityList != null && !contactEntityList.isEmpty()) {
            for (ContactEntity contactEntity : contactEntityList) {
                if (contactEntity.getSubscribeStatus().equals(SubscribeStatus.SUBSCRIBED)) {
                    recipientList.add(contactEntity.getEmailAddress());
                }
            }
        }

        return recipientList;
    }

    //TODO: move duplicated code in SubscriberManagementController to converter
    private List<SubscriberGroupDTO> convertToSubscribeGroupDTOList(List<SubscriberGroupEntity> subscriberGroupEntityList, List<Long> subscriberGroupList) {
        List<SubscriberGroupDTO> subscriberGroupDTOList = new ArrayList<>();

        if (subscriberGroupEntityList != null && !subscriberGroupEntityList.isEmpty()) {
            for (SubscriberGroupEntity subscriberGroupEntity : subscriberGroupEntityList) {
                SubscriberGroupDTO subscriberGroupDTO = new SubscriberGroupDTO();
                subscriberGroupDTO.setId(subscriberGroupEntity.getId());
                subscriberGroupDTO.setSubscriberGroupName(subscriberGroupEntity.getGroupName());
                subscriberGroupDTO.setSubscriberGroupStatus(subscriberGroupEntity.getStatus().getStatusDescription());
                subscriberGroupDTO.setSubscriberLastUpdateDate(dateTimeFormat.format(subscriberGroupEntity.getLastUpdatedDateTime()));

                if (subscriberGroupList != null && !subscriberGroupList.isEmpty()) {
                    if (subscriberGroupList.contains(subscriberGroupDTO.getId())) {
                        subscriberGroupDTO.setAddedToCampaign(true);
                    }
                }
                subscriberGroupDTOList.add(subscriberGroupDTO);
            }
        }

        return subscriberGroupDTOList;
    }


    private Map<CampaignSearchFilter, String> buildMapFilter(HttpServletRequest request) {

        String campaignSubjectName = request.getParameter(CampaignSearchFilter.CAMPAIGN_SUBJECT.getFilterName());
        String campaignName = request.getParameter(CampaignSearchFilter.CAMPAIGN_NAME.getFilterName());


        Map<CampaignSearchFilter, String> filterMap = new THashMap<>();
        if (!StringUtils.isEmpty(campaignSubjectName)) {
            filterMap.put(CampaignSearchFilter.CAMPAIGN_SUBJECT, campaignSubjectName);
        }
        if (!StringUtils.isEmpty(campaignName)) {
            filterMap.put(CampaignSearchFilter.CAMPAIGN_NAME, campaignName);
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
                campaignDTO.setEmailSubject(campaignEntity.getEmailSubject());
                campaignDTO.setCampaignStatus(campaignEntity.getCampaignStatus().getCampaignStatusDescription());
                if (campaignEntity.getLastUpdatedDateTime() != null) {
                    campaignDTO.setLastUpdateDate(dateTimeFormat.format(campaignEntity.getLastUpdatedDateTime()));
                }
                campaignDTOList.add(campaignDTO);
            }
        }
        return campaignDTOList;
    }

    private List<Long> convertIdsToLongList(String subscriberGroupIds) {
        StringTokenizer tokenizer = new StringTokenizer(subscriberGroupIds, ",");

        List<Long> subscriberGroupIdList = new ArrayList<>();
        while (tokenizer.hasMoreElements()) {
            subscriberGroupIdList.add(Long.valueOf((String) tokenizer.nextElement()));
        }

        return subscriberGroupIdList;
    }

}
