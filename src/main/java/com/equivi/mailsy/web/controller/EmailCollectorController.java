package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.emailer.EmailCollector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


@Controller
public class EmailCollectorController {
    @RequestMapping(value = "/main/emailcollector", method = RequestMethod.GET)
    public String getEmailCollectorPage(Model model) {
        model.addAttribute("resultList", getEmailCollectorResults());
        return "emailCollectorPage";
    }

    private List<EmailCollector> getEmailCollectorResults() {
        List<EmailCollector> emailCollectorsList = new ArrayList<>();

        EmailCollector emailCollectorDummy = new EmailCollector();
        emailCollectorDummy.setEmailAddress("gerrardkoh@equivi.com");
        emailCollectorDummy.setSite("http://www.equivi.com");

        EmailCollector emailCollectorDummy2 = new EmailCollector();
        emailCollectorDummy2.setEmailAddress("esthertan@equivi.com");
        emailCollectorDummy2.setSite("http://www.equivi.com");

        EmailCollector emailCollectorDummy3 = new EmailCollector();
        emailCollectorDummy3.setEmailAddress("unknown@equivi.com");
        emailCollectorDummy3.setSite("http://www.equivi.com");

        emailCollectorsList.add(emailCollectorDummy);
        emailCollectorsList.add(emailCollectorDummy2);
        emailCollectorsList.add(emailCollectorDummy3);


        return emailCollectorsList;
    }
}
