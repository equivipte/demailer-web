<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<form action="${context}/main/admin/users/1">
    <table>
        <td>
            <a href="#" class="add-user-button">
                <button class="btn btn-info" type="button">
                    <i class="icon-plus-sign white bigger-120"></i>
                    <spring:message code="label.add"/>
                </button>
            </a>
        </td>
        <td>
            <a href="#">
                <button class="btn btn-info">
                    <i class="icon-search white bigger-120"></i>
                    <spring:message code="label.search"/>
                </button>
            </a>
        </td>
        <td>

            <div>
                <input type="text"
                       name="userName"
                       path="userName"
                       size="30"
                       maxlength="30"
                       value="${fn:escapeXml(param.userName)}"
                       placeholder="<spring:message code="label.user.name"/>"
                       class="col-lg-12"/>
            </div>
        </td>
        <td>
            <div class>
                <input type="text"
                       name="emailAddress"
                       path="emailAddress"
                       size="30"
                       maxlength="30"
                       value="${fn:escapeXml(param.emailAddress)}"
                       placeholder="<spring:message code="label.user.email_address"/>"
                       class="col-lg-12"/>
            </div>
        </td>
    </table>
</form>

<script type="text/javascript">
    $(".add-user-button").click(function (event) {
        window.location.replace("${pageContext.request.contextPath}/main/admin/user/create");
    });
</script>
