package com.equivi.mailsy.service.user;


import com.equivi.mailsy.dto.user.UserRequestDTO;

public interface UserMailService {

    void sendEmailForUserCreation(UserRequestDTO userRequestDTO);

    void sendEmailForPasswordUpdate(UserRequestDTO userRequestDTO);

    void sendEmailForForgetPassword(UserRequestDTO userRequestDTO);

}
