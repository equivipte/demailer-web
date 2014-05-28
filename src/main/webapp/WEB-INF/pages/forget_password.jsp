<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>


<c:set var="context" value="${pageContext.request.contextPath}"/>

<div id="forgot-box" class="forgot-box widget-box no-border">
    <div class="widget-body">
        <div id="success_message" class="alert alert-block alert-success" style="display: none">
            <button type="button" class="close" data-dismiss="alert">
                <i class="icon-remove"></i>
            </button>
            <p>
                <strong>
                    <i class="icon-ok"></i>
                </strong>
                <spring:message code="label.forget_password.succesfully.send"/>
            </p>
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

        <div id="loading">

        </div>
        <form>

            <div class="widget-main">
                <h4 class="header red lighter bigger">
                    <i class="icon-key"></i>
                    <spring:message code="label.forget_password.retrieve.password"/>
                </h4>

                <div class="space-6"></div>
                <p>
                    <spring:message code="label.forget_password.enter.email"/>
                </p>

                <fieldset>
                    <label class="block clearfix">
                        <span class="block input-icon input-icon-right">
                            <input type="email" name="email_address" id="email_address"
                                   class="form-control"
                                   maxlength="30"
                                   placeholder="Email"/>
                            <i class="icon-envelope"></i>
                        </span>
                    </label>

                    <div class="clearfix">
                        <button type="button" id="send-forget-password"
                                onclick="sendForgetPassword()"
                                class="width-35 pull-right btn btn-sm btn-danger">
                            <i class="icon-lightbulb"></i>
                            <spring:message code="label.forget_password.send"/>
                        </button>
                    </div>
                </fieldset>
            </div>
            <!-- /widget-main -->

            <div class="toolbar center">
                <a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">
                    <spring:message code="label.forget_password.back_to_login"/>
                    <i class="icon-arrow-right"></i>
                </a>
            </div>

            <script type="text/javascript">

                function sendForgetPassword() {
                    $('#loading').html('<i class="icon-spinner icon-spin orange bigger-280"></i>');

                    // Capture the href from the selected link...
                    var emailAddress = $("#email_address").val();


                    $.ajax({
                        url: "${pageContext.request.contextPath}/forgetPassword?email_address=" + emailAddress,
                        type: "POST",
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert("An error has occurred making the request: " + errorThrown);

                        },
                        success: function (data) {
                            if (data != 'SUCCESS') {
                                clearAllMessages();
                                $("#error-block").css("display", "block");
                                $("#error-message").text(data);
                            }
                            else {
                                clearAllMessages();
                                $("#success_message").css("display", "block");
                                $("#email_address").val("");
                            }
                        }
                    });

                }

                function clearAllMessages() {
                    $("#loading").html("");
                    $("#error-block").css("display", "none");
                    $("#success_message").css("display", "none");
                }
            </script>

