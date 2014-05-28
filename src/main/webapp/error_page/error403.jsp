<!DOCTYPE html>
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

    <title>dEmailer</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">

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


    <script src="${pageContext.request.contextPath}/resources/js/ace-extra.min.js"></script>

</head>
<body>

<div class="page-content">
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="error-container">
                <div class="well">
                    <h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="icon-minus-sign"></i>
												403
											</span>
                        Access Denied
                    </h1>

                    <hr/>
                    <h3 class="lighter smaller">Insufficient User Privilege</h3>

                    <hr/>
                </div>
            </div>
            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->
</div>
<!-- /.page-content -->
</body>
