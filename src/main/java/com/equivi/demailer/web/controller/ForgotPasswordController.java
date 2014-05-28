package com.equivi.demailer.web.controller;


import com.equivi.demailer.service.exception.InvalidDataException;
import com.equivi.demailer.service.user.UserService;
import com.equivi.demailer.web.message.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class ForgotPasswordController {

    @Resource
    private UserService userService;

    @Resource
    private ErrorMessage errorMessage;

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public String forgetPassword(HttpServletRequest request, Model model, Locale locale) {
        String emailAddress = request.getParameter("email_address");

        try {
            userService.forgotPassword(emailAddress);
            return "SUCCESS";
        } catch (InvalidDataException iex) {
            return errorMessage.buildServiceValidationErrorMessages(iex, locale).get(0);
        }
    }
}
