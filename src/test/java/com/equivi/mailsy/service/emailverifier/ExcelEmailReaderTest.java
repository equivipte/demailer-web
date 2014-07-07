package com.equivi.mailsy.service.emailverifier;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ExcelEmailReaderTest {


    private ExcelEmailReader excelEmailReader;

    @Before
    public void setUp() throws Exception {
        excelEmailReader = new ExcelEmailReader();
    }

    @Test
    public void testGetEmailAddressList() throws Exception {
        System.out.println(this.getClass().getResource("/EmailList.xlsx").getFile());
        List<String> emailAddressList = excelEmailReader.getEmailAddressList(this.getClass().getResource("/EmailList.xlsx").getFile());

        assertTrue(emailAddressList.size() == 7);
    }
}