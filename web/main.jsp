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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="chgk" uri="http://code.google.com/p/chgk/tags" %>
<c:if test="${(param.loginError eq 'FAILED') or (param.loginError eq 'LOCKED')}">
    <script type="text/javascript">
        $(document).ready(function () {
            $.Dialog({
                'title': 'Вход',
                'content': (window.location.href.indexOf('loginError=FAILED') >= 0) ?
                        'Введены неправильные логин или пароль.' : 'Учётная запись заблокирована.',
                'overlay': true,
                'buttonsAlign': 'right',
                'buttons': {
                    'OK': {
                        'action': function () {
                            var url = window.location.href.replace('loginError=FAILED', '')
                                    .replace('loginError=LOCKED', '');
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
<a id="tournament-list-link" href="<s:url action="tournament-list"/>">
    <div class="tile double bg-color-green">
        <div class="tile-content">
            <h4>Список турниров</h4>

            <p>Переходите к просмотру списка турниров</p>
        </div>
    </div>
</a>
<shiro:hasAnyRoles name="administrator,organizer">
    <a id="user-list-link" href="<s:url action="settings/user-list"/>">
        <div class="tile double bg-color-orange">
            <div class="tile-content">
                <h4>Список пользователей</h4>

                <p>Переходите к просмотру списка пользователей</p>
            </div>
        </div>
    </a>
</shiro:hasAnyRoles>
<shiro:hasAnyRoles name="administrator">
    <a id="settings-link" href="<s:url action="settings/settings"/>">
        <div class="tile double bg-color-orange">
            <div class="tile-content">
                <h4>Настройки приложения</h4>

                <p>Переходите к редактированию настроек приложения</p>
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

