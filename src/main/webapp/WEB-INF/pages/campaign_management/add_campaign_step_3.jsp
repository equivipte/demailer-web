<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="nothing_to_select" class="alert alert-info" style="display: none">
    <button type="button" class="close" data-dismiss="alert">
        <i class="icon-remove"></i>
    </button>
    <strong>
        <i class="icon-info"></i>
    </strong>


    <spring:message code="campaign.please.select.subscriber_group"/>
    <br/>
</div>

<div class="page-header">
    <h1>
        <spring:message code="label.campaign.email.recipients"/>
    </h1>
</div>


<c:set var="sendDate"><spring:message code="label.campaign.scheduled_send_date"/></c:set>

<jsp:include page="campaign_wizard_header_step3.jsp"/>
</br>
</br>

<div class="table-responsive">
<table id="table-subscriber" class="table table-striped table-bordered table-hover">
<thead>
<tr>
    <th class="center">
        <label class="pull-right">
            <input id="checkedAll" type="checkbox" class="ace"/>
            <span class="lbl"></span>
        </label>
    </th>
    <th><spring:message code="label.subscriber.subscriber_group"/></th>
    <th><spring:message code="label.subscriber.status"/></th>
</tr>
</thead>
<c:if test="${subscriberGroupDTOList.size() > 0 }">
    <tbody>
    <c:forEach items="${subscriberGroupDTOList}" var="subscriber">
        <tr id="${subscriber.id}">
            <td class="center">
                <label class="pull-right">
                    <c:if test="${subscriber.subscriberGroupStatus == 'ACTIVE'}">
                        <c:if test="${subscriber.addedToCampaign == true}">
                            <input type="checkbox" name="messageIds" value="${subscriber.id}" class="ace" checked="true"/>
                        </c:if>
                        <c:if test="${subscriber.addedToCampaign == false}">
                            <input type="checkbox" name="messageIds" value="${subscriber.id}" class="ace"/>
                        </c:if>
                        <span class="lbl"></span>
                    </c:if>

                </label>
            </td>
            <td>${subscriber.subscriberGroupName}</td>
            <td>
                <c:if test="${subscriber.subscriberGroupStatus == 'ACTIVE'}">
                    <span class="label label-sm label-success">Active</span>
                </c:if>
                <c:if test="${subscriber.subscriberGroupStatus == 'INACTIVE'}">
                    <span class="label label-sm label-grey">Inactive</span>
                </c:if>
                <c:if test="${subscriber.subscriberGroupStatus == 'DISABLED'}">
                    <span class="label label-sm label-danger">Disabled</span>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    </table>
    </div>
</c:if>

<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button id="back_to_user_id_list" class="btn" onclick="goToStep2(${campaignDTO.id})">
            <i class="icon-arrow-left icon-on-right"></i>
            <spring:message code="label.prev"/>
        </button>
        &nbsp; &nbsp; &nbsp;

        <button class="btn btn-success btn-next" onclick="goToStep4(${campaignDTO.id})">
            <spring:message code="label.next"/>
            <i class="icon-arrow-right icon-on-right"></i>
        </button>

    </div>
</div>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    function initCheckbox() {
        $('table th input:checkbox').on('click', function () {
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                    .each(function () {
                        this.checked = that.checked;
                        $(this).closest('tr').toggleClass('selected');
                    });

        });
    }
    function goToStep4(campaignId) {
        var selectValues = "";

        $('#table-subscriber').find('input[type="checkbox"]:checked').each(function () {
            var subscriberId = $(this).closest('tr').attr('id');

            if (subscriberId != null && subscriberId.length > 0) {
                selectValues = selectValues + subscriberId + ",";
            }
        });

        selectValues = selectValues.substring(0, selectValues.length - 1);

        clearAll();

        //size 0 eq ,
        if (selectValues.length > 0) {
            goToNextStep(campaignId,selectValues);
        }
        else {
            showNothingToSelect();
            return;
        }
    }

    function clearAll() {
        $("#nothing_to_select").css("display", "none");
    }

    function showNothingToSelect() {
        clearAll();
        $("#nothing_to_select").css("display", "block");
    }

    function goToNextStep(campaignId,selectValues) {
        var url = "${context}/main/merchant/campaign_management/" + campaignId + "/saveSubscriberGroup";

        $.ajax({
            url: url,
            type: "POST",
            data: selectValues,

            contentType: 'application/json',
            success: function () {
                window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementEmailDeliveryPage");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Save email content - the following error occured: " + textStatus, errorThrown);
            }
        });
    }

    function goToStep2(campaignId){
        window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementEmailContentPage");
    }

    $(document).ready(function () {
        initCheckbox();
    });

</script>