<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!-- #dialog-confirm -->
<jsp:include page="../general/delete_confirmation.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<div class="page-header">
    <h1>
        <spring:message code="label.user.page.header"/>
    </h1>
</div>

<c:if test="${userList.size() == 0 }">
    <jsp:include page="../general/no_result_found.jsp"/>
</c:if>

<c:if test="${not empty success_message}">
    <div class="alert alert-success">
        <a class="close" data-dismiss="alert">x</a>
        <spring:message code="${success_message}"/>
        <strong>${user_object_updated_password}</strong>
    </div>
</c:if>
<!-- /.page-header -->

<![if !IE]>
<jsp:include page="user_search_form_non_IE.jsp"/>
<![endif]>

<!--[if gte IE 10]>
<jsp:include page="user_search_form_non_IE.jsp"/>
<![endif]-->

<!--[if lt IE 10]>
<jsp:include page="user_search_form_IE.jsp"/>
<![endif]-->


</br>
</br>
<c:if test="${userList.size() > 0 }">
    <div class="table-responsive">
        <table id="table-user" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th><spring:message code="label.user.name"/></th>
                <th><spring:message code="label.user.email_address"/></th>
                <th><spring:message code="label.user.userrole"/></th>
                <th><spring:message code="label.user.userstatus"/>
                <th><spring:message code="label.user.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.firstName} ${user.lastName}</td>
                    <td>${user.userName}</td>
                    <td><spring:message code="${user.userRole.roleDescription}"/></td>
                    <td>
                        <c:if test="${user.userStatus.userStatusDescription == 'ACTIVE'}">
                             <span class="label label-sm label-success"><spring:message
                                     code="${user.userStatus.userStatusDescription}"/></span>
                        </c:if>
                        <c:if test="${user.userStatus.userStatusDescription == 'INACTIVE'}">
                             <span class="label label-sm label-grey"><spring:message
                                     code="${user.userStatus.userStatusDescription}"/></span>
                        </c:if>
                        <c:if test="${user.userStatus.userStatusDescription == 'LOCKED'}">
                        <span class="label label-sm label-danger"><spring:message
                                code="${user.userStatus.userStatusDescription}"/></span>
                        </c:if>
                    </td>
                    <td>
                        <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                            <button class="btn btn-xs btn-info" onclick="redirectToEdit(${user.id})">
                                <i class="icon-edit bigger-120"></i>
                            </button>

                            <button class="btn btn-xs btn-danger" onclick="deleteUser(${user.id})">
                                <i class="icon-trash bigger-120"></i>
                            </button>
                        </div>
                        <div class="visible-xs visible-sm hidden-md hidden-lg">
                            <div class="inline position-relative">
                                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
                                    <i class="icon-cog icon-only bigger-110"></i>
                                </button>
                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
                                    <li>
                                        <a id="${user.id}" href="#" class="tooltip-success editUser" data-rel="tooltip"
                                           title="<spring:message code="label.edit"/>">
                                            <span class="green">
                                                <i class="icon-edit bigger-120"></i>
                                            </span>
                                        </a>
                                    </li>

                                    <li>
                                        <a id="${user.id}" href="#" class="tooltip-error deleteUser" data-rel="tooltip"
                                           class="deleteUser"
                                           title="<spring:message code="label.user.delete"/>">
                                            <span class="red">
                                                <i class="icon-trash bigger-120"></i>
                                            </span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <c:set var="paginationParameter"
           value="userName=${param.userName}&emailAddress=${param.emailAddress}"/>

    <c:url var="firstUrl" value="/main/admin/users/1?${paginationParameter}"/>
    <c:url var="lastUrl" value="/main/admin/users/${deploymentLog.totalPages}?${paginationParameter}"/>
    <c:url var="prevUrl" value="/main/admin/users/${currentIndex - 1}?${paginationParameter}"/>
    <c:url var="nextUrl" value="/main/admin/users/${currentIndex + 1}?${paginationParameter}"/>


    <ul class="pagination pull-right no-margin">
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><a href="#"><i class="icon-double-angle-left"></i></a></li>
                <li class="disabled"><a href="#"><i class="icon-angle-left"></i></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${firstUrl}"><i class="icon-double-angle-left"></i></a></li>
                <li><a href="${prevUrl}"><i class="icon-angle-left"></i></a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="/main/admin/users/${i}?${paginationParameter}"/>
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex == deploymentLog.totalPages}">
                <li class="disabled"><a href="#"><i class="icon-angle-right"></i></a></li>
                <li class="disabled"><a href="#"><i class="icon-double-angle-right"></i></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${nextUrl}"><i class="icon-angle-right"></i></a></li>
                <li><a href="${lastUrl}"><i class="icon-double-angle-right"></i></a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</c:if>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    $(document).ready(function () {
        $(".editUser").click(function (event) {
            var userId = $(this).attr("id");
            redirectToEdit(userId)
        });

        $(".deleteUser").click(function (event) {
            var userId = $(this).attr("id");
            deleteUser(userId)
        });

    });


    function redirectToEdit(id) {
        window.location.replace("${context}/main/admin/user/" + id);
    }

    function deleteUser(id) {
        var href = "${context}/main/admin/users/1";
        $("#dialog-confirm").removeClass('hide').dialog({
            resizable: false,
            modal: true,
            title: "<div class='widget-header'><h4 class='smaller'><i class='icon-warning-sign red'></i><spring:message code="label.user.delete"/></h4></div>",
            title_html: true,
            buttons: [
                {
                    html: "<i class='icon-trash bigger-110'></i>&nbsp; <spring:message code="label.user.delete"/> ",
                    "class": "btn btn-danger btn-xs",
                    click: function () {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/main/admin/user/delete/" + id,
                            type: "DELETE",
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                alert("An error has occurred making the request: " + errorThrown);

                            },
                            success: function (data) {
                                if (data != 'SUCCESS') {
                                    if (data == 'import.process_running.warning.message') {
                                        alert("<spring:message code="import.process_running.warning.message"/>");
                                    }
                                    else {
                                        alert("<spring:message code="general.exception.delete"/>");
                                    }
                                }
                                else {
                                    //Do stuff here on success such as modal info
                                    $("#dialog-confirm").dialog("close");
                                    window.location.replace(href);
                                }

                            }
                        });
                    }
                }
                ,
                {
                    html: "<i class='icon-remove bigger-110'></i>&nbsp; <spring:message code="label.cancel"/> ",
                    "class": "btn btn-xs",
                    click: function () {
                        $(this).dialog("close");
                    }
                }
            ]
        });
    }

</script>