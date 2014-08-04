<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
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
                   name="contactGroupName"
                   path="contactGroupName"
                   size="30"
                   maxlength="30"
                   value="${fn:escapeXml(param.userName)}"
                   placeholder="Contact Group Name"
                   class="col-lg-12"/>
        </div>
    </td>
</table>

<script type="text/javascript">
    $(".add-user-button").click(function (event) {
        window.location.replace("${pageContext.request.contextPath}/main/admin/user/create");
    });
</script>
