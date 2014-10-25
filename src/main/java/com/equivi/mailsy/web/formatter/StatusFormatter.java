package com.equivi.mailsy.web.formatter;

import com.equivi.mailsy.data.entity.UserStatus;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Locale;


@Component
public class StatusFormatter implements Formatter<UserStatus>, Serializable {

    private static final long serialVersionUID = 2711951899641614487L;

    @Resource
    private MessageSource messageSource;

    @Override
    public UserStatus parse(String string, Locale locale) throws ParseException {
        return UserStatus.getUserStatusByDescription(string);
    }

    @Override
    public String print(UserStatus userStatus, Locale locale) {
        return messageSource.getMessage(userStatus.getUserStatusDescription(), new Object[]{}, locale);
    }
}
