package com.equivi.mailsy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UploadController {


    @RequestMapping(value = "/main/upload/", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(final @RequestParam("file") MultipartFile file, final HttpServletRequest servletRequest, final Model model) {
        ModelAndView modelAndView = new ModelAndView();


        return modelAndView;
    }
}
