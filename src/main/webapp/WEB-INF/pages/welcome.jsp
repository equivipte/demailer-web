<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="jumbotron">
    <p>
        <spring:message code="label.welcome_message"/>
        ${userLoggedIn.firstName}

        ${userLoggedIn.middleName}

    <p>
</div>