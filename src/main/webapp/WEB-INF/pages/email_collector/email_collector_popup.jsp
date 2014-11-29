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
    <div class="spinner">
      <div class="rect1"></div>
      <div class="rect2"></div>
      <div class="rect3"></div>
      <div class="rect4"></div>
      <div class="rect5"></div>
    </div>

    <span id="scanning"><span></span></span>
    <span id="terminating"><span></span></span>
</div>

<div class="widget-main no-padding">
	<table>
	    <td>
	        <div>
	        	<spring:message code="label.please.type.site.search" var="siteSearch"/>
	        	<input id="url" path="site" size="100" maxlength="255" value="${siteUrl}" placeholder="${siteSearch}"/>
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

<div id="buttons" class="pull-left">
    <a href="#">
        <button class="btn btn-info" id="export-btn">
            <i class="icon-mail-forward white bigger-120"></i>
            <spring:message code="label.common.export_results_to_excel"/>
        </button>
    </a>

    <a href="#">
        <button class="btn btn-info" id="verify-emails-btn">
            <i class="icon-ok white bigger-120"></i>
            <spring:message code="label.common.verify_email_list"/>
        </button>
    </a>
</div>
</br>
</br>
</br>

<div id="quota" class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">Quota Information</h3>
      </div>
      <div class="panel-body">
        You have <span class="label label-info">${quota.emailVerifyQuota}</span> quota for email verification. You already used <span class="label label-danger">${quota.currentEmailsVerified}</span> emails.
      </div>
</div>

<style>
    div#sb-container {
        z-index: 10000;
    }
</style>

<script type="text/javascript" src="<c:url value='/resources/js/crawlingpolling.js' /> "></script>
<link href="<c:url value="/resources/css/crawlingpolling.css" />" rel="stylesheet">

<script type="text/javascript">

	$(document).ready(function() {
        var poll = new Poll();
        poll.showHideButtons();
		runCrawling();

        $('#crawling').click(function(){
            $("#terminating").removeClass("show");
            $("#terminating").addClass("hide");

            $("#scanning").removeClass("hide");
            $("#scanning").addClass("show");

            $('#scanning span').text('');

            runCrawling();
        });

		$("#cancelcrawling").click(function() {
            cancelcrawling();
		});

		$("#verify-emails-btn").click(function() {
		    var emails = getEmailsInTable();

            $.ajax({
                url : "${context}/main/emailcollector/putResultToSession",
                type : "POST",
                data : JSON.stringify(emails),
                contentType: 'application/json',
                success: function() {
                    window.opener.verifyEmail();
                    window.close();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("Email verifier - the following error occured: " + textStatus, errorThrown);
                }
            });
		});

		$("#export-btn").click(function() {
            var emails = getEmailsInTable();

            $.ajax({
                url : "${context}/main/emailcollector/putResultToSession",
                type : "POST",
                data : JSON.stringify(emails),
                contentType: 'application/json',
                success: function() {
                    window.location.href = "${context}/main/emailcollector/exportToExcel";
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("Export to excel - the following error occured: " + textStatus, errorThrown);
                }
            });
		});

		$(window).bind('beforeunload', function(){
		    cancelcrawling();

            $.ajax({
                url : "${context}/main/emailcollector/terminatePopupSession",
                type : "GET",
                success: function() {
                    console.log("Crawling session is terminated");
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("Crawling session termination - the following error occured: " + textStatus, errorThrown);
                }
            });
        });
	});

	var poll = new Poll();

	function runCrawling() {
	    $("#progress").removeClass("hide");
        $("#progress").addClass("show");
        $("#url").prop('disabled', true);
        $("#crawlingsearch").toggleClass("hide");
        $("#crawlingcancel").toggleClass("hide");

        $("#cancelcrawling").removeAttr('disabled');

        $("#emailTable").find("tr:gt(0)").remove();

        var url = $("#url").val();

        var startUrl = "${context}/main/emailcollector/async/begin";
        var pollUrl = "${context}/main/emailcollector/async/update";
        var scanningUrl = "${context}/main/emailcollector/async/updateUrlScanning";
        var crawlingStatusUrl = "${context}/main/emailcollector/updateCrawlingStatus";
        poll.start(startUrl,pollUrl, url, scanningUrl, crawlingStatusUrl);
	}

	function cancelcrawling() {
        var cancelUrl = "${context}/main/emailcollector/cancelCrawling";

        $("#scanning").removeClass("show");
        $("#scanning").addClass("hide");

        $("#terminating").removeClass("hide");
        $("#terminating").addClass("show");

        $("#cancelcrawling").attr('disabled', 'disabled');

        $('#terminating span').text('Terminating site crawlers...Please wait..');

        poll.cancelCrawling(cancelUrl);
	}

	function getEmailsInTable() {
        var table = $("#emailTable > tbody");

        var emails = [];

        table.find('tr').each(function (i) {
            var $tds = $(this).find('td'),email = $tds.eq(0).text();

            emails.push(email);

        });

        return emails;
	}
</script>