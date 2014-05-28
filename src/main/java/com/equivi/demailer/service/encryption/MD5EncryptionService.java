package com.equivi.demailer.service.encryption;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component("MD5")
public class MD5EncryptionService implements EncryptionService {

    private static final Logger logger = LoggerFactory.getLogger(MD5EncryptionService.class);

    @Override
    public String encrypt(String clearText) {
        if (StringUtils.isBlank(clearText)) {
            return "";
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(clearText.getBytes());
            byte[] encrypted = messageDigest.digest();

            return convertToHex(encrypted);

        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private String convertToHex(byte[] byteArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteArr.length; i++) {
            sb.append(Integer.toString((byteArr[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    @Override
    public String decrypt(String encryptedText) {
        throw new IllegalArgumentException("This operation is not supported for this service");
    }
}
