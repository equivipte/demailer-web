<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

</br>
<table>
    <td>
        <a href="#modal-form-subscriber" class="add-subscribe-button" data-toggle="modal">
            <button class="btn btn-info" type="button">
                <i class="icon-plus-sign white bigger-120"></i>
                <spring:message code="label.add"/>
            </button>
        </a>
    </td>
    <td>
        <a href="#">
            <button class="btn btn-info">
                <i class="icon-search white bigger-120"></i>
                <spring:message code="label.search"/>
            </button>
        </a>
    </td>
    <td>

        <div>
            <input type="text"
                   name="emailAddress"
                   size="50"
                   maxlength="50"
                   placeholder="<spring:message code="label.common.emailaddress"/>"
                   class="col-lg-12"/>
        </div>
    </td>
</table>

</br>
</br>

<table id="table-subscriber" name="table-user" class="table table-striped table-bordered table-hover"
       width="500px">
    <thead>
    <tr>
        <th><spring:message code="label.subscriber.email_address"/></th>
        <th><spring:message code="label.subscriber.status"/></th>
        <th><spring:message code="label.action"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${subscriberDTOList}" var="subscriber">
        <tr>
            <td>${subscriber.emailAddress}</td>
            <td>
                <c:if test="${subscriber.subscribeStatus == 'SUBSCRIBED'}">
                    <span class="label label-sm label-success">Subscribed</span>
                </c:if>
                <c:if test="${subscriber.subscribeStatus == 'UNSUBSCRIBED'}">
                    <span class="label label-sm label-danger">Unsubscribed</span>
                </c:if>
            </td>
            <td>
                <button id="btnDeleteTrash" class="btn btn-xs btn-danger btnDelete" type="button"><i
                        class="icon-trash bigger-120"></i></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button id="back_to_subscriber_list" class="btn" onclick="backToSubcriberList()" type="reset">
            <i class="icon-undo bigger-110"></i>
            <spring:message code="label.subscriber.subscriber_group.subscriber_group_list"/>
        </button>
    </div>
</div>