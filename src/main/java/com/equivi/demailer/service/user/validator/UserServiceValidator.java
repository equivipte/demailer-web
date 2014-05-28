package com.equivi.demailer.service.user.validator;


import com.equivi.demailer.data.dao.UserDao;
import com.equivi.demailer.data.entity.QUserEntity;
import com.equivi.demailer.data.entity.UserEntity;
import com.equivi.demailer.dto.user.UserRequestDTO;
import com.equivi.demailer.service.encryption.EncryptionService;
import com.equivi.demailer.service.encryption.PasswordService;
import com.equivi.demailer.service.encryption.PasswordUtil;
import com.equivi.demailer.service.exception.InvalidDataException;
import com.mysema.query.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class UserServiceValidator {

    private static final String EMAIL_ALREADY_EXIST = "invalid.data.email.already.exist";
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceValidator.class);
    @Resource
    private UserDao userDao;
    @Resource
    private EncryptionService encryptionService;
    @Resource
    private PasswordService passwordService;

    public void validate(UserRequestDTO userRequestDTO) {

        UserEntity userEntity = userDao.findOne(getUserByUserName(userRequestDTO.getEmailAddress()));

        if (userEntity != null) {
            throw new InvalidDataException(EMAIL_ALREADY_EXIST);
        }

    }

    public void validateUpdate(UserRequestDTO userRequestDTO) {
        LOG.info("had gone through validate update");
    }


    public void validateChangePassword(UserEntity userEntity, String oldPassword, String newPassword, String confirmPassword) {

        if (StringUtils.isBlank(oldPassword)) {
            throw new InvalidDataException("password.old_password.mandatory");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new InvalidDataException("password.password.mandatory");
        }
        if (StringUtils.isBlank(confirmPassword)) {
            throw new InvalidDataException("password.password-confirmation.mandatory");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidDataException("password.confirmation.not_match");
        }

        if (isPasswordDifferentWithOriginal(userEntity.getPassword(), oldPassword)) {
            throw new InvalidDataException("invalid.old.password");
        }

        String fullName = userEntity.getFirstName() + " " + userEntity.getLastName();
        passwordService.checkPasswordValidation(newPassword, fullName, userEntity.getUserName(), PasswordUtil.PASSWORD_MIN_LENGTH);
    }

    public boolean isPasswordDifferentWithOriginal(String originalPassword, String passwordFromScreen) {
        if (encryptionService.encrypt(passwordFromScreen).equals(originalPassword)) {
            return false;
        }
        return true;
    }

    public Predicate getUserByUserName(final String userName) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        return qUserEntity.userName.eq(userName);
    }
}
