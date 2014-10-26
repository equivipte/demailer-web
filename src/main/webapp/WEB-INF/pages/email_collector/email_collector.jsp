<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
		paceOptions = {  
		  // Configuration goes here. Example:  
		  elements: false,  
		  restartOnPushState: false,  
		  restartOnRequestAfter: false  
		};  	
</script>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<div class="page-header">
    <h1>
        <spring:message code="label.menu.email.collector"/>
    </h1>
</div>

<div id="dialog-message" class="hide">
    <p>
        Nothing to select
    </p>
</div>

<div id="progress" class="hide">
    <span id="crawlinginprogress"><span></span></span>
</div>

<div class="widget-main no-padding">
	<table>
	    <td>
	        <div>
	        	<spring:message code="label.please.type.site.search" var="siteSearch"/>
	        	<input id="url" path="site" size="100" maxlength="255" placeholder="${siteSearch}"/>
	        </div>
	    </td>
	    <td>
           <div id="crawlingsearch">
           		<button id="crawling" class="btn btn-sm btn-info">
	           		<i class="icon-search white bigger-120 crawlingsearch"></i>
	            	<spring:message code="label.search"/>
            	</button>
           </div>
           <div id="crawlingcancel" class="hide">
           		<button id="cancelcrawling" class="btn btn-sm btn-info">
           			<spring:message code="label.stop"/>
           		</button>
           </div>
	    </td>
	</table>
</div>
&nbsp;
&nbsp;
&nbsp;
<div id="table_result" class="table-responsive">
    <table id="emailTable" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th><spring:message code="label.common.emailaddress"/></th>
        </tr>
        </thead>
        
        <tbody>
        </tbody>
    </table>
</div>

</br>
</br>
</br>
<style>
    div#sb-container {
        z-index: 10000;
    }
</style>

<script type="text/javascript" src="<c:url value='/resources/js/crawlingpolling.js' /> "></script>
<link href="<c:url value="/resources/css/crawlingpolling.css" />" rel="stylesheet">

<script type="text/javascript">
	$(document).ready(function() {
		$('#crawling').click(function(){
		    var url = $("#url").val();

		    $("#url").prop('disabled', true);
		    $("#crawling").prop('disabled', true);

            $("#progress").removeClass("hide");
            $("#progress").addClass("show");
		    $('#crawlinginprogress span').text('Crawling in progress ...');

		    $.ajax({
                url : "${context}/main/emailcollector/passToPopup",
                type : "POST",
                data : url,
                contentType: 'application/json',
                success: function() {
                    window.open("${context}/main/emailcollector/popup", "Email Collector", "scrollbars=yes,height=640,width=860");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("Email collector pop up- the following error occured: " + textStatus, errorThrown);
                }
            });
        });
	});
</script>