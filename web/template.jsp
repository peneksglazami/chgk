<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<html lang="en">
<head>
    <sx:head compressed="true" debug="false" cache="true" parseContent="false"/>
    <link rel="stylesheet" type="text/css" href="frameworks/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="frameworks/bootstrap/css/bootstrap-responsive.min.css"/>
    <link rel="stylesheet" type="text/css" href="styles/styles.css"/>
    <script type="text/javascript" src="frameworks/jquery/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="frameworks/bootstrap/js/bootstrap.js"></script>
    <title><tiles:getAsString name="title" ignore="true"/></title>
</head>
<body>
<table class="main-table">
    <tr class="main-table-header">
        <td><tiles:getAsString name="title" ignore="true"/></td>
    </tr>
    <tr class="main-table-body">
        <td><tiles:insertAttribute name="body"/></td>
    </tr>
    <tr class="main-table-footer">
        <td><a href="http://cher-chgk.3dn.ru" target="_blank">Череповецкий клуб интеллектуальных игр</a></td>
    </tr>
</table>
</body>
</html>