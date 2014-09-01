package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.data.entity.CampaignEntity;
import com.equivi.mailsy.dto.campaign.CampaignDTO;
import com.equivi.mailsy.service.campaign.CampaignManagementService;
import com.equivi.mailsy.service.campaign.CampaignSearchFilter;
import com.equivi.mailsy.service.constant.ConstantProperty;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.equivi.mailsy.web.message.ErrorMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CampaignManagementController extends AbstractController {

    @Resource
    private WebConfiguration webConfiguration;

    @Resource
    private ErrorMessage errorMessage;

    @Resource
    private CampaignManagementService campaignManagementService;

    private static SimpleDateFormat sdf;

    static {
        sdf = new SimpleDateFormat(ConstantProperty.DATE_TIME_FORMAT.getValue());
    }


    @RequestMapping(value = "/main/campaignmanagement", method = RequestMethod.GET)
    public String getCampaignManagementPage() {
        return "campaignManagementPage";
    }

    @RequestMapping(value = "/main/campaign_management/{pageNumber}", method = RequestMethod.GET)
    public String getCampaignManagementPage(@PathVariable Integer pageNumber, final HttpServletRequest request, Model model) {

        Page<CampaignEntity> campaignPage = campaignManagementService.listCampaignEntity(buildMapFilter(request), pageNumber, webConfiguration.getMaxRecordsPerPage());

        setCampaignList(model, campaignPage);

        setPagination(model, campaignPage);

        return "campaignManagementPage";
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
                campaignDTO.setEmaiSubject(campaignEntity.getEmaiSubject());
                campaignDTO.setLastUpdateDate(sdf.format(campaignEntity.getLastUpdatedDateTime()));
                campaignDTOList.add(campaignDTO);
            }
        }
        return campaignDTOList;
    }

}
