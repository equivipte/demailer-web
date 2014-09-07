<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/demailer.ico"/>

    <title><tiles:insertAttribute name="title" ignore="true"/></title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">

    <link href="${pageContext.request.contextPath}/resources/css/shadowbox.css" rel="stylesheet" type="text/css"/>

    <link href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>

    <link href="${pageContext.request.contextPath}/resources/css/jquery-ui-1.10.3.full.min.css" rel="stylesheet"
          type="text/css"/>

    <link href="${pageContext.request.contextPath}/resources/css/multi-select.css" rel="stylesheet" type="text/css"/>

    <!--[if IE 7]>
    <link href="${pageContext.request.contextPath}/resources/css/font-awesome-ie7.min.css" rel="stylesheet"
          type="text/css"/>
    <![endif]-->

    <!-- page specific plugin styles -->

    <!-- fonts -->

    <link href="${pageContext.request.contextPath}/resources/css/ace-fonts.css" rel="stylesheet" type="text/css"/>

    <!-- ace styles -->

    <link href="${pageContext.request.contextPath}/resources/css/ace.min.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/css/ace-rtl.min.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/css/ace-skins.min.css" rel="stylesheet" type="text/css"/>

    <!--[if lte IE 8]>
    <link href="${pageContext.request.contextPath}/resources/css/ace-ie.min.css" rel="stylesheet" type="text/css"/>
    <![endif]-->

    <link href="${pageContext.request.contextPath}/resources/css/chosen.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-timepicker.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/datepicker.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/daterangepicker.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/colorpicker.css" rel="stylesheet"/>


</head>

<body>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='${pageContext.request.contextPath}/resources/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
</script>
<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js'>" + "<" + "/script>");
</script>
<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='${pageContext.request.contextPath}/resources/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.maskedinput.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/typeahead-bs2.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootbox.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/fuelux/fuelux.wizard.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/markdown/markdown.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/markdown/bootstrap-markdown.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.hotkeys.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-wysiwyg.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/shadowbox.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.ui.touch-punch.min.js"></script>

<![if !IE]>
<script src="${pageContext.request.contextPath}/resources/js/jquery.session.js"></script>
<![endif]>

<script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.bootstrap.js"></script>


<script src="${pageContext.request.contextPath}/resources/js/ace-elements.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ace.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/ace-extra.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/select2.min.js"></script>


<script src="${pageContext.request.contextPath}/resources/js/chosen.jquery.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/date-time/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/date-time/bootstrap-timepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/date-time/daterangepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/date-time/bootstrap-datetimepicker.min.js"></script>

<!-- inline styles related to this page -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
<![endif]-->

<!-- Modified JS-->
<script src="${pageContext.request.contextPath}/resources/js/sigcap-web.js"></script>

<tiles:insertAttribute name="header"/>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <tiles:insertAttribute name="menu"/>
        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="${pageContext.request.contextPath}/main"><spring:message code="label.home"/></a>
                    </li>
                    <tiles:insertAttribute name="breadcrumb"/>
                </ul>
                <!-- .breadcrumb -->
            </div>
            <div class="page-content">

                <tiles:insertAttribute name="body"/>
            </div>
        </div>
    </div>
</div>
<style>
    .spinner-preview {
        width: 100px;
        height: 100px;
        text-align: center;
        margin-top: 60px;
    }

    .dropdown-preview {
        margin: 0 5px;
        display: inline-block;
    }

    .dropdown-preview > .dropdown-menu {
        display: block;
        position: static;
        margin-bottom: 5px;
    }
</style>


</body>
</html>

