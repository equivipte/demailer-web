package com.equivi.mailsy.dto.user;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

public class UserRequestDTO implements Serializable {

    private static final long serialVersionUID = -5586868900484528585L;

    private Long userId;

    @Email(message = "invalid.email.address")
    @NotEmpty(message = "email.empty")
    private String emailAddress;

    @NotEmpty(message = "first.name.empty")
    private String firstName;

    private String lastName;

    private boolean generatePassword;

    private String password;

    private String phoneNo;

    private Integer userRole;

    private Integer userStatus;

    private String branchCode;

    private String branchCodeSelected;

    private List<String> branchCodeList;

    public String getBranchCodeSelected() {
        return branchCodeSelected;
    }

    public void setBranchCodeSelected(String branchCodeSelected) {
        this.branchCodeSelected = branchCodeSelected;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public boolean isGeneratePassword() {
        return generatePassword;
    }

    public void setGeneratePassword(boolean generatePassword) {
        this.generatePassword = generatePassword;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<String> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<String> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }
}
