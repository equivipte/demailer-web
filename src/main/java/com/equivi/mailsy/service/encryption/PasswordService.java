package com.equivi.mailsy.service.encryption;


import com.equivi.mailsy.service.exception.InvalidDataException;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private PasswordUtil passwordUtil;

    public String generateRandomPassword(int passwordLength) {
        return new String(PasswordUtil.generateStrongPassword(passwordLength));
    }

    public void checkPasswordValidation(String password, String fullName, String accountName, int passwordLength) {

        if (password.length() < passwordLength) {
            throw new InvalidDataException("error.password.complexity.rule2");
        }

        if (password.contains(accountName) || password.contains(fullName)) {
            throw new InvalidDataException("error.password.complexity.rule1");
        }

        if (!PasswordUtil.isPasswordComplex(password)) {
            throw new InvalidDataException("error.password.complexity.rule3");
        }
    }
}
