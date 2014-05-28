<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../general/error_message.jsp"/>

<div id="error-block-message" class="alert alert-danger" style="display: none">
    <button type="button" class="close" data-dismiss="alert">
        <i class="icon-remove"></i>
    </button>

    <strong>
        <i class="icon-remove"></i>
        <label id="error-message-user"></label>
    </strong>
    <br/>
</div>


<c:set var="email_address"><spring:message code="label.user.email_address"/></c:set>
<c:set var="firstName"><spring:message code="label.user.first_name"/></c:set>
<c:set var="lastName"><spring:message code="label.user.last_name"/></c:set>
<c:set var="phoneNo"><spring:message code="label.user.phone_no"/></c:set>
<c:set var="passwordDisableNotification"><spring:message code="label.change.password.disable.notification"/></c:set>
<c:set var="phoneNo"><spring:message code="label.user.phone_no"/></c:set>

<div class="page-header">
    <h1>
        <spring:message code="label.user.edit"/>
    </h1>
</div>

<c:url var="url" value="/main/admin/saveUpdateUser"/>

<!-- /.page-header -->
<div class="row">
    <div class="col-xs-12">
        <form:form class="form-horizontal"
                   commandName="user"
                   action="${url}"
                   method="POST">
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-email-address"><spring:message
                        code="label.user.email_address"></spring:message></label>

                <div class="col-sm-9">
                    <form:input type="email"
                                id="form-email-address" value="${userRequestDTO.emailAddress}"
                                name="email"
                                path="emailAddress"
                                maxlength="50"
                                readonly="true"
                                placeholder="${email_address}"
                                class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-password"><spring:message
                        code="label.user.password"/></label>

                <div class="col-sm-9">
                    <c:if test="${change_password_allowed =='true'}">
                        <form:input type="password"
                                    id="form-password"
                                    path="password"
                                    name="password"
                                    value=""
                                    maxlength="20"
                                    class="col-xs-10 col-sm-5"/>
                        <span class="help-inline col-xs-12 col-sm-7">
                            <label class="middle">
                                <input class="ace" type="checkbox" id="id-generate-password"
                                       name="generatePassword"/>
                                <span class="lbl"><spring:message
                                        code="label.user.generate_password"/></span>
                            </label>
                        </span>
                    </c:if>
                    <c:if test="${change_password_allowed =='false'}">
                        <form:input type="password"
                                    id="form-password-disabled"
                                    path="password"
                                    name="password"
                                    maxlength="20"
                                    readonly="true"
                                    title="${passwordDisableNotification}"
                                    class="col-xs-10 col-sm-5"/>
                    </c:if>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-firstname"><spring:message
                        code="label.user.first_name"></spring:message></label>

                <div class="col-sm-9">
                    <form:input type="text" id="form-firstname"
                                path="firstName"
                                placeholder="${firstName}"
                                name="firstName"
                                maxlength="50"
                                value="${userRequestDTO.firstName}"
                                class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-lastname"><spring:message
                        code="label.user.last_name"></spring:message></label>

                <div class="col-sm-9">
                    <form:input type="text" id="form-lastname"
                                path="lastName"
                                name="lastName"
                                maxlength="50"
                                value="${userRequestDTO.lastName}"
                                placeholder="${lastName}"
                                class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-phone-no">
                    <spring:message code="label.user.phone_no"></spring:message>
                </label>

                <div class="col-sm-9">
                    <form:input class="input-mask-phone"
                                path="phoneNo" type="text"
                                value="${userRequestDTO.phoneNo}"
                                placeholder="${phoneNo}"
                                name="phoneNo"
                                id="form-phone-no"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-user-select-status"><spring:message
                        code="label.user.userstatus"></spring:message></label>

                <div class="col-sm-9">
                    <form:select class="form-control width-40" id="form-user-select-status" path="userStatus"
                                 items="${userStatusList}" itemValue="userStatusId" name="userStatus">
                        <form:options items="${userStatusList}" itemValue="userStatusId"/>
                    </form:select>

                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-user-role"><spring:message
                        code="label.user.userrole"></spring:message></label>

                <div class="col-sm-9">
                    <form:select class="form-control width-40" id="form-user-role" path="userRole" name="userRole">
                        <form:options items="${userRoleList}" itemValue="roleId"/>
                    </form:select>
                </div>
            </div>

            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info" type="submit">
                        <i class="icon-ok bigger-110"></i>
                        <spring:message code="label.save"/>
                    </button>

                    &nbsp; &nbsp; &nbsp;
                    <button class="btn" type="button" onclick="backToUserList()">
                        <i class="icon-undo bigger-110"></i>
                        <spring:message code="label.user.back_to_user_list"/>
                    </button>


                </div>
            </div>
            <form:input type="hidden" path="userId" name="userId" value="${userRequestDTO.userId}"/>
            <form:input type="hidden" id="branchCodeSelected" path="branchCodeSelected" value=""/>
        </form:form>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#form-password').val('');
        $("#form-password-disabled").tooltip({
            show: {
                effect: "slideDown",
                delay: 250
            }
        });

        //Disable locked
        $("select option:contains('Locked')").attr("disabled", "disabled");
    });

    $('#id-generate-password').on('click', function () {
        var inp = $('#form-password').get(0);
        if (inp.hasAttribute('disabled')) {
            inp.setAttribute('enabled', 'true');
            inp.removeAttribute('disabled');
            inp.value = "";
        }
        else {
            inp.setAttribute('disabled', 'true');
            inp.removeAttribute('enabled');
            inp.value = "*********";
        }
    });

    function backToUserList() {

        window.location.replace("${pageContext.request.contextPath}/main/admin/users/1");
    }

</script>

