<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<jsp:include page="../general/error_message.jsp"/>

<script type="text/css">
    table {
        table-layout: fixed;
        word-wrap: break-word;
    }
</script>

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

<div class="page-header">
    <h1>
        <spring:message code="label.user.add"/>
    </h1>
</div>

<!-- /.page-header -->
<div class="row">
    <div class="col-xs-12">

        <c:url var="url" value="/main/admin/saveAddUser"/>

        <!-- #dialog-confirm -->
        <form:form id="userAddForm"
                   class="form-horizontal"
                   commandName="user"
                   action="${url}"
                   method="POST" cssClass="form-horizontal">
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-email-address"><spring:message
                        code="label.user.email_address"></spring:message></label>

                <div class="col-sm-9">
                    <form:input
                            id="form-email-address"
                            path="emailAddress"
                            value="${userRequestDTO.emailAddress}"
                            placeholder="${email_address}"
                            maxlength="50"
                            class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-password"><spring:message
                        code="label.user.password"/></label>

                <div class="col-sm-9">
                    <form:input type="password"
                                name="password"
                                path="password"
                                maxlength="20"
                                id="form-password"
                                class="col-xs-10 col-sm-5"/>
                    <span class="help-inline col-xs-12 col-sm-7">
                    <label class="middle">
                        <input class="ace" type="checkbox" id="id-generate-password"
                               name="generatePassword"/>
                    <span class="lbl"><spring:message
                            code="label.user.generate_password"/></span>
                    </label>
                    </span>
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
                                name="lastName"
                                path="lastName"
                                maxlength="50"
                                placeholder="${lastName}"
                                value="${userRequestDTO.lastName}"
                                class="col-xs-10 col-sm-5"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-phone-no">
                    <spring:message code="label.user.phone_no"></spring:message>
                </label>

                <div class="col-sm-9">
                    <form:input
                            class="input-mask-phone" type="text"
                            value="${userRequestDTO.phoneNo}"
                            path="phoneNo"
                            placeholder="${phoneNo}"
                            name="phoneNo"
                            id="form-phone-no"/>
                </div>
            </div>


            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-user-role"><spring:message
                        code="label.user.userrole"></spring:message></label>

                <div class="col-sm-9">
                    <form:select path="userRole" class="form-control width-40"
                                 id="form-user-role" name="userRole">
                        <form:options items="${userRoleList}" itemValue="roleId"/>
                    </form:select>
                </div>
            </div>

            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button id="id-btn-save" class="btn btn-info" onclick="submitSelectedBranchCode()" type="submit">
                        <i class="icon-ok bigger-110"></i>
                        <spring:message code="label.save"/>
                    </button>

                    &nbsp; &nbsp; &nbsp;
                    <button id="back_to_user_id_list" class="btn" onclick="backToUserList()" type="reset">
                        <i class="icon-undo bigger-110"></i>
                        <spring:message code="label.user.back_to_user_list"/>
                    </button>
                </div>
            </div>
        </form:form>
    </div>
</div>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    $(document).ready(function () {
        $('#form-user-role').val(0);
    });

    function backToUserList() {

        window.location.replace("${pageContext.request.contextPath}/main/admin/users/1");
    }

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
    $('.input-mask-phone').mask('(999) 9999-9999');

</script>


