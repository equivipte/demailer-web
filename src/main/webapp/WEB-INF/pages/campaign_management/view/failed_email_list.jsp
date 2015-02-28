<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<div class="table-failed">
    <table id="table-transaction-failed" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th><spring:message code="label.campaign.email.failure_email"/></th>
        </tr>
        </thead>
        <c:if test="${failEmailRecipientList.size() > 0 }">
            <tbody>
            <c:forEach items="${failEmailRecipientList}" var="failEmailRecipient">
                <tr id="${failEmailRecipient}">
                    <td>${failEmailRecipient}</td>
                </tr>
            </c:forEach>
            </tbody>
        </c:if>
    </table>
    <c:if test="${failEmailRecipientList.size() > 0 }">
        <a href="#">
            <button class="btn btn-info" id="export-failed-btn">
                <i class="icon-mail-forward white bigger-120"></i>
                <spring:message code="label.common.export_results_to_excel"/>
            </button>
        </a>
    </c:if>
</div>

<script type="text/javascript">
    jQuery(window).load(function () {
        $("#export-failed-btn").click(function () {

            var exist = $('#table-transaction-failed').length > 0;

            if (exist) {
                var emails = [];

                var table = $("#table-transaction-failed > tbody");

                table.find('tr').each(function (i) {
                    var $tds = $(this).find('td'), email = $tds.eq(0).text();
                    emails.push(email);
                });

                if (emails.length != 0) {
                    var url_result_session = "${context}/main/merchant/opened_email/putResultToSession";
                    var url_export_excel = "${context}/main/merchant/opened_email/exportToExcel";
                    $.ajax({
                        url: url_result_session,
                        type: "POST",
                        data: JSON.stringify(emails),
                        contentType: 'application/json',
                        success: function () {
                            window.location.href = url_export_excel;
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log("Export to excel - the following error occured: " + textStatus, errorThrown);
                        }
                    });
                }
            }
        });
    });
</script>
