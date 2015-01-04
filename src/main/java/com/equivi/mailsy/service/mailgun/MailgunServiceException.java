package com.equivi.mailsy.service.mailgun;

public class MailgunServiceException extends RuntimeException {

    public static final String ATTACHMENT_EMPTY_EXCEPTION_MESSAGE = "Attachment file is empty, attachment file cannot be empty";
    public static final String UNABLE_TO_UNSUBSCRIBE_EMAIL_ADDRESS = "Unable to unsubscribe email address";
    private static final long serialVersionUID = 603871727732324645L;

    MailgunServiceException(String message) {
        super(message);
    }

}
