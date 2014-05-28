package com.equivi.demailer.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class EmailVerifierController {

    @RequestMapping(value = "/main/emailverifier", method = RequestMethod.GET)
    public String getEmailVerifierPage() {
        return "emailVerifierPage";
    }
}
