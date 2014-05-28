package com.equivi.demailer.web.message;


import com.equivi.demailer.service.exception.InvalidDataException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = -6187961846532280300L;

    @Resource
    private MessageSource messageSource;

    public List<String> buildFormValidationErrorMessages(BindingResult bindingResult, Locale locale) {
        List<String> errorMessageList = new ArrayList<>();
        for (Object object : bindingResult.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;

                String errorMessage = fieldError.getDefaultMessage();
                String message = messageSource.getMessage(errorMessage, new Object[]{}, locale);
                errorMessageList.add(message);
            }
        }
        return errorMessageList;
    }

    public List<String> buildServiceValidationErrorMessages(InvalidDataException idex, Locale locale) {
        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.add(messageSource.getMessage(idex.getMessage(), new Object[]{}, locale));
        return errorMessageList;
    }

    public List<String> buildErrorMessages(String message, Locale locale) {
        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.add(messageSource.getMessage(message, new Object[]{}, locale));
        return errorMessageList;
    }

}
