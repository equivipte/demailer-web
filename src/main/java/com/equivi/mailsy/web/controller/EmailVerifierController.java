package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.emailer.EmailVerifierResult;
import com.equivi.mailsy.dto.quota.QuotaDTO;
import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.emailverifier.EmailVerifierServiceFactory;
import com.equivi.mailsy.service.emailverifier.VerifierService;
import com.equivi.mailsy.service.quota.QuotaService;
import com.equivi.mailsy.util.WebConfigUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/main/merchant")
public class EmailVerifierController {

    @Resource(name = "emailVerifierServiceFactory")
    private EmailVerifierServiceFactory emailVerifierServiceFactory;

    @Autowired
    private QuotaService quotaService;

    @RequestMapping(value = "emailverifier", method = RequestMethod.GET)
    public String getEmailVerifierPage(Model model) {

        QuotaDTO quota = quotaService.getQuota();
        long emailVerifyQuota = quota.getEmailVerifyQuota();
        long currentEmailsVerified = quota.getCurrentEmailsVerified();

        boolean quotaExceeded = currentEmailsVerified >= emailVerifyQuota ? true : false;

        model.addAttribute("quotaExceeded", quotaExceeded);
        model.addAttribute("quota", quota);

        return "emailVerifierPage";
    }

    @RequestMapping(value = "emailverifier", method = RequestMethod.POST,
            headers = {"Content-type=application/json"},
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    EmailVerifierResult verifyEmail(@RequestBody EmailVerifierResult emailVerifierResult) {
        VerifierService verifierService = emailVerifierServiceFactory.getEmailVerifierService(WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_VERIFIER_SERVICE));
        return verifierService.getEmailAddressStatus(emailVerifierResult.getEmailAddress());
    }

    @RequestMapping(value = "emailverifier/verify", method = RequestMethod.GET)
    public String verifyEmail(HttpServletRequest request, Model model) {
        List<EmailVerifierResult> emailVerifierResponses = Lists.newArrayList();
        List<String> resultEmails = (List<String>) request.getSession().getAttribute(EmailCollectorController.SESSION_RESULT_EMAILS);

        boolean partiallyVerified = false;

        if (CollectionUtils.isNotEmpty(resultEmails)) {
            List<String> emailsToBeVerified;

            QuotaDTO quota = quotaService.getQuota();
            long emailVerifyQuota = quota.getEmailVerifyQuota();
            long currentEmailsVerified = quota.getCurrentEmailsVerified();
            int remainingEmailsToBeVerified = (int) (emailVerifyQuota - currentEmailsVerified);

            if(remainingEmailsToBeVerified < resultEmails.size()) {
                partiallyVerified = true;

                emailsToBeVerified = resultEmails.subList(0, remainingEmailsToBeVerified);
            } else {
                emailsToBeVerified = resultEmails;
            }

            QuotaDTO quotaDTO = new QuotaDTO();
            quotaDTO.setCurrentEmailsVerified(emailsToBeVerified.size());

            quotaDTO = quotaService.saveQuotaEntity(quotaDTO);

            model.addAttribute("quota", quotaDTO);

            for (String email : emailsToBeVerified) {
                emailVerifierResponses.add(new EmailVerifierResult()
                        .setEmailAddressResult(email)
                        .setStatusResult("UNAVAILABLE")
                        .setInfoDetailResult("Not Available"));
            }

            model.addAttribute("emailVerifierList", emailVerifierResponses);
        }

        model.addAttribute("partiallyVerified", partiallyVerified);

        request.getSession().removeAttribute(EmailCollectorController.SESSION_RESULT_EMAILS);

        return "emailVerifierPage";
    }

    @RequestMapping(value = "emailverifier/exportToExcel", method = RequestMethod.GET)
    public ModelAndView exportToExcel(Map<String, Object> model, HttpServletRequest request) throws IOException {
        List<String> verifiedEmails = (List<String>) request.getSession().getAttribute(EmailCollectorController.SESSION_RESULT_EMAILS);


        model.put(EmailCollectorController.KEY_RESULT_EMAILS, verifiedEmails);

        ModelAndView mav = new ModelAndView("verifierResultExcelView");
        request.getSession().removeAttribute(EmailCollectorController.SESSION_RESULT_EMAILS);

        return mav;
    }
}
