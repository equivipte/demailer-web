package com.equivi.mailsy.service.emailverifier;



import com.equivi.mailsy.dto.emailer.EmailVerifierResult;

import java.util.List;

public interface VerifierService {

    /**
     *
     * @param emailList
     * @return List of EmailVerifierResponse
     */
    List<?> filterValidEmail(List<String> emailList);

    /**
     *
     * @param emailAddress
     * @return EmailVerifierResponse
     */
    EmailVerifierResult getEmailAddressStatus(String emailAddress);

}
