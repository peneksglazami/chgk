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
<html>
<head>
    <title>Что? Где? Когда? - Результаты турнира<s:if test="teamCategory != null"> - <s:property value="teamCategory.title"/></s:if></title>
</head>
<body>
<table width="590px" align="center">
    <tr>
        <td align="center" colspan="5" style="font-size: 20pt; font-weight: bold;">
            <s:property value="tournament.title"/>
        </td>
    </tr>
    <tr>
        <td align="center" colspan="5" style="font-size: 16pt; font-weight: bold;">
            <s:date name="tournament.date" format="dd.MM.yyyy"/>
        </td>
    </tr>
    <s:if test="teamCategory != null">
        <tr>
            <td align="center" colspan="5" style="font-size: 16pt; font-weight: bold;">
                <s:property value="teamCategory.title"/>
            </td>
        </tr>
    </s:if>
    <tr>
        <td height="50px" colspan="5"></td>
    </tr>
    <tr>
        <td align="center" width="350px" style="font-size: 14pt; font-weight: bold;" colspan="2">Команда</td>
        <td align="center" width="80px" style="font-size: 14pt; font-weight: bold;">Очки</td>
        <td align="center" width="80px" style="font-size: 14pt; font-weight: bold;">Рейтинг</td>
        <td align="center" width="80px" style="font-size: 14pt; font-weight: bold;">Место</td>
    </tr>
    <s:iterator var="teamResult" value="teamResults">
        <tr>
            <s:if test="teamCategory==null && tournament.teamCategories.size > 0">
                <td align="left" style="padding: 5px; font-size: 14pt;" width="20px">
                    ${teamResult.team.teamCategory.title}
                </td>
                <td align="left" style="padding: 5px; font-size: 14pt;" width="330px">${teamResult.team.name}</td>
            </s:if>
            <s:else>
                <td align="left" style="padding: 5px; font-size: 14pt;" width="350px" colspan="2">${teamResult.team.name}</td>
            </s:else>
            <td align="center" style="padding: 5px; font-size: 14pt;" width="80px">${teamResult.rightAnswers}</td>
            <td align="center" style="padding: 5px; font-size: 14pt;" width="80px">${teamResult.ranking}</td>
            <td align="center" style="padding: 5px; font-size: 14pt;" width="80px">${teamResult.rank}</td>
        </tr>
    </s:iterator>
</table>
</body>
</html>