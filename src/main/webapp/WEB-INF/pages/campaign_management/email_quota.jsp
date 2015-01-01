<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<input type="hidden" id="emailSendingQuota" value="${quota.emailSendingQuota}">
<input type="hidden" id="emailSendingQuotaUsed" value="${quota.currentEmailsSent}">

<div id="quota" class="alert alert-info hide">
    <div class="panel-heading">
        <h3 class="panel-title"><spring:message code="label.quota.header"/></h3>
    </div>
    <div class="panel-body">
        <spring:message code="label.quota.emailsend.nonexceed"
                        arguments="${quota.emailSendingQuota},${quota.currentEmailsSent}" htmlEscape="false"/>
    </div>
</div>

<div id="quota-exceeded" class="alert alert-danger hide">
    <div class="panel-heading">
        <h3 class="panel-title"><spring:message code="label.quota.header"/></h3>
    </div>
    <div class="panel-body">
        <spring:message code="label.quota.emailsend.exceed" arguments="${quota.emailSendingQuota}" htmlEscape="false"/>
    </div>
</div>

<script type="text/javascript">
    jQuery(window).load(function () {
        // quota info
        var emailSendingQuota = parseInt($("#emailSendingQuota").val());
        var emailSendingQuotaUsed = parseInt($("#emailSendingQuotaUsed").val());

        if (emailSendingQuotaUsed >= emailSendingQuota) {
            $('#quota-exceeded').toggleClass("hide");
        } else {
            $('#quota').toggleClass("hide");
        }
    });

</script>