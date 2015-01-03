package com.equivi.mailsy.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/main/streaming")
public class StreamingController {
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
}
