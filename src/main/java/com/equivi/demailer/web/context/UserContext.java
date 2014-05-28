package com.equivi.demailer.web.context;

import com.equivi.demailer.dto.login.UserLoginDTO;

import java.io.Serializable;


public class UserContext implements Serializable {

    private static final long serialVersionUID = 5621330132940609951L;

    private static ThreadLocal<UserLoginDTO> userContext;

    static {
        userContext = new ThreadLocal<>();
    }

    public static UserLoginDTO get() {
        return userContext.get();
    }

    public static void put(final UserLoginDTO user) {
        userContext.set(user);
    }

    public static void remove() {
        userContext.remove();
    }

    static void setUserContext(final ThreadLocal<UserLoginDTO> userContext) {
        UserContext.userContext = userContext;
    }


    public static void reinitialize() {
        userContext = new ThreadLocal<>();
    }
}
