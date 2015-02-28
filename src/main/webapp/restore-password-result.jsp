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
<div>
    <s:property value="message"/>
</div>
<div style="padding-top: 5px;">
    <s:if test="!restoreResult">
        <s:url var="showRestorePasswordPageUrl" action="show-restore-password-page"/>
        <s:a href="%{showRestorePasswordPageUrl}"
             cssClass="button bg-color-blue fg-color-white">Попробовать ещё раз</s:a>
    </s:if>
    <a href="<s:url action='main'/>" class="button">Возвратиться на главную страницу</a>
</div>
