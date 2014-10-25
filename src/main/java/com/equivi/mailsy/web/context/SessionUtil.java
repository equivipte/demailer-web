package com.equivi.mailsy.web.context;


import com.equivi.mailsy.dto.login.UserLoginDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Component
public class SessionUtil implements Serializable {

    private static final long serialVersionUID = 4135864777879232235L;

    @Resource
    private CurrentPage currentPage;

    public void setCurrentPage(HttpServletRequest request, CurrentPage currentPage) {
        this.currentPage = currentPage;
        request.getSession().setAttribute("currentPage", currentPage);
    }

    public void setCurrentUser(HttpServletRequest request, UserLoginDTO userLoginDTO) {
        request.getSession().setAttribute("userLoggedIn", userLoginDTO);
    }

    public UserLoginDTO getCurrentUser(HttpServletRequest request) {
        return (UserLoginDTO) request.getSession().getAttribute("userLoggedIn");
    }

    public CurrentPage getCurrentPage() {
        return currentPage;
    }


}
