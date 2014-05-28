<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="error-container">
    <div class="well">
        <h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="icon-random"></i>
												500
											</span>
            <spring:message code="label.error500.message.1"/>
        </h1>

        <hr/>
        <h3 class="lighter smaller">
            <spring:message code="label.error500.message.2"/>
            <i class="icon-wrench icon-animated-wrench bigger-125"></i>
            <spring:message code="label.error500.message.3"/>
        </h3>

        <div class="space"></div>

        <div>
            <h4 class="lighter smaller"><spring:message code="label.error500.message.4"/></h4>

            <ul class="list-unstyled spaced inline bigger-110 margin-15">
                <li>
                    <i class="icon-hand-right blue"></i>
                    <spring:message code="label.error500.message.5"/>
                </li>
                <li>
                    <i class="icon-hand-right blue"></i>
                    <spring:message code="label.error500.message.6"/>
                </li>
            </ul>
        </div>

        <hr/>
    </div>
</div>