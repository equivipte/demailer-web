<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<jsp:include page="../../general/error_message.jsp"/>

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
        ${campaignDTO.campaignName}
    </h1>
</div>

<div class="col-lg-12">
    <div class="tabbable">
        <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
            <c:if test="${mainScreen eq 'CAMPAIGN_STATISTIC'}">
                <li class="active">
                    <a data-toggle="tab" href="#campaignStatistic"><spring:message
                            code="label.campaign.statistic"/></a>
                </li>

                <li>
                    <a data-toggle="tab" href="#emailContent"><spring:message code="label.campaign.email.content"/></a>
                </li>
                <li>
                    <a data-toggle="tab" href="#subscriberGroupList"><spring:message
                            code="label.subscriber.subscriber_group"/></a>
                </li>
            </c:if>
            <c:if test="${mainScreen eq 'EMAIL_CONTENT'}">
                <li class="active">
                    <a data-toggle="tab" href="#emailContent"><spring:message
                            code="label.campaign.email.content"/></a>
                </li>

                <li>
                    <a data-toggle="tab" href="#campaignStatistic"><spring:message code="label.campaign.statistic"/></a>
                </li>
                <li>
                    <a data-toggle="tab" href="#subscriberGroupList"><spring:message
                            code="label.subscriber.subscriber_group"/></a>
                </li>
            </c:if>
            <c:if test="${mainScreen eq 'SUBSCRIBER_GROUP'}">
                <li class="active">
                    <a data-toggle="tab" href="#subscriberGroupList"><spring:message
                            code="label.subscriber.subscriber_group"/></a>
                </li>

                <li>
                    <a data-toggle="tab" href="#campaignStatistic"><spring:message code="label.campaign.statistic"/></a>
                </li>
                <li>
                    <a data-toggle="tab" href="#emailContent"><spring:message code="label.campaign.email.content"/></a>
                </li>
            </c:if>
        </ul>

        <div class="tab-content">
            <c:if test="${mainScreen eq 'CAMPAIGN_STATISTIC'}">
                <div id="campaignStatistic" class="tab-pane in active">
                    <jsp:include page="campaign_statistic.jsp"/>
                </div>
                <div id="emailContent" class="tab-pane">
                    <jsp:include page="email_content.jsp"/>
                </div>
                <div id="subscriberGroupList" class="tab-pane">
                    <jsp:include page="subscriber_group_list.jsp"/>
                </div>
            </c:if>
            <c:if test="${mainScreen eq 'EMAIL_CONTENT'}">
                <div id="campaignStatistic" class="tab-pane">
                    <jsp:include page="campaign_statistic.jsp"/>
                </div>
                <div id="emailContent" class="tab-pane in active">
                    <jsp:include page="email_content.jsp"/>
                </div>
                <div id="subscriberGroupList" class="tab-pane">
                    <jsp:include page="subscriber_group_list.jsp"/>
                </div>
            </c:if>
            <c:if test="${mainScreen eq 'SUBSCRIBER_GROUP'}">
                <div id="campaignStatistic" class="tab-pane">
                    <jsp:include page="campaign_statistic.jsp"/>
                </div>
                <div id="emailContent" class="tab-pane">
                    <jsp:include page="email_content.jsp"/>
                </div>
                <div id="subscriberGroupList" class="tab-pane in active">
                    <jsp:include page="subscriber_group_list.jsp"/>
                </div>
            </c:if>
        </div>
    </div>
</div>
<!-- /span -->

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    function backToCampaignList() {

        window.location.replace("${pageContext.request.contextPath}/main/merchant/campaign_management/1");
    }
</script>


