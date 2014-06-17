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
<table class="striped row-hovered">
    <thead>
    <tr style="background-color:#bbbbbb">
        <th>Логин</th>
    </tr>
    </thead>
    <tbody>
    <s:iterator var="user" value="users">
        <s:url var="url" action="edit-user">
            <s:param name="userId">
                <s:property value="#user.id"/>
            </s:param>
        </s:url>
        <tr style="cursor: pointer;" onclick="javascript:document.location='<s:property value="%{url}"/>'">
            <td><s:property value="#user.username"/></td>
        </tr>
    </s:iterator>
    </tbody>
</table>
<p>
    <shiro:hasPermission name="user:create">
        <a href="<s:url action='new-user'/>" class="button bg-color-green fg-color-white">Создать пользователя</a>
    </shiro:hasPermission>
    <a href="<s:url action='main' namespace="/"/>" class="button">Возвратиться на главную страницу</a>
</p>
