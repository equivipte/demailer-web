<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Upload email templates -->
<div>
    <h3 class="header smaller lighter blue">
        <span>
            <i class="icon-cloud-upload"></i>
            <spring:message code="label.email.template.upload.header"/>
        </span>
    </h3>
</div>

<div id="info-keyword" class="alert alert-info">
    <spring:message code="label.email.template.upload.info"/>
</div>