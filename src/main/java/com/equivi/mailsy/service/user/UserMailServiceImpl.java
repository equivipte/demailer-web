package com.equivi.mailsy.service.user;

import com.equivi.mailsy.dto.user.UserRequestDTO;
import com.equivi.mailsy.service.mail.MailService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Service
public class UserMailServiceImpl implements UserMailService {

    private static final String PASSWORD_CHANGED_SUBJECT_KEY = "password.changed.email.subject";

    private static final String PASSWORD_CHANGED_EMAIL_TEMPLATE = "password_changed_email_content.vm";

    private static final String CREATE_USER_SUBJECT_KEY = "new.user.email.subject";

    private static final String CREATE_USER_EMAIL_TEMPLATE = "user_creation_email_content.vm";

    private static final String FORGOT_PASSWORD_EMAIL_SUBJECT_KEY = "password.changed.email.subject";

    private static final String FORGOT_PASSWORD_EMAIL_TEMPLATE = "forgot_password_email_content.vm";

    @Resource
    private MailService mailService;

    @Resource
    private VelocityEngine velocityEngine;


    @Resource(name = "dEmailerWebProperties")
    private Properties dEmailerWebProperties;

    @Override
    public void sendEmailForUserCreation(UserRequestDTO userRequestDTO) {
        Map model = new HashMap();
        model.put("userRequestDTO", userRequestDTO);

        String messageContent = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, CREATE_USER_EMAIL_TEMPLATE, "UTF-8", model);

        final String newUserEmailSubject = dEmailerWebProperties.getProperty(CREATE_USER_SUBJECT_KEY);

        mailService.sendMailPlain(Arrays.asList(userRequestDTO.getEmailAddress()), null, null, newUserEmailSubject, messageContent);

    }

    @Override
    public void sendEmailForPasswordUpdate(UserRequestDTO userRequestDTO) {
        Map model = new HashMap();
        model.put("userRequestDTO", userRequestDTO);

        String messageContent = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, PASSWORD_CHANGED_EMAIL_TEMPLATE, "UTF-8", model);

        final String passwordChangedEmailSubject = dEmailerWebProperties.getProperty(PASSWORD_CHANGED_SUBJECT_KEY);

        mailService.sendMailPlain(Arrays.asList(userRequestDTO.getEmailAddress()), null, null, passwordChangedEmailSubject, messageContent);
    }

    @Override
    public void sendEmailForForgetPassword(UserRequestDTO userRequestDTO) {
        Map model = new HashMap();
        model.put("userRequestDTO", userRequestDTO);

        String messageContent = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, FORGOT_PASSWORD_EMAIL_TEMPLATE, "UTF-8", model);

        final String forgotPasswordEmailSubject = dEmailerWebProperties.getProperty(FORGOT_PASSWORD_EMAIL_SUBJECT_KEY);

        mailService.sendMailPlain(Arrays.asList(userRequestDTO.getEmailAddress()), null, null, forgotPasswordEmailSubject, messageContent);

    }
}
