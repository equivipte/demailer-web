<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="page-header">
    <h1>
        <spring:message code="label.campaign.email.review_and_confirm"/>
    </h1>
</div>


<c:set var="sendDate"><spring:message code="label.campaign.scheduled_send_date"/></c:set>

<jsp:include page="campaign_wizard_header_step4.jsp"/>
</br>
</br>

<div class="widget-header widget-header-flat widget-header-small">
    <h5>
        <spring:message code="label.campaign.info"/>
    </h5>
</div>
<div class="widget-body">
    <div class="widget-main">
        <div class="row">
            <div class="col-xs-12">

                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right"><b><spring:message
                            code="label.campaign.name"></spring:message></b></label>

                    <div class="col-sm-9">
                        <label class="col-sm-3 control-label no-padding-right">${campaignDTO.campaignName}</label>
                    </div>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right"><b><spring:message
                            code="label.campaign.email.from"></spring:message></b></label>

                    <div class="col-sm-9">
                        <label class="col-sm-3 control-label no-padding-right">${campaignDTO.emailFrom}</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right"><b><spring:message
                            code="label.campaign.subject"></spring:message></b></label>

                    <div class="col-sm-9">
                        <label class="col-sm-3 control-label no-padding-right">${campaignDTO.emailSubject}</label>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</br>
</br>


<div class="widget-header widget-header-flat widget-header-small">
    <h5>
        <spring:message code="label.campaign.email.content"/>
    </h5>
</div>
<div class="widget-body">
    <div class="widget-main">
        <div id="email-content-div" class="widget-main-email-content">
            <c:out value="${campaignDTO.emailContent}"/>
        </div>
    </div>
</div>
</br>
</br>
<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button id="back_to_user_id_list" class="btn" onclick="backToRecipients(${campaignDTO.id})">
            <i class="icon-arrow-left icon-on-right"></i>
            <spring:message code="label.prev"/>
        </button>
        &nbsp; &nbsp; &nbsp;

        <button class="btn btn-success btn-next" type="submit" onclick="goToScheduleDeliveryDate(${campaignDTO.id})">
            <spring:message code="label.confirm"/>
            <i class="icon-arrow-right icon-on-right"></i>
        </button>

    </div>
</div>

<input type="hidden" id="email-content" value="${campaignDTO.emailContent}">

<script type="text/javascript">
    var content = $('input#email-content').val();
    $("#email-content-div").html(content);
</script>


<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">


    function goToScheduleDeliveryDate(campaignId) {
        window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementEmailDeliveryPage");
    }

    function backToRecipients(campaignId) {

        window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementRecipientsPage");
    }
</script>