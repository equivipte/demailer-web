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

<input type="hidden" id="emailTemplatesSize" value="${emailTemplates.size()}">

<div id="emailTemplatesDiv" class="row">
    <c:forEach items="${emailTemplates}" var="template">
        <div class="" page="">
            <div class="thumbnail">
                <img origsrc="${context}/main/streaming/loadImage?dirName=${template.dirName}&imageName=${template.imageName}"
                     alt="" class="templateImg">
            </div>
            <div class="caption blue center">
                <h5>
                    <a href="${context}/main/streaming/loadHTML?dirName=${template.dirName}&htmlName=${template.htmlName}"
                       target="_blank"><spring:message code="label.email.template.download.view"/></a> |
                    <a href="${context}/main/streaming/downloadFile?dirName=${template.dirName}&fileName=${template.htmlName}"><spring:message
                            code="label.email.template.download.download"/></a>
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
    var numOfItemsInPage = 8;
    var totalPages = 0;
    var cssItem = "col-xs-6 col-md-3";

    $(document).ready(function () {
        disableImageCache();
        setPageAndCssAttribute();
        handlePagination();
    });

    function disableImageCache() {
        $(".templateImg", "#emailTemplatesDiv").each(function () {
            var imgSrc = $(this).attr("origsrc") + '&' + (new Date().getTime());
            $(this).attr("src", imgSrc);
            $(this).removeAttr("origsrc");
        });
    }

    function setPageAndCssAttribute() {
        $("[page]", "#emailTemplatesDiv").each(function (index, value) {
            var page = Math.ceil((index + 1) / numOfItemsInPage);

            $(this).attr("page", page);
            $(this).attr("class", cssItem + " " + "show");

            if (page > 1) {
                $(this).attr("class", cssItem + " " + "hide");
            }
        });
    }

    function handlePagination() {
        var emailTemplatesSize = parseInt($("#emailTemplatesSize").val());

        var tmpPagination = emailTemplatesSize / numOfItemsInPage;

        if (tmpPagination > 1) {
            totalPages = Math.ceil(tmpPagination);
        }

        var visiblePages = 10;

        if (emailTemplatesSize > visiblePages) {
            visiblePages = emailTemplatesSize;
        }

        $('#template-pagination').twbsPagination({
            totalPages: totalPages,
            visiblePages: visiblePages,
            onPageClick: function (event, page) {
                movePage(page);
            }
        });
    }

    function movePage(page) {
        for (var i = 1; i <= totalPages; i++) {
            var item = $("#emailTemplatesDiv").find("[page='" + i + "']");

            if (i == page) {
                item.each(function () {
                    $(this).removeClass("hide");
                    $(this).addClass("show");
                });
            } else {
                item.each(function () {
                    $(this).removeClass("show");
                    $(this).addClass("hide");
                });
            }
        }
    }
</script>