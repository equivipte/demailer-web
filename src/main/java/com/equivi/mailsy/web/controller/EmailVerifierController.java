package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.emailer.EmailCollector;
import com.equivi.mailsy.service.emailverifier.EmailVerifierResponse;
import com.equivi.mailsy.service.emailverifier.VerifierService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Controller
public class EmailVerifierController {

    @Resource(name = "webEmailVerifierImpl")
    private VerifierService verifierService;

    @RequestMapping(value = "/main/emailverifier", method = RequestMethod.GET)
    public String getEmailVerifierPage(Model model) {
        return "emailVerifierPage";
    }

    @RequestMapping(value = "/main/emailverifier", method = RequestMethod.POST,
            headers = {"Content-type=application/json"},
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody EmailVerifierResponse verifyEmail(@RequestBody EmailVerifierResponse emailVerifierResponse) {
        return verifierService.getEmailAddressStatus(emailVerifierResponse.getAddress());
    }

}
