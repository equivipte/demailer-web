package com.equivi.demailer.dto.user;


import com.equivi.demailer.data.entity.UserRole;
import com.equivi.demailer.data.entity.UserStatus;

import java.io.Serializable;

public class UserResponseDTO implements Serializable {

    private static final long serialVersionUID = 1536329713993222943L;

    private String userName;

    private String firstName;

    private String middleName;

    private String lastName;

    private UserStatus userStatus;

    private UserRole userRole;

    private boolean resetPasswordRequired;

    private String phoneNo;

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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
