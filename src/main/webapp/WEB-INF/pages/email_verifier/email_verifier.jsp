<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="context" value="${pageContext.request.contextPath}"/>

<c:url var="url" value="/main/emailverifier/imports/upload"/>

<div class="page-header">
    <h1>
        <spring:message code="label.menu.email.verifier"/>
    </h1>
</div>

<div class="row">
    <div class="col-xs-12">
        <form method="POST" enctype="multipart/form-data" action="${url}">
            <div class="form-group">
                <div class="col-sm2">
                    <label class="col-sm-2 padding-right"><spring:message
                            code="label.import.email.file"></spring:message></label>
                </div>
                <div class="col-sm-3">
                    <input type="file" id="id-input-file-2" contenteditable="false" name="file" width="30px"/>
                    <button id="id-btn-upload" class="btn btn-xs btn-info no-padding-left" type="submit">
                        <spring:message code="label.import.upload"></spring:message>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<c:if test="${emailVerifierList.size() > 0 }">
    <div id="table_result" class="table-responsive">
        <table id="table-transaction" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th><spring:message code="label.common.emailaddress"/></th>
                <th><spring:message code="label.emailverifier.status.description"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${emailVerifierList}" var="email">
                <tr>
                    <td>${email.address}</td>
                    <td>
                        <c:if test="${email.status == 'UNAVAILABLE'}">
                            <span class="label label-sm label-inverse">${email.statusCode}</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pull-left">
        <a href="#">
            <button class="btn btn-info" id="export-btn">
                <i class="icon-mail-forward white bigger-120"></i>
                <spring:message code="label.common.export_results_to_excel"/>
            </button>
        </a>

        <a href="#">
            <button class="btn btn-info" id="import-btn">
                <i class="icon-book white bigger-120"></i>
                <spring:message code="label.emailverifier.import.to.contact"/>
            </button>
        </a>
    </div>
</c:if>
</br>
</br>
</br>
<style>
    div#sb-container {
        z-index: 10000;
    }
</style>

<script type="text/javascript" src="<c:url value='/resources/js/jquery-blink.js' />"></script>

<script type="text/javascript">
    jQuery(function ($) {
        $('#id-input-file-1 , #id-input-file-2').ace_file_input({
            no_file: 'No File ...',
            btn_choose: 'Choose',
            btn_change: 'Change',
            droppable: false,
            onchange: null,
            thumbnail: false //| true | large
            //whitelist:'gif|png|jpg|jpeg'
            //blacklist:'exe|php'
            //onchange:''
            //
        });

    });

    jQuery(window).load(function() {

        var exist = $('#table_result').length > 0;

        if(exist) {
            var table = $("#table-transaction > tbody");

            table.find('tr').each(function (i) {
                var $tds = $(this).find('td'),email = $tds.eq(0).text();

                $tds.eq(1).html('<span class="label label-sm label-arrowed">Verifying</span>');

                $($tds.eq(1).find('span')).blink({delay: 500});

                var url = "${context}/main/emailverifier";

                $.ajax({
                    url : url,
                    type : "POST",
                    data : "{\"Address\" : " + "\"" + email + "\"}",

                    contentType: 'application/json',
                    success: function(verifier) {
                        var status = verifier.Status;
                        var statusCode = verifier.StatusCode;

                        if('Valid' === status) {
                            $tds.eq(1).html('<span class="label label-sm label-success">' + statusCode + '</span>');
                        } else if('Invalid' === status) {
                            $tds.eq(1).html('<span class="label label-sm label-danger">' + statusCode + '</span>');
                        } else if('Unknown' === status) {
                            $tds.eq(1).html('<span class="label label-sm label-grey">' + statusCode + '</span>');
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Email verifier - the following error occured: " + textStatus, errorThrown);

                        $tds.eq(1).html('<span class="label label-sm label-inverse">Not Available</span>');
                    }
                });
            });
        }
    });
</script>