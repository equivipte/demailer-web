<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="widget-header widget-header-flat widget-header-small">
    <h5>
        <spring:message code="label.campaign.email.content"/>
    </h5>
</div>
<div class="widget-body">
    <div class="widget-main">
        <div id="email-content-div" class="widget-main-email-content">
            <c:out value="${campaignDTO.emailContent}"/>
        </div>
    </div>
</div>

<input type="hidden" id="email-content" value="${campaignDTO.emailContent}">

<script type="text/javascript">
    var content = $('input#email-content').val();
    $("#email-content-div").html(content);
</script>