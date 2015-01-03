package com.equivi.mailsy.dto.campaign;

import java.io.Serializable;

/**
 * Created by tsagita on 3/1/15.
 */
public class EmailTemplateDTO implements Serializable {
    private String imageName;
    private String htmlName;
    private String dirName;

    // This is needed to prevent browser doing image caching
    private String dateTime;

    public EmailTemplateDTO(String imageName, String htmlName, String dirName, String dateTime) {
        this.imageName = imageName;
        this.htmlName = htmlName;
        this.dirName = dirName;
        this.dateTime = dateTime;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getHtmlName() {
        return htmlName;
    }

    public void setHtmlName(String htmlName) {
        this.htmlName = htmlName;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
