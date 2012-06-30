<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="tournaments.size > 0">
    <table class="list-table table table-bordered">
        <thead>
        <tr>
            <th>Название турнира</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator var="tournament" value="tournaments">
            <tr>
                <s:url var="url" action="tournament-info">
                    <s:param name="tournamentId">${tournament.id}</s:param>
                </s:url>
                <td onclick="javascript:document.location='<s:property value="%{url}"/>'">
                        ${tournament.title}
                </td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
</s:if>
<s:else>
    <div>Пока не создано ни одного турнира</div>
</s:else>
<p>
    <a href="<s:url action='new-tournament'/>" class="btn btn-primary">Создать турнир</a>
    <a href="<s:url action='main'/>" class="btn btn-inverse">Возвратиться на главную страницу</a>
</p>