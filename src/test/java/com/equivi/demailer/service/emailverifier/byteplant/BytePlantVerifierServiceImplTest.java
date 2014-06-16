package com.equivi.demailer.service.emailverifier.byteplant;

import com.equivi.demailer.service.emailverifier.VerifierService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring-service.xml")
public class BytePlantVerifierServiceImplTest {

    @Resource(name = "bytePlantVerifierServiceImpl")
    private VerifierService emailVerifierService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testFilterValidEmail() throws Exception {

    }

    @Test
    public void testGetEmailAddressStatus() throws Exception {
        EmailVerifierResponse response = emailVerifierService.getEmailAddressStatus("aditya.eldrid@gmail.com");

    }
}