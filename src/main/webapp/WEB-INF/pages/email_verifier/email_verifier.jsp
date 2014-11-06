<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="context" value="${pageContext.request.contextPath}"/>

<c:url var="url" value="/main/merchant/emailverifier/imports/upload"/>

<div class="page-header">
    <h1>
        <spring:message code="label.menu.email.verifier"/>
    </h1>
</div>

<div class="row">
    <div class="col-xs-12">
        <form method="POST" enctype="multipart/form-data" action="${url}">
            <div class="form-group">
                <div class="col-sm-5" align="right">
                    <input type="file" id="id-input-file-2" contenteditable="false" name="file" width="30px"/>
                    <button id="id-btn-upload" class="btn btn-xs btn-info no-padding-left" type="submit">
                        <spring:message code="label.import.upload"></spring:message>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
</br>
</br>
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
                    <td>${email.emailAddress}</td>
                    <td>
                        <c:if test="${email.status == 'UNAVAILABLE'}">
                            <span class="label label-sm label-inverse">${email.infoDetails}</span>
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
    $(document).ready(function () {
        $("#success_message").css("display", "none");
        $('#id-input-file-2').ace_file_input({
            style: 'well',
            btn_choose: 'Drop your excel file here or click to choose',
            btn_change: null,
            no_icon: 'icon-cloud-upload',
            droppable: true,
            thumbnail: 'large'//large | fit
            //,icon_remove:null//set null, to hide remove/reset button
            /**,before_change:function(files, dropped) {
						//Check an example below
						//or examples/file-upload.html
						return true;
					}*/
            /**,before_remove : function() {
						return true;
					}*/,
            preview_error: function (filename, error_code) {
                //name of the file that failed
                //error_code values
                //1 = 'FILE_LOAD_FAILED',
                //2 = 'IMAGE_LOAD_FAILED',
                //3 = 'THUMBNAIL_FAILED'
                //alert(error_code);
            }

        }).on('change', function () {
            //console.log($(this).data('ace_input_files'));
            //console.log($(this).data('ace_input_method'));
        });
    });

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

                var url = "${context}/main/merchant/emailverifier";

                $.ajax({
                    url : url,
                    type : "POST",
                    data : "{\"emailAddress\" : " + "\"" + email + "\"}",

                    contentType: 'application/json',
                    success: function(verifier) {
                        var status = verifier.status;

                        if('Valid' === status) {
                            $tds.eq(1).html('<span class="label label-sm label-success">' + status + '</span>');
                        } else if('Invalid' === status) {
                            $tds.eq(1).html('<span class="label label-sm label-danger">' + status + '</span>');
                        } else if('Unknown' === status) {
                            $tds.eq(1).html('<span class="label label-sm label-grey">' + status + '</span>');
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Email verifier - the following error occured: " + textStatus, errorThrown);

                        $tds.eq(1).html('<span class="label label-sm label-inverse">Not Available</span>');
                    }
                });
            });
        }

        $("#export-btn").click(function() {
            var exist = $('#table_result').length > 0;

            if(exist) {
                var emails = [];

                var table = $("#table-transaction > tbody");

                table.find('tr').each(function (i) {
                    var $tds = $(this).find('td'),email = $tds.eq(0).text(), status = $tds.eq(1).text();

                    if('Valid' === status) {
                        emails.push(email);
                    }
                });

                if(emails.length != 0) {
                    $.ajax({
                        url : "${context}/main/emailcollector/putResultToSession",
                        type : "POST",
                        data : JSON.stringify(emails),
                        contentType: 'application/json',
                        success: function() {
                            window.location.href = "${context}/main/merchant/emailverifier/exportToExcel";
                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            console.log("Export to excel - the following error occured: " + textStatus, errorThrown);
                        }
                    });
                }
            }
        });
    });
</script>