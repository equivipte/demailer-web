package com.equivi.mailsy.service.user;

import com.equivi.mailsy.data.entity.UserEntity;
import com.equivi.mailsy.dto.user.UserResponseDTO;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class UserResponseDTOConverter {

    private static final Logger logger = LoggerFactory.getLogger(UserResponseDTOConverter.class);

    public UserResponseDTO convertToDTO(UserEntity userEntity) {

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        try {
            PropertyUtils.copyProperties(userResponseDTO, userEntity);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        }

        return userResponseDTO;
    }

}
