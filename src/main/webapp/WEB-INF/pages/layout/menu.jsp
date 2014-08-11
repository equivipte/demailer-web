<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<div class="sidebar" id="sidebar">
    <script type="text/javascript">
        try {
            ace.settings.check('sidebar', 'fixed')
        } catch (e) {
        }
    </script>
    <ul class="nav nav-list" id="navigation-menu">

        <c:if test="${currentPage.currentSubMenu == 'EMAIL_COLLECTOR'}">
        <li id="email-collector" class="active">
            </c:if>

            <c:if test="${currentPage.currentSubMenu != 'EMAIL_COLLECTOR'}">
        <li id="email-collector">
            </c:if>
            <a href="${pageContext.request.contextPath}/main/emailcollector/new">
                <i class="icon-search"></i>
                <spring:message code="label.menu.email.collector"/>
            </a>
        </li>

        <c:if test="${currentPage.currentSubMenu == 'EMAIL_VERIFIER'}">
        <li id="email-verifier" class="active">
            </c:if>

            <c:if test="${currentPage.currentSubMenu != 'EMAIL_VERIFIER'}">
        <li id="email-verifier">
            </c:if>
            <a href="${pageContext.request.contextPath}/main/emailverifier">
                <i class="icon-ok-sign"></i>
                <spring:message code="label.menu.email.verifier"/>
            </a>
        </li>

        <c:if test="${currentPage.currentSubMenu == 'CAMPAIGN_MANAGEMENT'}">
        <li id="campaign-management" class="active">
            </c:if>

        <c:if test="${currentPage.currentSubMenu != 'CAMPAIGN_MANAGEMENT'}">
        <li id="campaign-management">
            </c:if>
            <a href="${pageContext.request.contextPath}/main/campaignmanagement">
                <i class="icon-envelope"></i>
                <spring:message code="label.menu.campaign"/>
            </a>
        </li>

        <c:if test="${currentPage.currentSubMenu == 'SUBSCRIBER_MANAGEMENT'}">
        <li id="contact-management" class="active">
            </c:if>

            <c:if test="${currentPage.currentSubMenu != 'SUBSCRIBER_MANAGEMENT'}">
        <li id="contact-management">
            </c:if>
            <a href="${pageContext.request.contextPath}/main/subscriber_management/1">
                <i class="icon-book"></i>
                <spring:message code="label.menu.subscriber"/>
            </a>
        </li>

        <c:if test="${currentPage.currentSubMenu == 'REPORT'}">
        <li id="report" class="active">
            </c:if>

            <c:if test="${currentPage.currentSubMenu != 'REPORT'}">
        <li id="report">
            </c:if>
            <a href="${pageContext.request.contextPath}/main/report">
                <i class="icon-bar-chart"></i>
                <spring:message code="label.menu.email.report"/>
            </a>
        </li>

        <c:if test="${currentPage.currentSubMenu == 'USER_MANAGEMENT'}">
        <li id="user-management" class="active">
            </c:if>

            <c:if test="${currentPage.currentSubMenu != 'USER_MANAGEMENT'}">
        <li id="user-management">
            </c:if>
            <a href="${pageContext.request.contextPath}/main/admin/users/1">
                <i class="icon-user"></i>
                <spring:message code="label.menu.administration.usermanagement"/>
            </a>
        </li>
</div>

<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'fixed')
    } catch (e) {
    }
</script>

