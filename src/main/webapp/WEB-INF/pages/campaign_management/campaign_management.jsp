<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!-- #dialog-confirm -->
<jsp:include page="../general/delete_confirmation.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<div class="page-header">
    <h1>
        <spring:message code="label.menu.campaign"/>
    </h1>
</div>


<!-- /.page-header -->
<jsp:include page="campaign_search_form_non_IE.jsp"/>

</br>
</br>
<div class="table-responsive">
<table id="table-subscriber" class="table table-striped table-bordered table-hover">
<thead>
<tr>
    <th><spring:message code="label.campaign.name"/></th>
    <th><spring:message code="label.campaign.subject"/></th>
    <th><spring:message code="label.campaign.status"/></th>
    <th><spring:message code="label.last_updated"/></th>
    <th><spring:message code="label.action"/></th>
</tr>
</thead>
<c:if test="${campaignDTOList.size() > 0 }">
    <tbody>
    <c:forEach items="${campaignDTOList}" var="campaign">
        <tr>
            <td>${campaign.campaignName}</td>
            <td>${campaign.emailSubject}</td>
            <td>
                <c:if test="${campaign.campaignStatus == 'SEND'}">
                    <span class="label label-sm label-success"><spring:message code="SEND"/></span>
                </c:if>
                <c:if test="${campaign.campaignStatus == 'DRAFT'}">
                    <span class="label label-sm label-warning"><spring:message code="DRAFT"/></span>
                </c:if>
            </td>
            <td>${campaign.lastUpdateDate}</td>
            <c:if test="${campaign.campaignStatus == 'DRAFT'}">
                <td>
                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                        <a id="${campaignDTO.id}" href="#" class="tooltip-success green editCampaign"
                           data-rel="tooltip"
                           title="<spring:message code="label.campaign.edit"/>">
                            <i class="icon-pencil bigger-130"></i>
                        </a>
                        <a id="${campaignDTO.id}" href="#" class="tooltip-error red deleteCampaign"
                           data-rel="tooltip"
                           class="deleteCampaign"
                           title="<spring:message code="label.campaign.delete"/>">
                            <i class="icon-trash bigger-130"></i>
                        </a>
                    </div>
                    <div class="visible-xs visible-sm hidden-md hidden-lg">
                        <div class="inline position-relative">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
                                <i class="icon-cog icon-only bigger-110"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
                                <li>
                                    <a id="${campaignDTO.id}" href="#" class="tooltip-success editCampaign"
                                       data-rel="tooltip"
                                       title="<spring:message code="label.campaign.edit"/>">
                                            <span class="green">
                                                <i class="icon-edit bigger-120"></i>
                                            </span>
                                    </a>
                                </li>

                                <li>
                                    <a id="${subscriber.id}" href="#" class="tooltip-error deleteCampaign"
                                       data-rel="tooltip"
                                       class="deleteCampaign"
                                       title="<spring:message code="label.campaign.delete"/>">
                                            <span class="red">
                                                <i class="icon-trash bigger-120"></i>
                                            </span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </c:if>
            <c:if test="${campaign.campaignStatus == 'SEND'}">
                <td>
                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                        <a id="${campaignDTO.id}" href="#" class="tooltip-success blue viewCampaign"
                           data-rel="tooltip"
                           title="<spring:message code="label.campaign.view"/>">
                            <i class="icon-zoom-in bigger-130"></i>
                        </a>
                    </div>
                    <div class="visible-xs visible-sm hidden-md hidden-lg">
                        <div class="inline position-relative">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
                                <i class="icon-cog icon-only bigger-110"></i>
                            </button>
                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
                                <li>
                                    <a id="${campaignDTO.id}" href="#" class="tooltip-success viewCampaign"
                                       data-rel="tooltip"
                                       title="<spring:message code="label.campaign.view"/>">
                                            <span class="green">
                                                <i class="icon-zoom-in bigger-120"></i>
                                            </span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
    </table>
    </div>

    <c:set var="paginationParameter"
           value="campaignSubject=${param.subscriberGroupName}"/>

    <c:url var="firstUrl" value="/main/merchant/campaign_management/1?${paginationParameter}"/>
    <c:url var="lastUrl" value="/main/merchant/campaign_management/${deploymentLog.totalPages}?${paginationParameter}"/>
    <c:url var="prevUrl" value="/main/merchant/campaign_management/${currentIndex - 1}?${paginationParameter}"/>
    <c:url var="nextUrl" value="/main/merchant/campaign_management/${currentIndex + 1}?${paginationParameter}"/>


    <ul class="pagination pull-right no-margin">
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><a href="#"><i class="icon-double-angle-left"></i></a></li>
                <li class="disabled"><a href="#"><i class="icon-angle-left"></i></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${firstUrl}"><i class="icon-double-angle-left"></i></a></li>
                <li><a href="${prevUrl}"><i class="icon-angle-left"></i></a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="/main/merchant/campaign_management/${i}?${paginationParameter}"/>
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex == deploymentLog.totalPages}">
                <li class="disabled"><a href="#"><i class="icon-angle-right"></i></a></li>
                <li class="disabled"><a href="#"><i class="icon-double-angle-right"></i></a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${nextUrl}"><i class="icon-angle-right"></i></a></li>
                <li><a href="${lastUrl}"><i class="icon-double-angle-right"></i></a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</c:if>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    $(document).ready(function () {
        $(".editCampaign").click(function (event) {
            var campaignId = $(this).attr("id");
            redirectToEdit(campaignId)
        });

        $(".deleteCampaign").click(function (event) {
            var campaignId = $(this).attr("id");
            deleteCampaign(campaignId)
        });

    });

    function redirectToEdit(id) {
        window.location.replace("${context}/main/merchant/campaign_management/campaign_list/" + id + "/1?nextPage=CAMPAIGN_GROUP");
    }

    function deleteCampaign(id) {
        var href = "${context}/main/merchant/campaign_management/1";
        $("#dialog-confirm").removeClass('hide').dialog({
            resizable: false,
            modal: true,
            title: "<div class='widget-header'><h4 class='smaller'><i class='icon-warning-sign red'></i><spring:message code="label.delete"/></h4></div>",
            title_html: true,
            buttons: [
                {
                    html: "<i class='icon-trash bigger-110'></i>&nbsp; <spring:message code="label.campaign.delete"/> ",
                    "class": "btn btn-danger btn-xs",
                    click: function () {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/main/merchant/campaign_management/" + id,
                            type: "DELETE",
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                alert("An error has occurred making the request: " + errorThrown);

                            },
                            success: function (data) {
                                if (data != 'SUCCESS') {
                                    alert("<spring:message code="general.exception.delete"/>");
                                }
                                else {
                                    //Do stuff here on success such as modal info
                                    $("#dialog-confirm").dialog("close");
                                    window.location.replace(href);
                                }

                            }
                        });
                    }
                }
                ,
                {
                    html: "<i class='icon-remove bigger-110'></i>&nbsp; <spring:message code="label.cancel"/> ",
                    "class": "btn btn-xs",
                    click: function () {
                        $(this).dialog("close");
                    }
                }
            ]
        });
    }


</script>