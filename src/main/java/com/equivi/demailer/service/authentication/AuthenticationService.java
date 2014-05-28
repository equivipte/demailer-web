package com.equivi.demailer.service.authentication;


import com.equivi.demailer.dto.login.UserLoginDTO;

public interface AuthenticationService {

    /**
     * @param userName
     * @return UserLoginDTO
     */
    UserLoginDTO getUser(final String userName);
}
