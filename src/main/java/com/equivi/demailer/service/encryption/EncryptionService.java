package com.equivi.demailer.service.encryption;


/**
 * EncryptionService interface
 */
public interface EncryptionService {

    /**
     * @param clearText
     * @return String
     */
    String encrypt(String clearText);


    /**
     * @param encryptedText
     * @return String
     */
    String decrypt(String encryptedText);

}
