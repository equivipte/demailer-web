<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<div id="modal-form-subscriber" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="blue bigger"><spring:message code="label.subscriber.add"/></h4>
            </div>
            <div id="error-block" class="alert alert-danger" style="display: none">
                <button type="button" class="close" data-dismiss="alert">
                    <i class="icon-remove"></i>
                </button>

                <strong>
                    <i class="icon-remove"></i>
                    <label id="error-message"></label>
                </strong>
                <br/>
            </div>

            <div class="modal-body overflow-visible">
                <div class="row">
                    <div class="col-xs-12 col-sm-7">
                        <div class="form-group">
                            <input multiple="" type="file" id="id-input-file-3" />
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button class="btn btn-sm" data-dismiss="modal">
                    <i class="icon-remove"></i>
                    <spring:message code="label.cancel"/>
                </button>

                <button class="btn btn-sm btn-primary" onclick="changePassword()" type="button">
                    <i class="icon-ok"></i>
                    <spring:message code="label.save"/>
                </button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function () {
        $("#success_message").css("display", "none");
        $('#id-input-file-3').ace_file_input({
            style:'well',
            btn_choose:'Drop files here or click to choose',
            btn_change:null,
            no_icon:'icon-cloud-upload',
            droppable:true,
            thumbnail:'small'//large | fit
            //,icon_remove:null//set null, to hide remove/reset button
            /**,before_change:function(files, dropped) {
						//Check an example below
						//or examples/file-upload.html
						return true;
					}*/
            /**,before_remove : function() {
						return true;
					}*/
            ,
            preview_error : function(filename, error_code) {
                //name of the file that failed
                //error_code values
                //1 = 'FILE_LOAD_FAILED',
                //2 = 'IMAGE_LOAD_FAILED',
                //3 = 'THUMBNAIL_FAILED'
                //alert(error_code);
            }

        }).on('change', function(){
            //console.log($(this).data('ace_input_files'));
            //console.log($(this).data('ace_input_method'));
        });
    });
</script>