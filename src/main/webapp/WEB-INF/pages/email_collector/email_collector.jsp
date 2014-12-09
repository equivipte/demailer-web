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

<div id="info-keyword" class="alert alert-info">
    <strong>
        <spring:message code="label.full.url.info"/>
    </strong>
</div>

<div id="progresspanel" class="hide">
    <span id="crawlinginprogress"><span></span></span>
</div>

<div class="widget-main no-padding">
	<table>
	    <td>
	        <div>
	        	<spring:message code="label.please.type.site.search" var="siteSearch"/>
	        	<input id="url" path="site" size="100" maxlength="255" placeholder="${siteSearch}"/>
	        	<input id="progressstatus" type="hidden" value="${inProgress}"/>
	        </div>
	    </td>
	    <td>
           <div id="searchcrawling">
           		<button id="crawlingbtn" class="btn btn-sm btn-info">
	           		<i class="icon-search white bigger-120 crawlingsearch"></i>
	            	<spring:message code="label.search"/>
            	</button>
           </div>
	    </td>
	</table>
</div>
<style>
    div#sb-container {
        z-index: 10000;
    }
</style>

<link href="<c:url value="/resources/css/crawlingpolling.css" />" rel="stylesheet">

<script type="text/javascript">
    var allow = true;
    var timer = null;

    function verifyEmail() {
        window.location.href = "${context}/main/merchant/emailverifier/verify";
    }

	$(document).ready(function() {
	    checkStatus();

		$('#crawlingbtn').click(function(){
            inProgressMode();

            var url = $("#url").val();

		    var request = $.ajax({
                url : "${context}/main/emailcollector/passToPopup",
                type : "POST",
                data : url,
                contentType: 'application/json'
            });

            request.done(function() {
                window.open("${context}/main/emailcollector/popup", "Email Collector", "scrollbars=yes,height=640,width=860");

                setInterval(function() {
                        if(allow === true) {
                            allow = false;
                            updatePopupSessionStatus();
                        }
                }, 500);

                $("#url").val('');
            });

            request.fail(function(jqXHR, textStatus, errorThrown) {
                console.log("Polling - the following error occured: " + textStatus, errorThrown);
            });

            request.always(function() {
                updatePopupSessionStatus();
            });

        });

        function updatePopupSessionStatus() {
            var popUpStatusUrl = "${context}/main/emailcollector/updatePopupSessionStatus";

            var requestComplete = $.ajax({
                url : popUpStatusUrl,
                type : "get",
            });

            requestComplete.done(function(message) {
                var inprogress = message;

                if(inprogress == 'true') {
                     allow = true;

                     inProgressMode();

                 } else {
                     allow = false;

                     nonProgressMode();
                     clearInterval(timer);
                 }
            });

            requestComplete.fail(function(jqXHR, textStatus, errorThrown) {
                console.log("Polling - the following error occured: " + textStatus, errorThrown);
            });
        }

        function checkStatus() {
            var progressstatus = $("#progressstatus").val();

            if(progressstatus === 'true') {
                inProgressMode();

                var fCheckStatus = function() {
                    updatePopupSessionStatus();
                };
                timer = setInterval(fCheckStatus, 1000);
            } else {
                nonProgressMode();
                clearInterval(timer);
            }
        }

        function inProgressMode() {
            $("#url").prop('disabled', true);
            $("#crawlingbtn").prop('disabled', true);

            $("#progresspanel").removeClass("hide");
            $("#progresspanel").addClass("show");

            $('#crawlinginprogress span').text('Crawling session has already started');
        }

        function nonProgressMode() {
            $("#url").prop('disabled', false);
            $("#url").val('');

            $("#progresspanel").removeClass("show");
            $("#progresspanel").addClass("hide");

            $("#crawlingbtn").removeAttr('disabled');
            $('#crawlinginprogress span').text('');
        }

	});
</script>