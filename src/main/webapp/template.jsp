<%--
  Copyright 2012-2015 Andrey Grigorov, Anton Grigorov

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy of
  the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations under
  the License.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html lang="en">
<head>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon" />
    <sx:head compressed="true" debug="false" cache="true" parseContent="false"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/frameworks/jquery/themes/base/minified/jquery-ui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/frameworks/metro-ui/css/modern.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/frameworks/metro-ui/css/modern-responsive.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/frameworks/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/frameworks/jquery/jquery.ui.core.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/frameworks/jquery/jquery.ui.datepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/frameworks/jquery/jquery.ui.datepicker-ru.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/frameworks/jquery/jquery.form.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/frameworks/metro-ui/javascript/dialog.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/frameworks/metro-ui/javascript/dropdown.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/utils.js"></script>
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <shiro:notAuthenticated>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#loginAction').click(function () {
                    $.Dialog({
                        'title': 'Вход',
                        'content': '<form id="loginForm">' +
                                '<input type="hidden" name="action:login">' +
                                '<table>' +
                                '<tr><td>Логин:</td><td><input id="loginInput" name="login" type="text"></td></tr>' +
                                '<tr><td>Пароль:</td><td><input name="password" type="password"></td></tr>' +
                                '<tr><td colspan="2" style="text-align: right;"><a href="<s:url action="show-restore-password-page"/>">Забыли пароль?</a></td></tr>' +
                                '</table>' +
                                '<input name="currentPage" type="hidden" value="' + encodeURIComponent(document.URL) + '">' +
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

            $(document).ready(function () {
                $('#signUpAction').click(function () {
                    $.ExtDialog({
                        'title': 'Регистрация',
                        'content': '<form id="signUpForm">' +
                                '<input type="hidden" name="action:sign-up">' +
                                '<table>' +
                                '<tr><td>Логин:</td><td><input id="loginInput" name="login" type="text"></td></tr>' +
                                '<tr><td>e-mail:</td><td><input id="email" name="email" type="text"></td></tr>' +
                                '<tr><td>Пароль:</td><td><input name="password" type="password"></td></tr>' +
                                '<tr><td>Повторите пароль:</td><td><input name="password2" type="password"></td></tr>' +
                                '</table>' +
                                '<form>',
                        'overlay': true,
                        'buttonsAlign': 'right',
                        'buttons': {
                            'Да': {
                                'action': function () {
                                    $('#signUpForm').ajaxSubmit({
                                        type: 'post',
                                        success: function (response) {
                                            if (response.actionErrors != null) {
                                                var content = '<ul>';
                                                for (var i = 0; i < response.actionErrors.length; i++) {
                                                    content += '<li>' + response.actionErrors[i] + '</li>';
                                                }
                                                content += '</ul>';
                                                $("#dialogBox").find("div.error").html(content);
                                            } else {
                                                $.ExtDialog.hide();
                                                $.ExtDialog({
                                                    'title': 'Регистрация',
                                                    'content': response.actionMessages[0],
                                                    'overlay': true,
                                                    'buttonsAlign': 'right',
                                                    'buttons': {
                                                        'OK': {
                                                            'closeAfterAction': true
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            },
                            'Отмена': {
                                'closeAfterAction': true
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
        <shiro:guest>
            <div style="display: inline-block; float: right" class="nav-bar-inner padding10 fg-color-white">
                <div id="loginAction" class="fg-color-white" style="cursor: pointer">Войти</div>
            </div>
            <div style="display: inline-block; float: right" class="nav-bar-inner padding10 fg-color-white">
                <div id="signUpAction" class="fg-color-white" style="cursor: pointer">Регистрация</div>
            </div>
        </shiro:guest>
        <shiro:user>
            <div style="display: inline-block; float: right" class="nav-bar-inner padding10 fg-color-white">
                <shiro:principal/>
                <s:a action="logout" namespace="/" cssClass="fg-color-white">Выход</s:a>
            </div>
        </shiro:user>
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