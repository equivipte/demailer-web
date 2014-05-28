package com.equivi.demailer.service.user;


public enum UserSearchFilter {

    USERNAME("userName"),
    EMAIL_ADDRESS("emailAddress");


    private String filterName;

    UserSearchFilter(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

}
