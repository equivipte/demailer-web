<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="page-header">
    <h1>
        <spring:message code="label.campaign.email.content"/>
    </h1>
</div>

<jsp:include page="campaign_wizard_header_step2.jsp"/>
</br>
</br>

<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button id="back_to_user_id_list" class="btn" onclick="goToStep1(${campaignDTO.id})">
            <i class="icon-arrow-left icon-on-right"></i>
            <spring:message code="label.prev"/>
        </button>
        &nbsp; &nbsp; &nbsp;

        <button class="btn btn-success btn-next" onclick="goToStep3(${campaignDTO.id})">
            <spring:message code="label.next"/>
            <i class="icon-arrow-right icon-on-right"></i>
        </button>

    </div>
</div>

<input type="hidden" id="email-content" value="${campaignDTO.emailContent}">
<c:set var="context" value="${pageContext.request.contextPath}"/>

<script type="text/javascript">

    function goToStep3(campaignId) {
        var emailContent = $('div#editor1').html();

        var url = "${context}/main/merchant/campaign_management/" + campaignId + "/saveCampaignEmailContent";

        $.ajax({
            url: url,
            type: "POST",
            data: emailContent,

            contentType: 'application/json',
            success: function () {
                window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementRecipientsPage");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Save email content - the following error occured: " + textStatus, errorThrown);
            }
        });
    }


    function goToStep1(campaignId) {
        window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementAddPage");
    }


</script>

