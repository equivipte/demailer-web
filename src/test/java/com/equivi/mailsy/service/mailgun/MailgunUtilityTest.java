package com.equivi.mailsy.service.mailgun;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MailgunUtilityTest {

    private static final String EMAIL_CONTENT = "<td>Inline Image:<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA\n" +
            "AAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO\n" +
            "9TXL0Y4OHwAAAABJRU5ErkJggg==\" alt=\"Red dot\" /><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA\n" +
            "AAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==\" alt=\"Red dot\" /></td>";

    private MailgunUtility mailgunUtility;

    @Before
    public void setUp() throws Exception {
        mailgunUtility = new MailgunUtility();

    }

    @Test
    public void testSanitizeEmailContentFromEmbeddedImage() throws Exception {
        //Given & When
        String sanitizedEmailContentFromEmbedeedImage = mailgunUtility.sanitizeEmailContentFromEmbeddedImage(EMAIL_CONTENT);

        String expectedSanitizedEmailContent = "<td>Inline Image:<img src=\"cid:file1.png\" alt=\"Red dot\" /><img src=\"cid:file2.png\" alt=\"Red dot\" /></td>";

        //Then
        assertEquals(expectedSanitizedEmailContent, sanitizedEmailContentFromEmbedeedImage);
    }

    @Test
    public void testGetAttachmentFile() throws Exception {
        //Given & When
        List<File> attachmentFile = mailgunUtility.getAttachmentFileList(EMAIL_CONTENT);

        //Then
        assertEquals(2, attachmentFile.size());
    }
}