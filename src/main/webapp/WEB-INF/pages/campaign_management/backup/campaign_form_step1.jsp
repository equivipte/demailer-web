<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="step-pane active" id="step1">
    <h3 class="lighter block green">Enter the following information</h3>

    <form class="form-horizontal" id="sample-form">
        <div class="form-group has-info">
            <label for="campaignName" class="col-xs-12 col-sm-3 control-label no-padding-right">Campaign Name</label>

            <div class="col-xs-12 col-sm-5">
                <input type="text" id="campaignName"
                       class="width-100"/>
            </div>
        </div>

        <div class="form-group has-info">
            <label for="emailSubject" class="col-xs-12 col-sm-3 control-label no-padding-right">Email Subject</label>

            <div class="col-xs-12 col-sm-5">
                <input type="text" id="emailSubject"
                       class="width-100"/>
            </div>
        </div>
    </form>
</div>