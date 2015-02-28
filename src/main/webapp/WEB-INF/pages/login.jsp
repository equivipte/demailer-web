<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:if test="${not empty success_message}">
    <div class="alert alert-success">
        <a class="close" data-dismiss="alert">x</a>
        <strong><spring:message code="${success_message}"/></strong>
    </div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">
        <a class="close" data-dismiss="alert">x</a>
        <strong>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </strong>
    </div>
</c:if>
<div class="main-content">
    <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
            <div class="login-container">
                <div class="center">
                    <h1>
                        <img src="${pageContext.request.contextPath}/resources/images/mailsy_front.png"/>
                    </h1>
                    <h4 class="blue"><spring:message code="label.copyright"/></h4>
                    <img src="${pageContext.request.contextPath}/resources/images/equivi_logo.png"/>
                </div>

                <div class="space-6"></div>

                <div class="position-relative">
                    <div id="login-box" class="login-box visible widget-box no-border">
                        <div class="widget-body">
                            <div class="widget-main">
                                <h4 class="header blue lighter bigger">
                                    <spring:message code="label.please_enter_your_information"/>
                                </h4>

                                <div class="space-6"></div>
                                <![if !IE]>
                                <jsp:include page="login_content_non_IE.jsp"/>
                                <![endif]>

                                <!--[if gte IE 10]>
                                <jsp:include page="login_content_non_IE.jsp"/>
                                <![endif]-->

                                <!--[if lt IE 10]>
                                <jsp:include page="login_content_IE.jsp"/>
                                <![endif]-->


                            </div>
                            <!-- /widget-main -->

                            <div class="toolbar clearfix">
                                <div>
                                    <a href="#" onclick="show_box('forgot-box'); return false;"
                                       class="forgot-password-link">
                                        <i class="icon-arrow-left"></i>
                                        <spring:message code="label.forget_password"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <!-- /widget-body -->
                    </div>
                    <!-- /login-box -->

                    <jsp:include page="forget_password.jsp"/>
                </div>
                <!-- /widget-body -->
            </div>
            <!-- /signup-box -->
        </div>
        <!-- /position-relative -->
    </div>
</div>
<!-- /.col -->
</div>
<!-- /.row -->
</div>
<script type="text/javascript">
    function show_box(id) {
        jQuery('.widget-box.visible').removeClass('visible');
        jQuery('#' + id).addClass('visible');
    }
</script>

