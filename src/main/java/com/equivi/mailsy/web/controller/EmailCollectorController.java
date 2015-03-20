package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.emailer.EmailCollector;
import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
import com.equivi.mailsy.dto.emailer.EmailCollectorStatusMessage;
import com.equivi.mailsy.dto.emailer.EmailCollectorUrlMessage;
import com.equivi.mailsy.dto.quota.QuotaDTO;
import com.equivi.mailsy.service.emailcollector.EmailCollectorService;
import com.equivi.mailsy.service.quota.QuotaService;
import com.equivi.mailsy.util.EmailCrawler;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


@Controller
@RequestMapping("/main/emailcollector")
public class EmailCollectorController {
    public static final String SESSION_RESULT_EMAILS = "sessionEmailResults";
    public static final String KEY_RESULT_EMAILS = "resultEmails";
    public static final String SESSION_SITE_URL = "sessionSiteUrl";
    private static final String SITE_URL = "siteUrl";
    private static final String EMAIL_COLLECTOR_PAGE = "emailCollectorPage";
    private static final String EMAIL_COLLECTOR_POPUP = "emailCollectorPopup";
    private static final String SESSION_CRAWLING = "sessionCrawling";
    private static final String SESSION_POPUP = "sessionPopup";

    public static final Queue<DeferredResult<EmailCollectorMessage>> resultQueue = new ConcurrentLinkedQueue<>();
    public static final Queue<DeferredResult<EmailCollectorUrlMessage>> resultUrlQueue = new ConcurrentLinkedQueue<>();

    private static final Set<EmailCollectorMessage> duplicateEmails = new HashSet<>();

    @Autowired
    private EmailCollectorService emailCollectorService;

    @Autowired
    private QuotaService quotaService;
    
	@RequestMapping(value = "/new", method = RequestMethod.GET)
    public String loadNewPage(Model model, HttpServletRequest request) {
        Boolean popupSessionStatus = (Boolean) request.getSession().getAttribute(SESSION_POPUP);

        if(BooleanUtils.isTrue(popupSessionStatus)) {
            model.addAttribute("inProgress", Boolean.TRUE.toString());
        }

        return EMAIL_COLLECTOR_PAGE;
    }

    @RequestMapping(value = "passToPopup", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void passSiteToPopup(@RequestBody String url, HttpServletRequest request) {
        request.getSession().setAttribute(SESSION_SITE_URL, url);
        request.getSession().setAttribute(SESSION_POPUP, Boolean.TRUE);
    }

    @RequestMapping(value = "/popup", method = RequestMethod.GET)
    public String loadPopup(Model model, HttpServletRequest request) {
        String url = (String) request.getSession().getAttribute(SESSION_SITE_URL);
        model.addAttribute(SITE_URL, url);

        QuotaDTO quota = quotaService.getQuota();
        model.addAttribute("quota", quota);

        return EMAIL_COLLECTOR_POPUP;
    }
    
    @RequestMapping(value = "async/begin", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public void start(@RequestBody EmailCollector emailCollector, HttpServletRequest request) throws Exception {
        duplicateEmails.clear();
    	emailCollectorService.subscribe(emailCollector.getSite());

        request.getSession().setAttribute(SESSION_CRAWLING, Boolean.TRUE);
        request.getSession().removeAttribute(SESSION_RESULT_EMAILS);

    }
    
    @RequestMapping(value = "async/update", method = RequestMethod.GET)
    public @ResponseBody DeferredResult<EmailCollectorMessage> getUpdate(HttpServletRequest request) {
    	final DeferredResult<EmailCollectorMessage> result = new DeferredResult<>();
        resultQueue.add(result);
        return result;
    	
    }

    @RequestMapping(value = "async/updateUrlScanning", method = RequestMethod.GET)
    public @ResponseBody DeferredResult<EmailCollectorUrlMessage> getUpdateUrlScanning(HttpServletRequest request) {
        Boolean crawlingStatus = (Boolean) request.getSession().getAttribute(SESSION_CRAWLING);
        final DeferredResult<EmailCollectorUrlMessage> result = new DeferredResult<>();

        if(BooleanUtils.isTrue(crawlingStatus)) {
            resultUrlQueue.add(result);
        } else {
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

    @RequestMapping(value = "updatePopupSessionStatus", method = RequestMethod.GET)
    public @ResponseBody String getUpdatePopupSessionStatus(HttpServletRequest request) {
        Boolean popupSessionStatus = (Boolean) request.getSession().getAttribute(SESSION_POPUP);

        return popupSessionStatus != null ? popupSessionStatus.toString() : Boolean.FALSE.toString();
    }

    @RequestMapping(value = "cancelCrawling", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void cancelCrawling(@RequestParam("close_popup") boolean closePopup, HttpServletRequest request) {

        if (closePopup) {
            request.getSession().setAttribute(SESSION_POPUP, Boolean.FALSE);
        }

        emailCollectorService.cancel();

        request.getSession().setAttribute(SESSION_CRAWLING, Boolean.FALSE);
        request.getSession().removeAttribute(SESSION_SITE_URL);
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

    @Scheduled(fixedRate = 1000)
    public void processResultQueue() throws InterruptedException {
        DeferredResult<EmailCollectorMessage> result = resultQueue.poll();

        if(result != null) {
            if(duplicateEmails.isEmpty()) {
                EmailCollectorMessage message = EmailCrawler.queue.poll();

                if(message != null) {
                    result.setResult(message);
                    duplicateEmails.add(message);
                }

            } else {
                Iterator<EmailCollectorMessage> it = EmailCrawler.queue.iterator();

                List<String> emails = Lists.newArrayList();

                while(it.hasNext()) {
                    EmailCollectorMessage message = it.next();

                    if(message != null && !duplicateEmails.contains(message)) {
                        duplicateEmails.add(message);
                        emails.add(message.getEmail());
                    }

                    it.remove();
                }


                if(!emails.isEmpty()) {
                    EmailCollectorMessage message = new EmailCollectorMessage(StringUtils.join(emails, ","));

                    result.setResult(message);
                }
            }

        }

    }

    @Scheduled(fixedRate = 1000)
    public void processUrlResultQueue() throws InterruptedException {
        DeferredResult<EmailCollectorUrlMessage> result = resultUrlQueue.poll();

        if(result != null) {
            EmailCollectorUrlMessage urlMessage = EmailCrawler.urlQueue.poll();

            if(urlMessage != null) {
                result.setResult(urlMessage);
            }
        }
    }
}
