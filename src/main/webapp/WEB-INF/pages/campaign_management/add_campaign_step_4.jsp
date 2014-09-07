<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="page-header">
    <h1>
        <spring:message code="label.create.campaign"/>
    </h1>
</div>


<c:set var="sendDate"><spring:message code="label.campaign.scheduled_send_date"/></c:set>

<jsp:include page="campaign_wizard_header_step4.jsp"/>
</br>
</br>

<!-- /.page-header -->
<div class="row">
    <div class="col-xs-12">

        <c:url var="url" value="/main/merchant/campaign_management/saveCampaignDelivery"/>

        <!-- #dialog-confirm -->
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">
                <spring:message code="label.campaign.scheduled_send_date"></spring:message></label>

            <div class="input-group col-sm-2">
                <div class="input-group">
                    <input class="form-control date-picker" id="id-date-picker-1" type="text"
                           data-date-format="dd-mm-yyyy"/>
                    <span class="input-group-addon">
                        <i class="icon-calendar bigger-110"></i>
                    </span>

                </div>
            </div>
            <div class="input-group col-sm-2">
                <div class="input-group  bootstrap-timepicker">
                    <input id="timepicker1" type="text" class="form-control"/>
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
                <button id="back_to_user_id_list" class="btn" onclick="backToEmailContent(${campaignDTO.id})">
                    <i class="icon-arrow-left icon-on-right"></i>
                    <spring:message code="label.prev"/>
                </button>
                &nbsp; &nbsp; &nbsp;

                <button class="btn btn-success btn-next" type="submit">
                    <spring:message code="label.next"/>
                    <i class="icon-arrow-right icon-on-right"></i>
                </button>

            </div>
        </div>
    </div>
</div>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

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
    function backToEmailContent(campaignId) {

        window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementEmailContentPage");
    }
</script>
