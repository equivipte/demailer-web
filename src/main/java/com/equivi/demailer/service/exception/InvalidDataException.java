package com.equivi.demailer.service.exception;


public class InvalidDataException extends RuntimeException {
    private static final long serialVersionUID = 138224590316916791L;

    public InvalidDataException(String message) {
        super(message);
    }
}
