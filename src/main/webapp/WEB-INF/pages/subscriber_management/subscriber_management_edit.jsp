<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


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
        <spring:message code="label.subscriber.subscriber_group.edit"/> : ${subscriberGroupDTO.subscriberGroupName}
    </h1>
</div>

<div class="col-lg-12">
    <div class="tabbable">
        <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
            <li class="active">
                <a data-toggle="tab" href="#subscriberGroup"><spring:message
                        code="label.subscriber.subscriber_group"/></a>
            </li>

            <li>
                <a data-toggle="tab" href="#subscriberList"><spring:message code="label.subscriber.list"/></a>
            </li>
        </ul>

        <div class="tab-content">
            <div id="subscriberGroup" class="tab-pane in active">
                <jsp:include page="subscriber_group_edit_form.jsp"/>
            </div>

            <div id="subscriberList" class="tab-pane">
                <jsp:include page="subscriber_list.jsp"/>
            </div>
        </div>
    </div>
</div>
<!-- /span -->

<jsp:include page="../subscriber_management/add_subscriber.jsp"/>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    function backToSubcriberList() {

        window.location.replace("${pageContext.request.contextPath}/main/subscriber_management/1");
    }
</script>


