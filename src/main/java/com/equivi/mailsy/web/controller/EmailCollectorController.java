package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.emailer.EmailCollector;
import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
import com.equivi.mailsy.dto.emailer.EmailCollectorStatusMessage;
import com.equivi.mailsy.dto.emailer.EmailCollectorUrlMessage;
import com.equivi.mailsy.service.emailcollector.EmailCollectorService;
import com.equivi.mailsy.service.emailcollector.EmailScanningService;
import com.equivi.mailsy.service.emailcollector.EmailScanningServiceImpl;
import com.equivi.mailsy.web.views.CollectorResultExcelView;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/main/emailcollector")
public class EmailCollectorController {
    private static final String EMAIL_COLLECTOR_PAGE = "emailCollectorPage";
    private static final String EMAIL_COLLECTOR_POPUP = "emailCollectorPopup";
    private static final String SESSION_CRAWLING = "sessionCrawling";

    public static final String SESSION_RESULT_EMAILS = "sessionEmailResults";
    public static final String KEY_RESULT_EMAILS = "resultEmails";

    public static final String SESSION_SITE_URL = "sessionSiteUrl";

    @Autowired
    private EmailCollectorService emailCollectorService;

    @Autowired
    private EmailScanningService emailScanningService;
    
	@RequestMapping(value = "/new", method = RequestMethod.GET)
    public String loadNewPage(Model model, HttpServletRequest request) {
        Boolean crawlingStatus = (Boolean) request.getSession().getAttribute(SESSION_CRAWLING);

		model.addAttribute("collector", new EmailCollector());

        return EMAIL_COLLECTOR_PAGE;
    }

    @RequestMapping(value = "passToPopup", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void passSiteToPopup(@RequestBody String url, HttpServletRequest request) {
        request.getSession().setAttribute(SESSION_SITE_URL, url);
    }

    @RequestMapping(value = "/popup", method = RequestMethod.GET)
    public String loadPopup(HttpServletRequest request) {
        String url = (String) request.getSession().getAttribute(SESSION_SITE_URL);
        request.setAttribute("siteUrl", url);

        request.getSession().removeAttribute(SESSION_SITE_URL);

        return EMAIL_COLLECTOR_POPUP;
    }
    
    @RequestMapping(value = "async/begin", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public void start(@RequestBody EmailCollector emailCollector, HttpServletRequest request) throws Exception {
    	emailCollectorService.subscribe(emailCollector.getSite());
        emailScanningService.subscribe();

        request.getSession().setAttribute(SESSION_CRAWLING, Boolean.TRUE);
        request.getSession().removeAttribute(SESSION_RESULT_EMAILS);

    }
    
    @RequestMapping(value = "async/update", method = RequestMethod.GET)
    public @ResponseBody DeferredResult<EmailCollectorMessage> getUpdate(HttpServletRequest request) {
    	final DeferredResult<EmailCollectorMessage> result = new DeferredResult<>();
    	emailCollectorService.getUpdate(result);
        return result;
    	
    }

    @RequestMapping(value = "async/updateUrlScanning", method = RequestMethod.GET)
    public @ResponseBody DeferredResult<EmailCollectorUrlMessage> getUpdateUrlScanning(HttpServletRequest request) {
        Boolean crawlingStatus = (Boolean) request.getSession().getAttribute(SESSION_CRAWLING);
        final DeferredResult<EmailCollectorUrlMessage> result = new DeferredResult<>();

        if(crawlingStatus) {
            emailScanningService.getUrlScanningUpdate(result);
        } else {
            EmailScanningServiceImpl.resultUrlQueue.clear();

            EmailCollectorUrlMessage urlMessage = new EmailCollectorUrlMessage("FINISH");
            result.setResult(urlMessage);
        }

        return result;
    }
    
    @RequestMapping(value = "updateCrawlingStatus", method = RequestMethod.GET)
    public @ResponseBody EmailCollectorStatusMessage getUpdateCrawlingStatus(HttpServletRequest request) {
        boolean crawlingStatus = emailCollectorService.getUpdateCrawlingStatus();

        if(crawlingStatus) {
            request.getSession().setAttribute(SESSION_CRAWLING, Boolean.FALSE);
        }

        return new EmailCollectorStatusMessage(crawlingStatus);
    }

    @RequestMapping(value = "cancelCrawling", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void cancelCrawling(HttpServletRequest request) {
        emailCollectorService.cancel();

        request.getSession().setAttribute(SESSION_CRAWLING, Boolean.FALSE);
    }

    @RequestMapping(value = "putResultToSession", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void putResultToSession(@RequestBody ArrayList<String> emails, HttpServletRequest request) {
        request.getSession().setAttribute(SESSION_RESULT_EMAILS, emails);
    }

    @RequestMapping(value = "exportToExcel", method = RequestMethod.GET)
    public ModelAndView exportToExcel(Map<String, Object> model, HttpServletRequest request) throws IOException {
        List<String> resultEmails = (List<String>) request.getSession().getAttribute(SESSION_RESULT_EMAILS);


        model.put(KEY_RESULT_EMAILS, resultEmails);

        ModelAndView mav = new ModelAndView("collectorResultExcelView");
        request.getSession().removeAttribute(SESSION_RESULT_EMAILS);

        return mav;
    }
}
