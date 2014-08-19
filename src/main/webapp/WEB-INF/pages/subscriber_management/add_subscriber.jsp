<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<div id="modal-form-subscriber" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="blue bigger"><spring:message code="label.change.password"/></h4>
            </div>
            <div id="error-block" class="alert alert-danger" style="display: none">
                <button type="button" class="close" data-dismiss="alert">
                    <i class="icon-remove"></i>
                </button>

                <strong>
                    <i class="icon-remove"></i>
                    <label id="error-message"></label>
                </strong>
                <br/>
            </div>

            <div class="modal-body overflow-visible">
                <div class="row">
                    <div class="col-xs-12 col-sm-7">
                        <div class="form-group">
                            <label for="old-password"><spring:message code="label.old.password"/></label>

                            <div>
                                <input class="input-large" type="password"
                                       name="old-password"
                                       id="old-password"
                                       maxlength="30"
                                       placeholder="<spring:message code="label.old.password"/>"/>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="form-group">
                            <label for="password"><spring:message code="label.password"/></label>

                            <div>
                                <input class="input-large"
                                       type="password"
                                       name="password"
                                       id="password"
                                       maxlength="30"
                                       placeholder="<spring:message code="label.password"/>"/>
                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label for="password-confirmation"><spring:message
                                    code="label.password_confirmation"/></label>

                            <div>
                                <input class="input-large"
                                       type="password"
                                       name="password-confirmation"
                                       id="password-confirmation"
                                       maxlength="30"
                                       placeholder="<spring:message code="label.password_confirmation"/>"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button class="btn btn-sm" data-dismiss="modal">
                    <i class="icon-remove"></i>
                    <spring:message code="label.cancel"/>
                </button>

                <button class="btn btn-sm btn-primary" onclick="changePassword()" type="button">
                    <i class="icon-ok"></i>
                    <spring:message code="label.save"/>
                </button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function () {
        $("#success_message").css("display", "none");
    });

    function changePassword() {
        $('#loading').html('<i class="icon-spinner icon-spin orange bigger-280"></i>');

        // Capture the href from the selected link...
        var oldPassword = $("#old-password").val();
        var password = $("#password").val();
        var password_confirmation = $("#password-confirmation").val();

        $.ajax({
            url: "${pageContext.request.contextPath}/main/changePassword?old-password=" + oldPassword + "&password=" + password + "&password-confirmation=" + password_confirmation,
            type: "POST",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("An error has occurred making the request: " + errorThrown);

            },
            success: function (data) {
                if (data != 'SUCCESS') {
                    $("#loading").html("");
                    $("#error-block").css("display", "none");
                    $("#error-block").css("display", "block");
                    $("#error-message").text(data);
                }
                else {
                    window.location.replace("${pageContext.request.contextPath}/login-page?originatedPage=changePasswordPage");
                }
            }
        });

    }
</script>