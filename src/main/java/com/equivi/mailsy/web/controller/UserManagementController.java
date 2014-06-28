package com.equivi.mailsy.web.controller;


import com.equivi.mailsy.data.entity.UserEntity;
import com.equivi.mailsy.data.entity.UserRole;
import com.equivi.mailsy.data.entity.UserStatus;
import com.equivi.mailsy.dto.login.UserLoginDTO;
import com.equivi.mailsy.dto.user.UserRequestDTO;
import com.equivi.mailsy.service.exception.IllegalAccessPageException;
import com.equivi.mailsy.service.exception.InvalidDataException;
import com.equivi.mailsy.service.user.UserSearchFilter;
import com.equivi.mailsy.service.user.UserService;
import com.equivi.mailsy.web.constant.PageConstant;
import com.equivi.mailsy.web.constant.WebConfiguration;
import com.equivi.mailsy.web.constant.WebRequestConstant;
import com.equivi.mailsy.web.context.SessionUtil;
import com.equivi.mailsy.web.message.ErrorMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class UserManagementController {

    private static final String NOTIFICATION_CHANGE_PASSWORD_SUCCESSFULLY = "change.password.succesfully.notification.user";
    @Resource
    private UserService userService;

    @Resource
    private WebConfiguration webConfiguration;

    @Resource
    private ErrorMessage errorMessage;

    @Resource
    private SessionUtil sessionUtil;

    @RequestMapping(value = "/main/admin/users/{pageNumber}", method = RequestMethod.GET)
    public String getUsers(@PathVariable Integer pageNumber, final HttpServletRequest request, Model model) {

        String originPage = request.getParameter(PageConstant.ORIGINATED_PAGE.getPageName());
        if (!StringUtils.isBlank(originPage)) {
            if (originPage.equals(PageConstant.CHANGE_PASSWORD_PAGE.getPageName())) {
                model.addAttribute("success_message", NOTIFICATION_CHANGE_PASSWORD_SUCCESSFULLY);
                model.addAttribute(WebRequestConstant.USER_OBJECT_UPDATED_PASSWORD.getConstantName(), request.getParameter(WebRequestConstant.USER_OBJECT_UPDATED_PASSWORD.getConstantName()));
            }
        }

        Page<UserEntity> userPage = userService.listUser(buildMapFilter(request), pageNumber, webConfiguration.getMaxRecordsPerPage());

        setUserList(model, userPage);

        setPagination(model, userPage);

        return "userManagementPage";
    }

    private Map<UserSearchFilter, String> buildMapFilter(HttpServletRequest request) {

        String userName = request.getParameter(UserSearchFilter.USERNAME.getFilterName());
        String emailAddress = request.getParameter(UserSearchFilter.EMAIL_ADDRESS.getFilterName());


        Map<UserSearchFilter, String> filterMap = new HashMap<>();
        if (!StringUtils.isBlank(userName)) {
            filterMap.put(UserSearchFilter.USERNAME, userName);
        }

        if (!StringUtils.isBlank(emailAddress)) {
            filterMap.put(UserSearchFilter.EMAIL_ADDRESS, emailAddress);
        }

        return filterMap;
    }

    @RequestMapping(value = "/main/admin/user/delete/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(@PathVariable Long userId) {

        try {
            userService.deleteUser(userId);
        } catch (IllegalAccessPageException iex) {
            return "import.process_running.warning.message";
        } catch (Exception ex) {
            return "general.exception.delete";
        }

        return "SUCCESS";
        //return "redirect:/main/admin/users/1";
    }

    @RequestMapping(value = "/main/admin/saveUpdateUser", method = RequestMethod.POST)
    public ModelAndView updateUser(final UserRequestDTO userRequestDTO, final HttpServletRequest httpServletRequest, final BindingResult result, final Locale locale) {

        ModelAndView modelAndView;
        try {
            if (result.hasErrors()) {
                modelAndView = new ModelAndView("userManagementEditPage");
                modelAndView.addObject("errors", errorMessage.buildFormValidationErrorMessages(result, locale));
            } else {

                userService.updateUser(userRequestDTO);

                modelAndView = new ModelAndView();
                String redirectData = "redirect:users/1";
                redirectData = checkIfUserUpdatePassword(userRequestDTO, modelAndView, redirectData);


                modelAndView.setViewName(redirectData);
                return modelAndView;
            }
        } catch (InvalidDataException idex) {
            modelAndView = new ModelAndView("userManagementEditPage");

            //Check if logged in user is current user
            UserEntity userFound = userService.getUser(userRequestDTO.getUserId().longValue());
            checkChangePasswordAllowedForLoggedInUser(httpServletRequest, modelAndView, userFound);

            modelAndView.addObject("errors", errorMessage.buildServiceValidationErrorMessages(idex, locale));
        }

        userRequestDTO.setBranchCodeList(convertToBranchCodeList(userRequestDTO.getBranchCodeSelected()));
        setPredefinedData(modelAndView, userRequestDTO);
        return modelAndView;
    }

    private String checkIfUserUpdatePassword(UserRequestDTO userRequestDTO, ModelAndView modelAndView, String redirectData) {
        //Check if user request dto is update password
        if (!StringUtils.isBlank(userRequestDTO.getPassword())) {
            redirectData = "redirect:users/1?" + PageConstant.ORIGINATED_PAGE.getPageName() + "=" + PageConstant.CHANGE_PASSWORD_PAGE.getPageName();
            modelAndView.addObject(WebRequestConstant.USER_OBJECT_UPDATED_PASSWORD.getConstantName(), userRequestDTO.getEmailAddress());
        }
        return redirectData;
    }


    private List<String> convertToBranchCodeList(String branchCodeSelected) {
        if (branchCodeSelected != null && branchCodeSelected.length() > 0) {
            String[] branchCodeList = branchCodeSelected.split(",");
            return Arrays.asList(branchCodeList);
        }
        return Collections.emptyList();
    }


    @RequestMapping(value = "/main/admin/saveAddUser", method = RequestMethod.POST)
    public ModelAndView addUser(@Valid UserRequestDTO userRequestDTO, BindingResult result, Locale locale) {

        ModelAndView modelAndView;
        try {
            if (result.hasErrors()) {
                modelAndView = new ModelAndView("userManagementAddPage");
                modelAndView.addObject("errors", errorMessage.buildFormValidationErrorMessages(result, locale));
            } else {
                userService.createUser(userRequestDTO);
                modelAndView = new ModelAndView("redirect:users/1");
                return modelAndView;
            }
        } catch (InvalidDataException idex) {
            modelAndView = new ModelAndView("userManagementAddPage");
            modelAndView.addObject("errors", errorMessage.buildServiceValidationErrorMessages(idex, locale));
        }
        setPredefinedData(modelAndView, userRequestDTO);
        return modelAndView;
    }


    @RequestMapping(value = "/main/admin/user/{userId}", method = RequestMethod.GET)
    public ModelAndView goToEditUser(@PathVariable final Long userId, final HttpServletRequest request, ModelAndView modelAndView) {

        UserEntity userFound = userService.getUser(userId.longValue());

        //Check if logged in user is current user
        checkChangePasswordAllowedForLoggedInUser(request, modelAndView, userFound);

        modelAndView.setViewName("userManagementEditPage");
        setPredefinedData(modelAndView, convertToUserRequestDTOEdit(userFound));
        return modelAndView;
    }

    private void checkChangePasswordAllowedForLoggedInUser(HttpServletRequest request, ModelAndView modelAndView, UserEntity userFound) {
        UserLoginDTO userLoginDTO = sessionUtil.getCurrentUser(request);
        if (!userLoginDTO.getUserId().equals(userFound.getId())) {
            modelAndView.addObject("change_password_allowed", "true");
        } else {
            modelAndView.addObject("change_password_allowed", "false");
        }
    }

    @RequestMapping(value = "/main/admin/user/create", method = RequestMethod.GET)
    public ModelAndView goToCreateUser(ModelAndView modelAndView) {

        UserRequestDTO userRequestDTO = new UserRequestDTO();

        modelAndView.setViewName("userManagementAddPage");
        setPredefinedData(modelAndView, userRequestDTO);

        return modelAndView;
    }

    private void setPredefinedData(ModelAndView modelAndView, UserRequestDTO userRequestDTO) {
        modelAndView.addObject("user", userRequestDTO);
        modelAndView.addObject("userRoleList", UserRole.values());
        modelAndView.addObject("userStatusList", UserStatus.values());
        modelAndView.addObject("branchCodeList", userRequestDTO.getBranchCodeList());

    }

    private UserRequestDTO convertToUserRequestDTOEdit(UserEntity userEntity) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserId(userEntity.getId());
        userRequestDTO.setFirstName(userEntity.getFirstName());
        userRequestDTO.setLastName(userEntity.getLastName());
        userRequestDTO.setPassword("");
        userRequestDTO.setUserRole(userEntity.getUserRole().getRoleId());
        userRequestDTO.setEmailAddress(userEntity.getUserName());
        userRequestDTO.setPhoneNo(userEntity.getPhoneNo());
        userRequestDTO.setUserStatus(userEntity.getUserStatus().getUserStatusId());

        return userRequestDTO;
    }

    private void setUserList(Model model, Page<UserEntity> userPage) {
        model.addAttribute("userList", userPage.getContent());
    }

    private void setPagination(Model model, Page<UserEntity> page) {
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("deploymentLog", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
    }

}
