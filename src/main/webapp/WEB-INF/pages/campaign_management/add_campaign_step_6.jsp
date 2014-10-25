<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="page-header">
    <h1>
        <spring:message code="label.create.campaign"/>
    </h1>
</div>


<c:set var="sendDate"><spring:message code="label.campaign.scheduled_send_date"/></c:set>

<jsp:include page="campaign_wizard_header_step6.jsp"/>
</br>
</br>

<!-- /.page-header -->
<div class="row">
    <div class="col-xs-12">
        <div class="center">
            <h3 class="green">Campaign has been created</h3>
            Your campaign will be queued for delivery.
        </div>
    </div>
</div>
</br>
</br>
<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button id="back_to_user_id_list" class="btn" onclick="backToCampaignList()">
            <i class="icon-undo bigger-110"></i>
            <spring:message code="label.campaign.back_to_campaign_list"/>
        </button>
    </div>
</div>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    function backToCampaignList() {

        window.location.replace("${pageContext.request.contextPath}/main/merchant/campaign_management/1");
    }
</script>