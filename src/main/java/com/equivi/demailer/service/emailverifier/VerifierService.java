package com.equivi.demailer.service.emailverifier;


import java.util.List;

public interface VerifierService {

    /**
     *
     * @param emailList
     * @return
     */
    List<String> filterValidEmail(List<String> emailList);

    /**
     *
     * @param emailAddress
     * @return
     */
    String  getEmailAddressStatus(String emailAddress);
}
