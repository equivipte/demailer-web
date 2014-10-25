package com.equivi.mailsy.web.constant;


public enum PageConstant {

    ORIGINATED_PAGE("originatedPage"),
    GO_TO_LOGIN_PAGE("go-to-login-page"),
    CHANGE_PASSWORD_PAGE("changePasswordPage"),
    FORCE_CHANGE_PASSWORD_PAGE("forceChangePasswordPage"),
    SUBSCRIBER_GROUP("SUBSCRIBER_GROUP"),
    SUBSCRIBER_LIST("SUBSCRIBER_LIST"),
    NEXT_PAGE("nextPage"),
    LOGIN_PAGE("login_page");


    private String pageName;

    PageConstant(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}
