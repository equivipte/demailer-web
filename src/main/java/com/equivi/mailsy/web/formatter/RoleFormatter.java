package com.equivi.mailsy.web.formatter;

import com.equivi.mailsy.data.entity.UserRole;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Locale;


@Component
public class RoleFormatter implements Formatter<UserRole>, Serializable {

    private static final long serialVersionUID = -7159550179919574875L;

    @Resource
    private MessageSource messageSource;

    @Override
    public UserRole parse(String s, Locale locale) throws ParseException {
        return UserRole.getUserRoleByDescription(s);
    }

    @Override
    public String print(UserRole userRole, Locale locale) {
        return messageSource.getMessage(userRole.getRoleDescription(), new Object[]{}, locale);
    }
}
