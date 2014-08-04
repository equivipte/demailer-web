package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.dto.contact.ContactDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactManagementController {

    @RequestMapping(value = "/main/contactmanagement", method = RequestMethod.GET)
    public String getContactManagementPage(Model model) {

        List<ContactDTO> contactDTOList = new ArrayList<>();

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactGroupName("Equivi Contact Group 1");
        contactDTO.setContactGroupStatus("ACTIVE");
        contactDTO.setLastUpdatedDate("2014/07/30 10:00");

        ContactDTO contactDTO2 = new ContactDTO();
        contactDTO2.setContactGroupName("Equivi Contact Group 2");
        contactDTO2.setContactGroupStatus("INACTIVE");
        contactDTO2.setLastUpdatedDate("2014/07/30 11:00");

        ContactDTO contactDTO3 = new ContactDTO();
        contactDTO3.setContactGroupName("Equivi Contact Group 3");
        contactDTO3.setContactGroupStatus("DISABLED");
        contactDTO3.setLastUpdatedDate("2014/07/30 10:00");

        contactDTOList.add(contactDTO);
        contactDTOList.add(contactDTO2);
        contactDTOList.add(contactDTO3);

        model.addAttribute("contactGroupList", contactDTOList);

        return "contactManagementPage";
    }
}
