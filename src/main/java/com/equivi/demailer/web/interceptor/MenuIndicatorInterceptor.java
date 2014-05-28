package com.equivi.demailer.web.interceptor;


import com.equivi.demailer.web.constant.MenuUrlPathMapping;
import com.equivi.demailer.web.context.CurrentPage;
import com.equivi.demailer.web.context.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MenuIndicatorInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MenuIndicatorInterceptor.class);

    @Resource
    private SessionUtil sessionUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        MenuUrlPathMapping menuUrlPathMapping = MenuUrlPathMapping.getMenuUrlPathMapping(request.getServletPath());
        if (menuUrlPathMapping != null) {
            CurrentPage currentPage = sessionUtil.getCurrentPage();
            currentPage.setCurrentMenu(menuUrlPathMapping.getMenu());
            currentPage.setCurrentSubMenu(menuUrlPathMapping.getSubMenu());
            sessionUtil.setCurrentPage(request, currentPage);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {


    }
}
