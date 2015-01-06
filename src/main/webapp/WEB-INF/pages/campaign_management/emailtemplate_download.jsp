<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<!-- Email templates download -->
<div>
    <h3 class="header smaller lighter blue">
        <span>
            <i class="icon-cloud-download"></i>
            <spring:message code="label.email.template.download.header"/>
        </span>
    </h3>
</div>

<div id="info-keyword" class="alert alert-info">
    <spring:message code="label.email.template.download.info"/>
</div>

<div id="emailTemplatesDiv" class="row">
    <c:forEach items="${emailTemplates}" var="template">
        <div class="col-xs-6 col-md-3">
            <div class="thumbnail">
              <img origsrc="${context}/main/streaming/loadImage?dirName=${template.dirName}&imageName=${template.imageName}" alt="" class="templateImg">
            </div>
            <div class="caption blue center">
                <h5>
                    <a href="${context}/main/streaming/loadHTML?dirName=${template.dirName}&htmlName=${template.htmlName}" target="_blank"><spring:message code="label.email.template.download.view"/></a> |
                    <a href="${context}/main/streaming/downloadFile?dirName=${template.dirName}&fileName=${template.htmlName}"><spring:message code="label.email.template.download.download"/></a>
                </h5>
            </div>
        </div>
    </c:forEach>
</div>

<div class="text-center">
    <nav>
        <ul id="template-pagination" class="pagination-sm pagination"></ul>
    </nav>
</div>

<script type="text/javascript" src="<c:url value='/resources/js/jquery.twbsPagination.min.js' /> "></script>

<script type="text/javascript">
    $(document).ready(function() {
        disableImageCache();
        handlePagination();
    });

    function disableImageCache() {
        $(".templateImg", "#emailTemplatesDiv").each(function() {
            var imgSrc = $(this).attr("origsrc") + '&' + (new Date().getTime());
            $(this).attr("src", imgSrc);
            $(this).removeAttr("origsrc");
        });
    }

    function handlePagination() {
        $('#template-pagination').twbsPagination({
            totalPages: 5,
            visiblePages: 3,
            onPageClick: function (event, page) {

            }
        });
    }
</script>