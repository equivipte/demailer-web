<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<div class="page-header">
    <h1>
        <spring:message code="label.campaign.email.content"/>
    </h1>
</div>

<jsp:include page="campaign_wizard_header_step2.jsp"/>

<!-- Email templates -->
<div>
    <h3 class="header smaller lighter blue">
        <span>
            <i class="icon-cloud-download"></i>
            Email Templates Download
        </span>
    </h3>
</div>

<div id="info-keyword" class="alert alert-info">
    Following are samples of email templates which you can download and use on your own.
</div>

<div class="row">
    <c:forEach items="${emailTemplates}" var="template">
        <div class="col-xs-6 col-md-3">
            <div class="thumbnail">
              <img src="${context}/main/streaming/loadImage?dirName=${template.dirName}&imageName=${template.imageName}&${template.dateTime}" alt="">
            </div>
            <div class="caption blue center">
                <h5>
                    <a href="${context}/main/streaming/loadHTML?dirName=${template.dirName}&htmlName=${template.htmlName}" target="_blank">View</a> |
                    <a href="#">Download</a>
                </h5>
            </div>
        </div>
    </c:forEach>
</div>

</br>

<!-- Upload email templates -->
<div>
    <h3 class="header smaller lighter blue">
        <span>
            <i class="icon-cloud-upload"></i>
            Email Template Upload
        </span>
    </h3>
</div>

<div id="info-keyword" class="alert alert-info">
    Upload your email template and see preview on the next step.
</div>


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

