<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="context" value="${pageContext.request.contextPath}"/>


<div class="page-header">
    <h1>
        <spring:message code="label.menu.email.verifier"/>
    </h1>
</div>

<div id="dialog-message" class="hide">
    <p>
        Nothing to select
    </p>
</div>
<div id="table_result" class="table-responsive">
    <table id="table-transaction" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th><spring:message code="label.common.emailaddress"/></th>
            <th><spring:message code="label.emailverifier.status.description"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${emailVerifyResultList}" var="email">
            <tr>
                <td>${email.emailAddress}</td>
                <td>
                    <c:if test="${email.status == 'VALID'}">
                        <span class="label label-sm label-success">${email.statusDescription}</span>
                    </c:if>
                    <c:if test="${email.status == 'INVALID'}">
                        <span class="label label-sm label-danger">${email.statusDescription}</span>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="pull-left">
    <a href="#">
        <button class="btn btn-info" id="export-btn">
            <i class="icon-mail-forward white bigger-120"></i>
            <spring:message code="label.common.export_results_to_excel"/>
        </button>
    </a>

    <a href="#">
        <button class="btn btn-info" id="import-btn">
            <i class="icon-book white bigger-120"></i>
            <spring:message code="label.emailverifier.import.to.contact"/>
        </button>
    </a>
</div>
</br>
</br>
</br>
<style>
    div#sb-container {
        z-index: 10000;
    }
</style>