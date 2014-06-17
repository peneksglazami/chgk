<%--
  Copyright 2012-2014 Andrey Grigorov, Anton Grigorov

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
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<shiro:hasPermission name="user:edit:${user.id}">
    <script type="text/javascript">
        $(document).ready(function () {
            $('#deleteButton').click(function (e) {
                $.Dialog({
                    'title': 'Удаление пользователя',
                    'content': 'Вы точно хотите удалить пользователя? Вся информация о пользователе будет полностью удалена.',
                    'overlay': true,
                    'buttonsAlign': 'right',
                    'buttons': {
                        'Да': {
                            'action': function () {
                                $('<input />').attr('type', 'hidden')
                                        .attr('name', 'action:delete-user')
                                        .attr('value', 'Удалить')
                                        .appendTo('#edit-user');
                                document.getElementById("edit-user").submit();
                            }
                        },
                        'Нет': {
                            'action': function () {
                            }
                        }
                    }
                });
            });
        });
    </script>
</shiro:hasPermission>
<s:form theme="simple" id="edit-user">
    <s:hidden name="user.id"/>
    <table>
        <tr>
            <td colspan="2">
                <s:fielderror/>
            </td>
        </tr>
        <tr>
            <td align="right">Логин</td>
            <td>
                <s:if test="user == null || user.id == null">
                    <s:textfield name="user.username" size="50" maxlength="20"/>
                </s:if>
                <s:else>
                    <s:label name="user.username"/>
                </s:else>
            </td>
        </tr>
        <shiro:hasPermission name="user:edit:${user.id}">
            <tr>
                <td align="right">Пароль</td>
                <td><s:password name="user.password" size="50" maxlength="50" showPassword="true"/></td>
            </tr>
        </shiro:hasPermission>
        <shiro:hasRole name="administrator">
            <tr>
                <td align="right">Роль</td>
                <td><s:select name="role" list="roles" value="currentRoleName"/></td>
            </tr>
        </shiro:hasRole>
        <tr>
            <td colspan="2" align="right">
                <s:if test="user == null || user.id == null">
                    <shiro:hasPermission name="user:create">
                        <s:submit value="Создать" cssClass="bg-color-green fg-color-white" action="save-user"/>
                    </shiro:hasPermission>
                </s:if>
                <s:else>
                    <shiro:hasPermission name="user:edit:${user.id}">
                        <s:submit value="Сохранить" cssClass="bg-color-green fg-color-white" action="save-user"/>
                        <s:if test="user.username != currentUserName">
                            <input id="deleteButton" type="button" class="button bg-color-red fg-color-white"
                                   value="Удалить"/>
                        </s:if>
                    </shiro:hasPermission>
                </s:else>
                <input class="button" type="button" value="Назад" onclick="history.back()">
            </td>
        </tr>
    </table>
</s:form>