package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.service.emailverifier.EmailVerifierResponse;
import com.equivi.mailsy.service.emailverifier.VerifierService;
import com.google.common.collect.Lists;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class EmailVerifierController {

    @Resource(name = "webEmailVerifierImpl")
    private VerifierService verifierService;

    @RequestMapping(value = "/main/merchant/emailverifier", method = RequestMethod.GET)
    public String getEmailVerifierPage(Model model) {
        return "emailVerifierPage";
    }

    @RequestMapping(value = "/main/merchant/emailverifier", method = RequestMethod.POST,
            headers = {"Content-type=application/json"},
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody EmailVerifierResponse verifyEmail(@RequestBody EmailVerifierResponse emailVerifierResponse) {
        return verifierService.getEmailAddressStatus(emailVerifierResponse.getAddress());
    }

    @RequestMapping(value = "/main/merchant/emailverifier/verify", method = RequestMethod.GET)
    public String verifyEmail(HttpServletRequest request, Model model) {
        List<EmailVerifierResponse> emailVerifierResponses = Lists.newArrayList();
        List<String> resultEmails = (List<String>) request.getSession().getAttribute(EmailCollectorController.SESSION_RESULT_EMAILS);

        if(resultEmails != null && !resultEmails.isEmpty()) {
            for (String email : resultEmails) {
                emailVerifierResponses.add(new EmailVerifierResponse(email, "UNAVAILABLE", "Not Available"));
            }

            model.addAttribute("emailVerifierList", emailVerifierResponses);
        }

        return "emailVerifierPage";
    }
}
