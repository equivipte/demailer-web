package com.equivi.demailer.dto.login;


import com.equivi.demailer.data.entity.UserRole;
import com.equivi.demailer.data.entity.UserStatus;

import java.io.Serializable;

public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 2191881275264098055L;

    private Long userId;

    private String userName;

    private String firstName;

    private String middleName;

    private String lastName;

    private UserStatus userStatus;

    //Fill this merchant id if this is merchant user
    private Long merchantId;

    private boolean resetPasswordRequired;

    private UserRole userRole;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isResetPasswordRequired() {
        return resetPasswordRequired;
    }

    public void setResetPasswordRequired(boolean resetPasswordRequired) {
        this.resetPasswordRequired = resetPasswordRequired;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
