package com.equivi.demailer.service.exception;

public class AuthenticationFailureException extends RuntimeException {

    private static final long serialVersionUID = -630223937282064462L;

    public AuthenticationFailureException(String message) {
        super(message);
    }
}
