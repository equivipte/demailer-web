<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty warning_message}">
    <div class="alert alert-danger">
        <strong><spring:message code="${warning_message}"/></strong>
    </div>
</c:if>
