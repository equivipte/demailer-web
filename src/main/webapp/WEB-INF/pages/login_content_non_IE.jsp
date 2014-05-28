<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<form action="${pageContext.request.contextPath}/login" method="post">
    <fieldset>
        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control"
                                                                   name="username" required autofocus
                                                                   placeholder="<spring:message code="login.email_address"/>"/>

															<i class="icon-user"></i>
														</span>
        </label>

        <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															 <input type="password" class="form-control" placeholder=
                                                             <spring:message code="login.password"/> required
                                                                    name="password">

															<i class="icon-lock"></i>
														</span>
        </label>

        <div class="space"></div>

        <div class="clearfix">
            <label class="inline">
                <input type="checkbox" class="ace"/>
                <span class="lbl"> Remember Me</span>
            </label>

            <button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
                <i class="icon-key"></i>
                <spring:message code="login.login"/>
            </button>
        </div>

        <div class="space-4"></div>
    </fieldset>
</form>