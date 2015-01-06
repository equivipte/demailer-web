<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="page-header">
    <h1>
        <spring:message code="label.campaign.scheduled_send_date"/>
    </h1>
</div>


<c:set var="sendDate"><spring:message code="label.campaign.scheduled_send_date"/></c:set>

<jsp:include page="email_quota.jsp"/>

<jsp:include page="campaign_wizard_header_step5.jsp"/>

</br>
</br>
</br>

<!-- /.page-header -->
<div class="row">
    <div class="col-xs-12">

        <!-- #dialog-confirm -->
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">
                <spring:message code="label.campaign.scheduled_send_date"></spring:message></label>

            <div class="input-group col-sm-2">
                <div class="input-group">
                    <input class="form-control date-picker scheduled-date" id="id-date-picker-1" type="text"
                           data-date-format="dd-mm-yyyy" value="${campaignDTO.scheduledSendDate}"/>
                    <span class="input-group-addon">
                        <i class="icon-calendar bigger-110"></i>
                    </span>

                </div>
            </div>
            <div class="input-group col-sm-2">
                <div class="input-group  bootstrap-timepicker">
                    <input id="timepicker1" type="text" class="form-control scheduled-time" value="${campaignDTO.scheduledSendTime}"/>
                    <span class="input-group-addon">
                        <i class="icon-time bigger-110"></i>
                    </span>
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

                <button id="btn-send-email" class="btn btn-success btn-next" type="submit"
                        onclick="sendEmail(${campaignDTO.id})">
                    <spring:message code="label.campaign.send"/>
                    <i class="icon-inbox icon-on-right"></i>
                </button>

            </div>
        </div>
    </div>
</div>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    jQuery(window).load(function () {
        // quota info
        var emailSendingQuota = parseInt($("#emailSendingQuota").val());
        var emailSendingQuotaUsed = parseInt($("#emailSendingQuotaUsed").val());

        if (emailSendingQuotaUsed >= emailSendingQuota) {
            $("#btn-send-email").attr('disabled', 'disabled');
        }
    });

    jQuery(function ($) {
        $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
        $('#timepicker1').timepicker({
            minuteStep: 1,
            showSeconds: false,
            showMeridian: false
        }).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    });

    function sendEmail(campaignId) {
        // quota info
        var emailSendingQuota = parseInt($("#emailSendingQuota").val());
        var emailSendingQuotaUsed = parseInt($("#emailSendingQuotaUsed").val());

        if (emailSendingQuotaUsed < emailSendingQuota) {
            var url = "${context}/main/merchant/campaign_management/" + campaignId + "/saveCampaignDelivery";

            var scheduledDate = $('#id-date-picker-1').val();
            var scheduledTime = $('#timepicker1').val();

            $.ajax({
                url: url,
                type: "POST",
                data: scheduledDate + " " + scheduledTime,

                contentType: 'application/json',
                success: function () {
                    window.location.replace("${context}/main/merchant/campaign_management/goToFinishPage");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("Save email content - the following error occured: " + textStatus, errorThrown);
                }
            });
        }

    }

    function backToRecipients(campaignId) {

        window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementEmailReviewPage");
    }
</script>
