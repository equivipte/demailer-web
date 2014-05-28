package com.equivi.demailer.service.user;


import com.equivi.demailer.dto.user.UserRequestDTO;

public interface UserMailService {

    void sendEmailForUserCreation(UserRequestDTO userRequestDTO);

    void sendEmailForPasswordUpdate(UserRequestDTO userRequestDTO);

    void sendEmailForForgetPassword(UserRequestDTO userRequestDTO);

}
