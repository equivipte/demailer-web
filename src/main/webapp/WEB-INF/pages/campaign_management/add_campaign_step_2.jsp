<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="page-header">
    <h1>
        <spring:message code="label.create.campaign"/>
    </h1>
</div>

<jsp:include page="campaign_wizard_header_step2.jsp"/>
</br>
</br>

<div class="row-fluid">
    <div class="wysiwyg-editor" id="editor1"></div>
</div>
<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button id="back_to_user_id_list" class="btn" onclick="goToStep1(${campaignDTO.id})">
            <i class="icon-arrow-left icon-on-right"></i>
            <spring:message code="label.prev"/>
        </button>
        &nbsp; &nbsp; &nbsp;

        <button class="btn btn-success btn-next" onclick="goToStep3(${campaignDTO.id})">
            <spring:message code="label.next"/>
            <i class="icon-arrow-right icon-on-right"></i>
        </button>

    </div>
</div>

<input type="hidden" id="email-content" value="${campaignDTO.emailContent}">
<c:set var="context" value="${pageContext.request.contextPath}"/>

<script type="text/javascript">

    jQuery(function ($) {

        var content = $('input#email-content').val();
        $( "div.wysiwyg-editor" )
                .html(content);


        function showErrorAlert(reason, detail) {
            var msg = '';
            if (reason === 'unsupported-file-type') {
                msg = "Unsupported format " + detail;
            }
            else {
                console.log("error uploading file", reason, detail);
            }
            $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>File upload error</strong> ' + msg + ' </div>').prependTo('#alerts');
        }


        //but we want to change a few buttons colors for the third style
        $('#editor1').ace_wysiwyg({
            toolbar: [
                'font',
                null,
                'fontSize',
                null,
                {name: 'bold', className: 'btn-info'},
                {name: 'italic', className: 'btn-info'},
                {name: 'strikethrough', className: 'btn-info'},
                {name: 'underline', className: 'btn-info'},
                null,
                {name: 'insertunorderedlist', className: 'btn-success'},
                {name: 'insertorderedlist', className: 'btn-success'},
                {name: 'outdent', className: 'btn-purple'},
                {name: 'indent', className: 'btn-purple'},
                null,
                {name: 'justifyleft', className: 'btn-primary'},
                {name: 'justifycenter', className: 'btn-primary'},
                {name: 'justifyright', className: 'btn-primary'},
                {name: 'justifyfull', className: 'btn-inverse'},
                null,
                {name: 'createLink', className: 'btn-pink'},
                {name: 'unlink', className: 'btn-pink'},
                null,
                {name: 'insertImage', className: 'btn-success'},
                null,
                'foreColor',
                null,
                {name: 'undo', className: 'btn-grey'},
                {name: 'redo', className: 'btn-grey'}
            ],
            'wysiwyg': {
                fileUploadError: showErrorAlert
            }
        }).prev().addClass('wysiwyg-style2');


        $('#editor2').css({'height': '200px'}).ace_wysiwyg({
            toolbar_place: function (toolbar) {
                return $(this).closest('.widget-box').find('.widget-header').prepend(toolbar).children(0).addClass('inline');
            },
            toolbar: [
                'bold',
                {name: 'italic', title: 'Change Title!', icon: 'icon-leaf'},
                'strikethrough',
                null,
                'insertunorderedlist',
                'insertorderedlist',
                null,
                'justifyleft',
                'justifycenter',
                'justifyright'
            ],
            speech_button: false
        });


        $('[data-toggle="buttons"] .btn').on('click', function (e) {
            var target = $(this).find('input[type=radio]');
            var which = parseInt(target.val());
            var toolbar = $('#editor1').prev().get(0);
            if (which == 1 || which == 2 || which == 3) {
                toolbar.className = toolbar.className.replace(/wysiwyg\-style(1|2)/g, '');
                if (which == 1) $(toolbar).addClass('wysiwyg-style1');
                else if (which == 2) $(toolbar).addClass('wysiwyg-style2');
            }
        });


        //Add Image Resize Functionality to Chrome and Safari
        //webkit browsers don't have image resize functionality when content is editable
        //so let's add something using jQuery UI resizable
        //another option would be opening a dialog for user to enter dimensions.
        if (typeof jQuery.ui !== 'undefined' && /applewebkit/.test(navigator.userAgent.toLowerCase())) {

            var lastResizableImg = null;

            function destroyResizable() {
                if (lastResizableImg == null) return;
                lastResizableImg.resizable("destroy");
                lastResizableImg.removeData('resizable');
                lastResizableImg = null;
            }

            var enableImageResize = function () {
                $('.wysiwyg-editor')
                        .on('mousedown', function (e) {
                            var target = $(e.target);
                            if (e.target instanceof HTMLImageElement) {
                                if (!target.data('resizable')) {
                                    target.resizable({
                                        aspectRatio: e.target.width / e.target.height,
                                    });
                                    target.data('resizable', true);

                                    if (lastResizableImg != null) {//disable previous resizable image
                                        lastResizableImg.resizable("destroy");
                                        lastResizableImg.removeData('resizable');
                                    }
                                    lastResizableImg = target;
                                }
                            }
                        })
                        .on('click', function (e) {
                            if (lastResizableImg != null && !(e.target instanceof HTMLImageElement)) {
                                destroyResizable();
                            }
                        })
                        .on('keydown', function () {
                            destroyResizable();
                        });
            }

            enableImageResize();

            /**
             //or we can load the jQuery UI dynamically only if needed
             if (typeof jQuery.ui !== 'undefined') enableImageResize();
             else {//load jQuery UI if not loaded
			$.getScript($path_assets+"/js/jquery-ui-1.10.3.custom.min.js", function(data, textStatus, jqxhr) {
				if('ontouchend' in document) {//also load touch-punch for touch devices
					$.getScript($path_assets+"/js/jquery.ui.touch-punch.min.js", function(data, textStatus, jqxhr) {
						enableImageResize();
					});
				} else	enableImageResize();
			});
		}
             */
        }


    });

    function goToStep3(campaignId) {
        var emailContent = $('div#editor1').html();

        var url = "${context}/main/merchant/campaign_management/" + campaignId + "/saveCampaignEmailContent";

        $.ajax({
            url: url,
            type: "POST",
            data: emailContent,

            contentType: 'application/json',
            success: function () {
                window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementEmailDeliveryPage");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("Save email content - the following error occured: " + textStatus, errorThrown);
            }
        });
    }


    function goToStep1(campaignId) {
        window.location.replace("${context}/main/merchant/campaign_management/" + campaignId + "/campaignManagementAddPage");
    }


</script>

