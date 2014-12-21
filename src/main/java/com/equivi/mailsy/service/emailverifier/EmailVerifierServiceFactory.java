package com.equivi.mailsy.service.emailverifier;

public interface EmailVerifierServiceFactory {

    VerifierService getEmailVerifierService(String verifierName);
}
