package com.equivi.mailsy.web.constant;


public enum WebRequestConstant {

    USER_OBJECT_UPDATED_PASSWORD("user_object_updated_password");


    private String constantName;

    WebRequestConstant(String constantName) {
        this.constantName = constantName;
    }

    public String getConstantName() {
        return constantName;
    }
}
