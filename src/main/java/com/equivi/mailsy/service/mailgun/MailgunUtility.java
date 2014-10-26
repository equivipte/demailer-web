package com.equivi.mailsy.service.mailgun;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class MailgunUtility {

    public String sanitizeEmailContentFromEmbeddedImage(String emailContent) {
        Document htmlDoc = Jsoup.parse(emailContent);
        Elements images = htmlDoc.select("img");

        int i = 0;
        for (Element image : images) {
            i++;
            emailContent = StringUtils.replace(emailContent, image.attr("src"), "cid:file" + i + ".png");
        }

        return emailContent;
    }

    public List<File> getAttachmentFileList(String emailContent) {
        Document htmlDoc = Jsoup.parse(emailContent);
        Elements images = htmlDoc.select("img");

        List<File> attachmentFileList = new ArrayList<>();
        int i = 0;
        for (Element image : images) {
            i++;
            String imageBase64Encoded = image.attr("src");

            String fileName = buildFileName(i);
            attachmentFileList.add(convertImageBase64ToFile(imageBase64Encoded, fileName));
        }

        return attachmentFileList;
    }

    private static String buildFileName(int i) {
        StringBuilder sbFileName = new StringBuilder();
        sbFileName.append("file");
        sbFileName.append(i);
        sbFileName.append(".png");

        return sbFileName.toString();
    }


    public static File convertImageBase64ToFile(String imageBase64Encoded, String fileName) {
        String imageDataBytes = imageBase64Encoded.substring(imageBase64Encoded.indexOf(",") + 1);

        if (!StringUtils.isEmpty(imageDataBytes)) {
            InputStream stream = new ByteArrayInputStream(Base64.decodeBase64(imageDataBytes.getBytes()));

            File fileToAttached = new File(fileName);

            try {
                FileUtils.copyInputStreamToFile(stream, fileToAttached);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileToAttached;
        }

        return null;
    }

}
