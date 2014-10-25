package com.equivi.mailsy.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MailsyStringUtilTest {

    @Test
    public void testBuildStringWithSeparator() throws Exception {
        List<String> recipientList = Lists.newArrayList();
        recipientList.add("admin@equivi1.com");
        recipientList.add("admin2@equivi1.com");

        assertEquals("admin@equivi1.com,admin2@equivi1.com", MailsyStringUtil.buildStringWithSeparator(recipientList, ','));
    }

    @Test
    public void testBuildQueryParameters() throws Exception {

    }
}