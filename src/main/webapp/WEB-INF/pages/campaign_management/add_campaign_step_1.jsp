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

<c:set var="campaignName"><spring:message code="label.campaign.name"/></c:set>
<c:set var="subject"><spring:message code="label.campaign.subject"/></c:set>
<c:set var="emailFrom"><spring:message code="label.campaign.email.from"/></c:set>


<div class="page-header">
    <h1>
        <spring:message code="label.campaign.info"/>
    </h1>
</div>

<jsp:include page="campaign_wizard_header_step1.jsp"/>
</br>
</br>

<!-- /.page-header -->
<div class="row">
    <div class="col-xs-12">

        <c:url var="url" value="/main/merchant/campaign_management/saveAddCampaign"/>

        <!-- #dialog-confirm -->
        <form:form id="campaignAddForm"
                   class="form-horizontal"
                   commandName="campaignDTO"
                   action="${url}"
                   method="POST" cssClass="form-horizontal">
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-campaign-name"><spring:message
                        code="label.campaign.name"></spring:message></label>

                <div class="col-sm-9">
                    <form:input
                            id="form-campaign-name"
                            path="campaignName"
                            value="${campaignDTO.campaignName}"
                            placeholder="${campaignName}"
                            maxlength="50"
                            class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-campaign-name"><spring:message
                        code="label.campaign.email.from"></spring:message></label>

                <div class="col-sm-9">
                    <form:input
                            id="form-email-from"
                            path="emailFrom"
                            value="${campaignDTO.emailFrom}"
                            placeholder="${emailFrom}"
                            maxlength="50"
                            class="col-xs-10 col-sm-5"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right" for="form-email-subject"><spring:message
                        code="label.campaign.subject"></spring:message></label>

                <div class="col-sm-9">
                    <form:input
                            id="form-email-subject"
                            path="emailSubject"
                            value="${campaignDTO.emailSubject}"
                            placeholder="${subject}"
                            maxlength="50"
                            class="col-xs-10 col-sm-5"/>
                </div>
            </div>

            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button id="back_to_user_id_list" class="btn" onclick="backToCampaignList()" type="reset">
                        <i class="icon-undo bigger-110"></i>
                        <spring:message code="label.cancel"/>
                    </button>
                    &nbsp; &nbsp; &nbsp;

                    <button class="btn btn-success btn-next" type="submit">
                        <spring:message code="label.next"/>
                        <i class="icon-arrow-right icon-on-right"></i>
                    </button>

                </div>
            </div>
            <form:input type="hidden" path="id" name="id" value="${campaignDTO.id}"/>
        </form:form>
    </div>
</div>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    function backToCampaignList() {

        window.location.replace("${pageContext.request.contextPath}/main/merchant/campaign_management/1");
    }
</script>


