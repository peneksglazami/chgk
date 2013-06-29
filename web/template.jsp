<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html lang="en">
<head>
    <sx:head compressed="true" debug="false" cache="true" parseContent="false"/>
    <link rel="stylesheet" href="frameworks/jquery/themes/base/minified/jquery-ui.min.css"/>
    <link rel="stylesheet" href="frameworks/metro-ui/css/modern.css">
    <link rel="stylesheet" href="frameworks/metro-ui/css/modern-responsive.css">
    <link rel="stylesheet" type="text/css" href="styles/styles.css"/>
    <script type="text/javascript" src="frameworks/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="frameworks/jquery/jquery.ui.core.min.js"></script>
    <script type="text/javascript" src="frameworks/jquery/jquery.ui.datepicker.min.js"></script>
    <script type="text/javascript" src="frameworks/jquery/jquery.ui.datepicker-ru.min.js"></script>
    <script type="text/javascript" src="frameworks/metro-ui/javascript/dialog.js"></script>
    <script type="text/javascript" src="frameworks/metro-ui/javascript/dropdown.js"></script>
    <script type="text/javascript" src="scripts/utils.js"></script>
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <shiro:notAuthenticated>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#loginAction').click(function (e) {
                    $.Dialog({
                        'title': 'Вход',
                        'content': '<form id="loginForm">' +
                                '<input type="hidden" name="action:login">' +
                                '<table>' +
                                '<tr><td>Логин:</td><td><input id="loginInput" name="login" type="text"></td></tr>' +
                                '<tr><td>Пароль:</td><td><input name="password" type="password"></td></tr>' +
                                '</table>' +
                                '<form>',
                        'overlay': true,
                        'buttonsAlign': 'right',
                        'buttons': {
                            'Да': {
                                'action': function () {
                                    $('#loginForm').submit();
                                }
                            },
                            'Отмена': {
                                'action': function () {
                                }
                            }
                        }
                    });
                    $("#loginInput").focus();
                });
            });
        </script>
    </shiro:notAuthenticated>
</head>
<body class="modern-ui">
<div class="page">
    <div class="nav-bar">
        <div style="display: inline-block;" class="nav-bar-inner padding10 fg-color-white">
            <b><tiles:getAsString name="title" ignore="true"/></b>
        </div>
        <div style="display: inline-block; float: right" class="nav-bar-inner padding10 fg-color-white">
            <shiro:notAuthenticated>
                <div id="loginAction" class="fg-color-white" style="cursor: pointer">Войти</div>
            </shiro:notAuthenticated>
            <shiro:authenticated>
                <shiro:principal/>
                <s:a action="logout" cssClass="fg-color-white">Выход</s:a>
            </shiro:authenticated>
        </div>
    </div>
</div>
<div class="page secondary">
    <div class="page-region padding10">
        <tiles:insertAttribute name="body"/>
    </div>
</div>
<div class="page-footer">
    <div class="page-footer-content">
        <div class="nav-bar">
            <div class="nav-bar-inner padding10">
                <span class="element">
                    <a class="fg-color-white" href="http://cher-chgk.3dn.ru" target="_blank">Череповецкий клуб
                        интеллектуальных игр</a>
                </span>
            </div>
        </div>
    </div>
</div>
</body>
</html>