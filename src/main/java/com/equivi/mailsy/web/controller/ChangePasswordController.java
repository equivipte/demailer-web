package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.login.UserLoginDTO;
import com.equivi.mailsy.service.exception.InvalidDataException;
import com.equivi.mailsy.service.user.UserService;
import com.equivi.mailsy.web.constant.PageConstant;
import com.equivi.mailsy.web.context.SessionUtil;
import com.equivi.mailsy.web.message.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class ChangePasswordController {


    private static final String OLD_PASSWORD = "old-password";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRM = "password-confirmation";
    @Resource
    private UserService userService;
    @Resource
    private SessionUtil sessionUtil;
    @Resource
    private ErrorMessage errorMessage;

    @RequestMapping(value = "/main/forceChangePassword", method = RequestMethod.POST)
    public String forceChangePassword(HttpServletRequest request, Model model, Locale locale) {

        String oldPassword = request.getParameter(OLD_PASSWORD);

        String password = request.getParameter(PASSWORD);

        String passwordConfirm = request.getParameter(PASSWORD_CONFIRM);

        UserLoginDTO userLoginDTO = sessionUtil.getCurrentUser(request);

        try {
            userService.changePassword(userLoginDTO.getUserId(), oldPassword, password, passwordConfirm, false);
        } catch (InvalidDataException iex) {
            model.addAttribute("errors", errorMessage.buildServiceValidationErrorMessages(iex, locale));
            return "forceChangePasswordPage";
        }

        request.getSession().invalidate();
        return "redirect:/login-page?" + PageConstant.ORIGINATED_PAGE.getPageName() + "=" + PageConstant.FORCE_CHANGE_PASSWORD_PAGE.getPageName();
    }


    @RequestMapping(value = "/main/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(HttpServletRequest request, Model model, Locale locale) {

        String oldPassword = request.getParameter(OLD_PASSWORD);

        String password = request.getParameter(PASSWORD);

        String passwordConfirm = request.getParameter(PASSWORD_CONFIRM);

        UserLoginDTO userLoginDTO = sessionUtil.getCurrentUser(request);

        try {
            userService.changePassword(userLoginDTO.getUserId(), oldPassword, password, passwordConfirm, false);
        } catch (InvalidDataException iex) {
            return errorMessage.buildServiceValidationErrorMessages(iex, locale).get(0);
        }

        request.getSession().invalidate();
        return "SUCCESS";
    }

}
