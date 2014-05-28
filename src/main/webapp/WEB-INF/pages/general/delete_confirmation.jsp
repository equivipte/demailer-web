<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="dialog-confirm" class="hide">
    <div class="alert alert-info bigger-110">
        <spring:message code="label.save.confirm.delete.declaration_message"/>
    </div>

    <div class="space-6"></div>

    <p class="bigger-110 bolder center grey">
        <i class="icon-hand-right blue bigger-120"></i>
        <spring:message code="label.save.confirm"/>
    </p>
</div>