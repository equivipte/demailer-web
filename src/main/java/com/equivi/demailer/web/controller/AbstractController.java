package com.equivi.demailer.web.controller;

import com.equivi.demailer.data.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;


public abstract class AbstractController {


    protected void setPagination(Model model, Page<? extends BaseEntity> page) {
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("deploymentLog", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
    }
}
