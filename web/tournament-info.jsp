<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
Название: <b><s:property value="tournament.title"/></b><br/>
Дата: <b><s:date name="tournament.date" format="dd.MM.yyyy"/></b><br/>

<div>
    <shiro:hasPermission name="tournament:edit:${tournament.id}">
        <s:url var="editTournamentUrl" action="edit-tournament">
            <s:param name="tournamentId"><s:property value="tournament.id"/></s:param>
        </s:url>
        <s:a href="%{editTournamentUrl}" cssClass="button">Изменить описание турнира</s:a>
    </shiro:hasPermission>
    <shiro:hasPermission name="tournament:edit:${tournament.id}">
        <s:url var="editTournamentResultUrl" action="edit-tournament-result">
            <s:param name="tournamentId"><s:property value="tournament.id"/></s:param>
        </s:url>
        <s:a href="%{editTournamentResultUrl}" cssClass="button">Редактировать результаты турнира</s:a>
    </shiro:hasPermission>

    <s:if test="tournament.teamCategories.size > 0">
        <div class="button bg-color-blue fg-color-white" data-role="dropdown">
            <div>Просмотр результатов турнира</div>
            <ul class="dropdown-menu">
                <li>
                    <s:url var="showTournamentResultUrl" action="show-tournament-result">
                        <s:param name="tournamentId"><s:property value="tournament.id"/></s:param>
                    </s:url>
                    <s:a href="%{showTournamentResultUrl}">Общий зачёт</s:a>
                </li>
                <s:iterator var="teamCategory" value="tournament.teamCategories">
                    <li>
                        <s:url var="showTeamCategoryTournamentResultUrl" action="show-tournament-result">
                            <s:param name="tournamentId"><s:property value="tournament.id"/></s:param>
                            <s:param name="teamCategoryId">${teamCategory.id}</s:param>
                        </s:url>
                        <s:a href="%{showTeamCategoryTournamentResultUrl}">${teamCategory.title}</s:a>
                    </li>
                </s:iterator>
            </ul>
        </div>
    </s:if>
    <s:else>
        <s:url var="showTournamentResultUrl" action="show-tournament-result">
            <s:param name="tournamentId"><s:property value="tournament.id"/></s:param>
        </s:url>
        <s:a href="%{showTournamentResultUrl}" cssClass="button bg-color-blue fg-color-white">Просмотр результатов турнира</s:a>
    </s:else>

    <shiro:hasPermission name="tournament:edit:${tournament.id}">
        <s:url var="createTeamUrl" action="new-team">
            <s:param name="tournamentId"><s:property value="tournament.id"/></s:param>
        </s:url>
        <s:a href="%{createTeamUrl}" cssClass="button bg-color-green fg-color-white">Зарегистрировать команду</s:a>
    </shiro:hasPermission>
</div>

<s:if test="tournament.teams.size > 0">
    <b>Список зарегистрированных команд</b>
    <table class="list-table table table-bordered row-hovered">
        <col width="20px"/>
        <s:if test="tournament.teamCategories.size > 0">
            <col width="50px"/>
        </s:if>
        <col/>
        <thead>
        <tr>
            <td><b>№</b></td>
            <s:if test="tournament.teamCategories.size > 0">
                <td><b>Категория</b></td>
            </s:if>
            <td><b>Название</b></td>
        </tr>
        </thead>
        <tbody>
        <s:iterator var="team" value="tournament.teams">
            <s:url var="editTeamUrl" action="edit-team">
                <s:param name="teamId">${team.id}</s:param>
            </s:url>
            <tr onclick="document.location='<s:property value="%{editTeamUrl}"/>'">
                <td>${team.number}</td>
                <s:if test="tournament.teamCategories.size > 0">
                    <td>${team.teamCategory.title}</td>
                </s:if>
                <td><b>${team.name}</b></td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
</s:if>
<s:else>
    <div>Пока ни одна команда не зарегистрирована на турнир</div>
</s:else>
<a href="<s:url action="tournament-list"/>" class="button">К списку турниров</a>