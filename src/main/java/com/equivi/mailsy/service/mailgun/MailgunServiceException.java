package com.equivi.mailsy.service.mailgun;

public class MailgunServiceException extends RuntimeException {

    private static final long serialVersionUID = 603871727732324645L;

    public static final String ATTACHMENT_EMPTY_EXCEPTION_MESSAGE = "Attachment file is empty, attachment file cannot be empty";

    MailgunServiceException(String message) {
        super(message);
    }

}
