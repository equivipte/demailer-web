package com.equivi.demailer.service.authentication;

import com.mysema.query.types.Predicate;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class AuthenticationPredicateTest {

    private static final String EXPECTED_AUTHENTICATION_PREDICATE = "userEntity.userName = USERNAME && userEntity.password = PASSWORD";

    private static final String EXPECTED_GET_USERNAME_PREDICATE = "userEntity.userName = USERNAME";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private AuthenticationPredicate authenticationPredicate;

    @Before
    public void setUp() throws Exception {
        authenticationPredicate = new AuthenticationPredicate();
    }

    @Test
    public void testGetUserByUserNameAndPassword() throws Exception {
        final Predicate predicate = authenticationPredicate.getUserByUserNameAndPassword(USERNAME, PASSWORD);
        assertEquals(EXPECTED_AUTHENTICATION_PREDICATE, predicate.toString());
    }

    @Test
    public void testGetUserByUserName() throws Exception {
        final Predicate predicate = authenticationPredicate.getUserByUserName(USERNAME);
        assertEquals(EXPECTED_GET_USERNAME_PREDICATE, predicate.toString());
    }
}
