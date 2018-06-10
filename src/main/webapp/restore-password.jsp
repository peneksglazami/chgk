<%--
  Copyright 2012-2018 Andrey Grigorov, Anton Grigorov

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
    Для восстановления пароля укажите свой логин или адрес электронной почты, которые были указаны при регистрации.
    <div>
        <div style="padding-top: 10px;">Логин или e-mail <s:textfield name="loginOrEmail" size="50" maxlength="250"/></div>
        <div style="padding-top: 10px;">
            <s:submit id="restore-password-button" value="Восстановить пароль" cssClass="bg-color-green fg-color-white" action="restore-password"/>
            <a href="<s:url action='main'/>" class="button">Возвратиться на главную страницу</a>
        </div>
    </div>
</s:form>