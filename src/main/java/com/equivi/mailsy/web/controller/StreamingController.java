package com.equivi.mailsy.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/main/streaming")
public class StreamingController {
    private static final int BUFFER_SIZE = 4096;

    @RequestMapping(value = "loadImage", method = RequestMethod.GET, produces=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] loadImageFromFileSystem(HttpServletRequest request, @RequestParam("dirName") String directoryName, @RequestParam("imageName") String imageName) throws IOException {
        InputStream in = new FileInputStream(directoryName + "/" + imageName);
        try {
            return IOUtils.toByteArray(in);
        }
        finally {
            in.close();
        }
    }

    @RequestMapping(value = "loadHTML", method = RequestMethod.GET, produces=MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public byte[] loadHTMLFromFileSystem(HttpServletRequest request, @RequestParam("dirName") String directoryName, @RequestParam("htmlName") String htmlName) throws IOException {
        InputStream in = new FileInputStream(directoryName + "/" + htmlName);
        try {
            return IOUtils.toByteArray(in);
        }
        finally {
            in.close();
        }
    }

    @RequestMapping(value = "downloadFile", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("dirName") String directoryName, @RequestParam("fileName") String fileName) throws IOException {
        String fullPath = directoryName + "/" + fileName;
        File downloadFile = new File(fullPath);
        InputStream in = new FileInputStream(downloadFile);

        String mimeType = request.getServletContext().getMimeType(fullPath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        while ((bytesRead = in.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        in.close();
        outStream.close();
    }
}
