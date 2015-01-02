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
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:form theme="simple" id="restore-password">
    Укажите новый вароль для вашей учётной записи.
    <s:hidden name="tokenUUID"/>
    <table>
        <tr>
            <td colspan="2">
                <s:fielderror/>
            </td>
        </tr>
        <tr>
            <td align="right">Новый пароль</td>
            <td>
                <s:password name="password" size="50" maxLength="50" showPassword="true"/>
            </td>
        </tr>
        <tr>
            <td align="right">Повторите пароль</td>
            <td>
                <s:password name="password2" size="50" maxLength="50" showPassword="true"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <s:submit value="Сохранить" cssClass="bg-color-green fg-color-white" action="set-new-password"/>
                <a href="<s:url action='main'/>" class="button">Возвратиться на главную страницу</a>
            </td>
        </tr>
    </table>
</s:form>