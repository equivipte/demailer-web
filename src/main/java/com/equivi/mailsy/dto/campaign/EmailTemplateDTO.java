package com.equivi.mailsy.dto.campaign;

import java.io.Serializable;

/**
 * Created by tsagita on 3/1/15.
 */
public class EmailTemplateDTO implements Serializable {
    private String imageName;
    private String htmlName;
    private String dirName;

    public EmailTemplateDTO(String imageName, String htmlName, String dirName) {
        this.imageName = imageName;
        this.htmlName = htmlName;
        this.dirName = dirName;
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

}
