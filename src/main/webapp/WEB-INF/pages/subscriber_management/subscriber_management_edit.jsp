<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<jsp:include page="../general/error_message.jsp"/>

<script type="text/css">
    table {
        table-layout: fixed;
        word-wrap: break-word;
    }
</script>

<div id="error-block-message" class="alert alert-danger" style="display: none">
    <button type="button" class="close" data-dismiss="alert">
        <i class="icon-remove"></i>
    </button>

    <strong>
        <i class="icon-remove"></i>
        <label id="error-message-subscribe-error"></label>
    </strong>
    <br/>
</div>

<c:set var="subscriberGroupName"><spring:message code="label.subscriber.subscriber_group.name"/></c:set>

<div class="page-header">
    <h1>
        <spring:message code="label.subscriber.subscriber_group.edit"/>
    </h1>
</div>

<!-- /.page-header -->
<div class="row">
    <div class="col-xs-12">

        <c:url var="url" value="/main/subscriber_management/saveUpdateSubscriberGroup"/>

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
                <label class="col-sm-3 control-label no-padding-right" for="form-subscribe-status">Active</label>

                <div class="col-sm-9">
                    <input id=form-subscribe-status" checked="" type="checkbox" class="ace ace-switch ace-switch-5" />
                    <span class="lbl middle"></span>
                </div>
            </div>

            </br>

            <h3 class="header smaller lighter blue"><spring:message code="label.subscriber.list"/></h3>

            <table>
                <td>
                    <a href="#" class="add-subscribe-button">
                        <button class="btn btn-info" type="button">
                            <i class="icon-plus-sign white bigger-120"></i>
                            <spring:message code="label.subscriber.add"/>
                        </button>
                    </a>
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
        </form:form>
    </div>
</div>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    function backToSubcriberList() {

        window.location.replace("${pageContext.request.contextPath}/main/subscriber_management/1");
    }
</script>


