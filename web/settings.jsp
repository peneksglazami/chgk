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
<s:form theme="simple" id="edit-settings">
    Настройки связи с почтовым сервером
    <table>
        <tr>
            <td align="right">Название хоста</td>
            <td>
                <s:textfield name="mailServerHostName" size="50" maxlength="250"/>
            </td>
        </tr>
        <tr>
            <td align="right">Порт</td>
            <td>
                <s:textfield name="mailServerPort" size="50" maxlength="5"/>
            </td>
        </tr>
        <tr>
            <td align="right">Имя пользователя</td>
            <td>
                <s:textfield name="mailServerUser" size="50" maxlength="250"/>
            </td>
        </tr>
        <tr>
            <td align="right">Пароль</td>
            <td><s:password name="mailServerPassword" size="50" maxlength="250" showPassword="true"/></td>
        </tr>
    </table>
    Общие настройки
    <table>
        <tr>
            <td align="right">Название хоста, на котором запущено приложение</td>
            <td>
                <s:textfield name="hostName" size="50" maxlength="250"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <s:submit value="Сохранить" cssClass="bg-color-green fg-color-white" action="save-settings"/>
                <input class="button" type="button" value="Назад" onclick="history.back()">
            </td>
        </tr>
    </table>

</s:form>
