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
        <th><spring:message code="label.subscriber.subscriber_date"/></th>
        <th><spring:message code="label.action"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${subscriberDTOList}" var="subscriber">
        <tr>
            <td>${subscriber.emailAddress}</td>
            <c:if test="${subscriber.subscriberStatus == 'SUBSCRIBED'}">
                <span class="label label-sm label-success">Subscribed</span>
            </c:if>
            <c:if test="${subscriber.subscriberStatus == 'UNSUBSCRIBED'}">
                <span class="label label-sm label-grey">Unsubscribed</span>
            </c:if>
            <td>
                <button id="btnEdit" class="btn btn-xs btn-edit btnEdit" type="button"><i
                        class="icon-edit bigger-120"></i></button>
                <button id="btnDeleteTrash" class="btn btn-xs btn-danger btnDelete" type="button"><i
                        class="icon-trash bigger-120"></i></button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>