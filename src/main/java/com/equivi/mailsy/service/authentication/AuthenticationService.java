package com.equivi.mailsy.service.authentication;


import com.equivi.mailsy.dto.login.UserLoginDTO;

public interface AuthenticationService {

    /**
     * @param userName
     * @return UserLoginDTO
     */
    UserLoginDTO getUser(final String userName);
}
