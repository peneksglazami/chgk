<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="tournaments.size > 0">
    <table class="striped ">
        <thead>
        <tr style="background-color:#bbbbbb">
            <th>Название турнира</th>
            <th>Количество вопросов</th>
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
                <td><s:property value="#tournament.dateAsString"/></td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
</s:if>
<s:else>
    <div>Пока не создано ни одного турнира</div>
</s:else>
<p>
    <a href="<s:url action='new-tournament'/>" class="button bg-color-green fg-color-white">Создать турнир</a>
    <a href="<s:url action='main'/>" class="button">Возвратиться на главную страницу</a>
</p>