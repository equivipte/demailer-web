package com.equivi.demailer.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CampaignManagementController {

    @RequestMapping(value = "/main/campaignmanagement", method = RequestMethod.GET)
    public String getCampaignManagementPage() {
        return "campaignManagementPage";
    }
}
