package com.equivi.demailer.service.emailverifier;


import com.equivi.demailer.dto.emailer.EmailVerifier;
import com.equivi.demailer.service.emailverifier.byteplant.EmailVerifierResponse;

import java.util.List;

public interface VerifierService {

    /**
     *
     * @param emailList
     * @return List of EmailVerifierResponse
     */
    List<EmailVerifierResponse> filterValidEmail(List<String> emailList);

    /**
     *
     * @param emailAddress
     * @return EmailVerifierResponse
     */
    EmailVerifierResponse getEmailAddressStatus(String emailAddress);
}
