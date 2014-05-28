<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../general/error_message.jsp"/>
<c:url var="url" value="/main/forceChangePassword"/>

<div class="page-header">
    <h1>
        <spring:message code="label.change.password"/>
    </h1>
</div>
<div class="row">
    <div class="col-xs-12">

        <!-- #dialog-confirm -->
        <form:form id="changePasswordForm"
                   class="form-horizontal"
                   commandName="password"
                   action="${url}"
                   method="POST">

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-password"><spring:message
                        code="label.old.password"/></label>

                <div class="col-sm-9">
                    <input type="password"
                           name="old-password"
                           path="old-password"
                           placeholder="Old Password"
                           maxlength="20"
                           id="form--old-password"
                           class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-password"><spring:message
                        code="label.password"/></label>

                <div class="col-sm-9">
                    <input type="password"
                           name="password"
                           path="password"
                           placeholder="Password"
                           maxlength="20"
                           id="form-password"
                           class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-password"><spring:message
                        code="label.password_confirmation"/></label>

                <div class="col-sm-9">
                    <input type="password"
                           name="password-confirmation"
                           path="password-confirmation"
                           placeholder="Password Confirmation"
                           maxlength="20"
                           id="form-password-confirm"
                           class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button id="id-btn-save" class="btn btn-info" type="submit">
                        <i class="icon-ok bigger-110"></i>
                        <spring:message code="label.change.password"/>
                    </button>
                </div>
            </div>

        </form:form>
    </div>
</div>