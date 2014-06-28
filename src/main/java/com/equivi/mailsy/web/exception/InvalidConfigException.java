package com.equivi.mailsy.web.exception;


public class InvalidConfigException extends RuntimeException {
    private static final long serialVersionUID = 3845493590794725646L;

    public InvalidConfigException(final String message) {
        super(message);
    }
}
