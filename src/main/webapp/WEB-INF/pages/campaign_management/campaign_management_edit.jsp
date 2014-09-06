<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url var="url" value="/main/merchant/subscriber_management/saveUpdateSubscriberGroup"/>

<!-- #dialog-confirm -->
<form:form id="subscriberGroupUpdateForm"
           class="form-horizontal"
           commandName="subscriberGroupDTO"
           action="${url}"
           method="POST" cssClass="form-horizontal">
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-subscribe-group-name"><spring:message
                code="label.subscriber.subscriber_group"></spring:message></label>

        <div class="col-sm-9">
            <form:input
                    id="form-subscribe-group-name"
                    path="subscriberGroupName"
                    value="${subscriberGroupDTO.subscriberGroupName}"
                    placeholder="${subscriberGroupName}"
                    maxlength="50"
                    class="col-xs-10 col-sm-5"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-subscribe-status"><spring:message code="ACTIVE"/></label>

        <div class="col-sm-9">
            <c:if test="${subscriberGroupDTO.subscriberGroupStatus eq 'ACTIVE'}">
                <input id=subscribe-status" name="subscribe-status" checked="checked" type="checkbox" class="ace ace-switch ace-switch-5"/>
            </c:if>
            <c:if test="${subscriberGroupDTO.subscriberGroupStatus eq 'INACTIVE'}">
                <input id=subscribe-status" name="subscribe-status" type="checkbox" class="ace ace-switch ace-switch-5"/>
            </c:if>

            <span class="lbl middle"></span>
        </div>
    </div>
    </br>

    <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
            <button id="id-btn-save" class="btn btn-info" type="submit">
                <i class="icon-ok bigger-110"></i>
                <spring:message code="label.save"/>
            </button>

            &nbsp; &nbsp; &nbsp;
            <button id="back_to_subscriber_list" class="btn" onclick="backToSubcriberList()" type="reset">
                <i class="icon-undo bigger-110"></i>
                <spring:message code="label.subscriber.subscriber_group.subscriber_group_list"/>
            </button>
        </div>
    </div>

    <form:input type="hidden" path="id" name="id" value="${subscriberGroupDTO.id}"/>
</form:form>