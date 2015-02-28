<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<div class="table-recipient">
    <table id="table-transaction" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th><spring:message code="label.campaign.email.recipients"/></th>
        </tr>
        </thead>
        <c:if test="${deliveredList.size() > 0 }">
            <tbody>
            <c:forEach items="${deliveredList}" var="recipient">
                <tr id="${recipient}">
                    <td>${recipient}</td>
                </tr>
            </c:forEach>
            </tbody>
        </c:if>
    </table>
</div>
