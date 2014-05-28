package com.equivi.demailer.service.encryption;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;


public class MD5EncryptionServiceTest {

    private String PASSWORD_TO_ENCRYPT = "admin";

    @InjectMocks
    private MD5EncryptionService encryptionService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEncrypt() throws Exception {
        String encryptedText = encryptionService.encrypt(PASSWORD_TO_ENCRYPT);

        String expectedValue = "21232f297a57a5a743894a0e4a801fc3";

        assertEquals(expectedValue, encryptedText);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecrypt() throws Exception {

        encryptionService.decrypt("TestDecrypt");
    }
}
