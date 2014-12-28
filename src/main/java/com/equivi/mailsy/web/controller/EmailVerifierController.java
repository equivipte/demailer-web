package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.emailer.EmailVerifierResult;
import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;
import com.equivi.mailsy.service.emailverifier.EmailVerifierServiceFactory;
import com.equivi.mailsy.service.emailverifier.VerifierService;
import com.equivi.mailsy.util.EmailVerifierQuotaUtil;
import com.equivi.mailsy.util.WebConfigUtil;
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

    @Resource
    private EmailVerifierQuotaUtil emailVerifierQuotaUtil;

    @RequestMapping(value = "emailverifier", method = RequestMethod.GET)
    public String getEmailVerifierPage(Model model) {

        emailVerifierQuotaUtil.validateExceedQuota(model);

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
        List<String> resultEmails = (List<String>) request.getSession().getAttribute(EmailCollectorController.SESSION_RESULT_EMAILS);

        emailVerifierQuotaUtil.updateEmailVerificationModel(model, resultEmails);

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
