package com.equivi.mailsy.util;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileUploader {

    public String uploadFile(final MultipartFile file, final String targetFileName) throws IOException {
        byte[] bytes = file.getBytes();

        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(targetFileName)));
        stream.write(bytes);
        stream.close();
        return targetFileName;
    }


}
