package com.equivi.demailer.web.context;


import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CurrentPage implements Serializable {

    private static final long serialVersionUID = 6713440828892618763L;

    private String currentMenu;

    private String currentSubMenu;


    public String getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(String currentMenu) {
        this.currentMenu = currentMenu;
    }

    public String getCurrentSubMenu() {
        return currentSubMenu;
    }

    public void setCurrentSubMenu(String currentSubMenu) {
        this.currentSubMenu = currentSubMenu;
    }
}
