<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="chgk" uri="http://code.google.com/p/chgk/tags" %>
<c:if test="${param.loginResult eq 'FAILED'}">
    <script type="text/javascript">
        $(document).ready(function () {
            $.Dialog({
                'title': 'Вход',
                'content': 'Введены неправильные логин или пароль.',
                'overlay': true,
                'buttonsAlign': 'right',
                'buttons': {
                    'OK': {
                        'action': function () {
                            var url = window.location.href.replace('loginResult=FAILED', '');
                            if (url.indexOf('?') == url.length - 1) {
                                url = url.substr(0, url.length - 1);
                            }
                            window.location.replace(url);
                        }
                    }
                }
            });
        });
    </script>
</c:if>
<a href="<s:url action="tournament-list"/>">
    <div class="tile double bg-color-green">
        <div class="tile-content">
            <h4>Список турниров</h4>

            <p>Переходите к просмотру списка турниров</p>
        </div>
    </div>
</a>
<shiro:hasAnyRoles name="administrator,organizer">
    <a href="<s:url action="settings/user-list"/>">
        <div class="tile double bg-color-orange">
            <div class="tile-content">
                <h4>Список пользователей</h4>

                <p>Переходите к просмотру списка пользователей</p>
            </div>
        </div>
    </a>
</shiro:hasAnyRoles>
<chgk:isDemoMode>
    <div class="demo-users">
        <h4>Демо-пользователи</h4>
        <b>Администратор</b>: admin - admin<br/>
        <b>Организатор</b>: organizer - organizer
    </div>
</chgk:isDemoMode>

