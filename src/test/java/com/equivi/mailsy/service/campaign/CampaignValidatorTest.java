package com.equivi.mailsy.service.campaign;

import com.equivi.mailsy.service.exception.InvalidDataException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CampaignValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private CampaignValidator campaignValidator;

    @Before
    public void setUp() throws Exception {
        campaignValidator = new CampaignValidator();

    }

    @Test
    public void testValidateEmailAddress() throws Exception {
        campaignValidator.validateEmailAddress("abc@email.com");
    }

    @Test
    public void testValidateInvalidEmailAddress() throws Exception {
        expectedException.expect(InvalidDataException.class);
        expectedException.expectMessage(CampaignValidator.INVALID_EMAIL_ADDRESS);
        campaignValidator.validateEmailAddress("abxemail.com");
    }

}