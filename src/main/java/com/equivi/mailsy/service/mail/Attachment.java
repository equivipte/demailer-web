package com.equivi.mailsy.service.mail;


import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Attachment implements Serializable {

    private static final long serialVersionUID = -5958742098134171460L;

    private String fileName;

    private ByteArrayOutputStream content;

    private FileTypeSupported type;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ByteArrayOutputStream getContent() {
        return content;
    }

    public void setContent(ByteArrayOutputStream content) {
        this.content = content;
    }

    public FileTypeSupported getType() {
        return type;
    }

    public void setType(FileTypeSupported type) {
        this.type = type;
    }
}
