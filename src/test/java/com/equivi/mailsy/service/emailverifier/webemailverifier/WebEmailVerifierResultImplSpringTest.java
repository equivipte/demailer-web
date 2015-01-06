package com.equivi.mailsy.service.emailverifier.webemailverifier;

import com.equivi.mailsy.dto.emailer.EmailVerifierResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring-service.xml")
public class WebEmailVerifierResultImplSpringTest {

    @Resource
    private WebEmailVerifierServiceImpl webEmailVerifier;


    @Test
    public void testFilterValidEmail() throws Exception {
        EmailVerifierResult response = webEmailVerifier.getEmailAddressStatus("zztop_aditya2@yahoo.com");
    }

    @Test
    public void testGetEmailAddressStatus() throws Exception {

    }
}