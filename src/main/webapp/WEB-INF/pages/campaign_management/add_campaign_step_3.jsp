<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="page-header">
    <h1>
        <spring:message code="label.create.campaign"/>
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
        <tr>
            <td class="center">
                <label class="pull-right">
                    <input type="checkbox" name="messageIds" value="${subscriber.id}" class="ace"/>
                    <span class="lbl"></span>
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
    $(document).ready(function () {
        initCheckbox();
    });

</script>