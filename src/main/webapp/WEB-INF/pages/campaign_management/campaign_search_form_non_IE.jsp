<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="campaignSubjectName"><spring:message code="label.campaign.subject"/></c:set>

<form action="${context}/main/campaign_management/1">
    <table>
        <td>
            <a href="#" class="add-campaign-button" title="<spring:message code="label.create.campaign"/>">
                <button class="btn btn-info" type="button">
                    <i class="icon-plus-sign white bigger-120"></i>
                    <spring:message code="label.create"/>
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
                       name="campaignSubjectName"
                       path="campaignSubjectName"
                       size="30"
                       maxlength="30"
                       value="${fn:escapeXml(param.campaignSubjectName)}"
                       placeholder="${campaignSubjectName}"
                       class="col-lg-12"/>
            </div>
        </td>
    </table>
</form>
<script type="text/javascript">
    $(".add-campaign-button").click(function (event) {
        window.location.replace("${context}/main/campaign_management/create");
    });
</script>
