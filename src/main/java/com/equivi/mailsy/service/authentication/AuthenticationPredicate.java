package com.equivi.mailsy.service.authentication;


import com.equivi.mailsy.data.entity.QUserEntity;
import com.mysema.query.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationPredicate {

    public Predicate getUserByUserNameAndPassword(final String userName, final String userPassword) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        return qUserEntity.userName.eq(userName).
                and(qUserEntity.password.eq(userPassword));
    }

    public Predicate getUserByUserName(final String userName) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        return qUserEntity.userName.eq(userName);
    }
}
