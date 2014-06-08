package com.equivi.demailer.web.controller;

import com.equivi.demailer.dto.emailer.EmailVerifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


@Controller
public class EmailVerifierController {

    @RequestMapping(value = "/main/emailverifier", method = RequestMethod.GET)
    public String getEmailVerifierPage(Model model) {
        model.addAttribute("emailVerifyResultList", getEmailVerifierResults());
        return "emailVerifierPage";
    }

    private List<EmailVerifier> getEmailVerifierResults() {
        List<EmailVerifier> emailVerifierList = new ArrayList<>();

        EmailVerifier emailVerifier = new EmailVerifier();
        emailVerifier.setEmailAddress("gerrardkoh@equivi.com");
        emailVerifier.setStatus("VALID");
        emailVerifier.setStatusDescription("Valid Email");

        EmailVerifier emailVerifier2 = new EmailVerifier();
        emailVerifier2.setEmailAddress("esthertan@equivi.com");
        emailVerifier2.setStatus("VALID");
        emailVerifier2.setStatusDescription("Valid Email");

        EmailVerifier emailVerifier3 = new EmailVerifier();
        emailVerifier3.setEmailAddress("unknown@equivi.com");
        emailVerifier3.setStatus("INVALID");
        emailVerifier3.setStatusDescription("Unknown email address");

        EmailVerifier emailVerifier4 = new EmailVerifier();
        emailVerifier4.setEmailAddress("unknown@equivix.com");
        emailVerifier4.setStatus("INVALID");
        emailVerifier4.setStatusDescription("Invalid domain address");

        emailVerifierList.add(emailVerifier);
        emailVerifierList.add(emailVerifier2);
        emailVerifierList.add(emailVerifier3);
        emailVerifierList.add(emailVerifier4);

        return emailVerifierList;
    }

}
