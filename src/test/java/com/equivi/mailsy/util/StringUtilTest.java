package com.equivi.mailsy.util;

import com.equivi.mailsy.service.mailgun.MailgunParameters;
import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StringUtilTest {

    private MailsyStringUtil stringUtil;

    @Before
    public void setUp() throws Exception {
        stringUtil = new MailsyStringUtil();

    }

    @Test
    public void testFormatSeparator() throws Exception {
        //Given
        List<String> toList = new ArrayList<>();

        toList.add("abc@email.com");
        toList.add("cde@email.com");

        //When
        String expectedStringAfterFormat = stringUtil.buildStringWithSeparator(toList, ',');

        //Then
        assertEquals("abc@email.com,cde@email.com", expectedStringAfterFormat);
    }

    @Test
    public void testBuildQueryParameter() {
        //Given
        TMap<String, String> paramMap = new THashMap<>();

        paramMap.put(MailgunParameters.TO.getValue(), "e@email.com,a@email.com");
        paramMap.put(MailgunParameters.FROM.getValue(), "ea@email.com");

        //When
        String expectedValue = MailgunParameters.TO.getValue() + "=e@email.com,a@email.com&" + MailgunParameters.FROM.getValue() + "=ea@email.com";

        //Then
        assertEquals(expectedValue, stringUtil.buildQueryParameters(paramMap));

    }

    @Test
    public void testBuildQueryParameterWithExcludeOfEmptyMapValue() {
        //Given
        TMap<String, String> paramMap = new THashMap<>();

        paramMap.put(MailgunParameters.TO.getValue(), "e@email.com,a@email.com");
        paramMap.put(MailgunParameters.FROM.getValue(), "");

        //When
        String expectedValue = MailgunParameters.TO.getValue() + "=e@email.com,a@email.com";

        //Then
        assertEquals(expectedValue, stringUtil.buildQueryParameters(paramMap));

    }
}