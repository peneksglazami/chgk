<%--
  Copyright 2012-2019 Andrey Grigorov, Anton Grigorov

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
<s:if test="tournaments.size > 0">
    <table class="striped row-hovered">
        <thead>
        <tr style="background-color:#bbbbbb">
            <th>Название турнира</th>
            <th>Количество вопросов</th>
            <th>Количество туров</th>
            <th>Дата проведения</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator var="tournament" value="tournaments">
            <s:url var="url" action="tournament-info">
                <s:param name="tournamentId">
                    <s:property value="#tournament.id"/>
                </s:param>
            </s:url>
            <tr style="cursor: pointer;" onclick="javascript:document.location='<s:property value="%{url}"/>'">
                <td><s:property value="#tournament.title"/></td>
                <td><s:property value="#tournament.questionAmount"/></td>
                <td><s:property value="#tournament.roundAmount"/></td>
                <td><s:property value="#tournament.dateAsString"/></td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
</s:if>
<s:else>
    <div>Пока не создано ни одного турнира</div>
</s:else>
<p style="padding-top: 5px;">
    <shiro:hasPermission name="tournament:create">
        <a href="<s:url action='new-tournament'/>" class="button bg-color-green fg-color-white">Создать турнир</a>
    </shiro:hasPermission>
    <a href="<s:url action='main'/>" class="button">Возвратиться на главную страницу</a>
</p>