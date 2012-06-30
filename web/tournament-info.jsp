<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
Название: <b><s:property value="tournament.title"/></b><br/>
Дата: <b><s:date name="tournament.date" format="dd.MM.yyyy"/></b><br/>

<div class="btn-toolbar">
    <div class="btn-group">
        <s:url var="editTournamentUrl" action="edit-tournament">
            <s:param name="tournamentId">${tournament.id}</s:param>
        </s:url>
        <s:a href="%{editTournamentUrl}" cssClass="btn">Изменить описание турнира</s:a>
    </div>
    <div class="btn-group">
        <s:url var="editTournamentResultUrl" action="edit-tournament-result">
            <s:param name="tournamentId">${tournament.id}</s:param>
        </s:url>
        <s:a href="%{editTournamentResultUrl}" cssClass="btn">Редактировать результаты турнира</s:a>
    </div>
    <div class="btn-group">
        <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">
            Просмотр результатов турнира
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
            <li>
                <s:url var="showTournamentResultUrl" action="show-tournament-result">
                    <s:param name="tournamentId">${tournament.id}</s:param>
                </s:url>
                <s:a href="%{showTournamentResultUrl}">Общий зачёт</s:a>
            </li>
            <li>
                <s:url var="showJuniorTournamentResultUrl" action="show-tournament-result">
                    <s:param name="tournamentId">${tournament.id}</s:param>
                    <s:param name="teamType">JUNIOR</s:param>
                </s:url>
                <s:a href="%{showJuniorTournamentResultUrl}">Младшие школьники</s:a>
            </li>
            <li>
                <s:url var="showSeniorTournamentResultUrl" action="show-tournament-result">
                    <s:param name="tournamentId">${tournament.id}</s:param>
                    <s:param name="teamType">SENIOR</s:param>
                </s:url>
                <s:a href="%{showSeniorTournamentResultUrl}">Старшие школьники</s:a>
            </li>
        </ul>
    </div>
</div>

<s:url var="createTeamUrl" action="new-team">
    <s:param name="tournamentId"><s:property value="tournament.id"/></s:param>
</s:url>
<p><s:a href="%{createTeamUrl}" cssClass="btn btn-primary">Зарегистрировать команду</s:a></p>

<s:if test="tournament.teams.size > 0">
    <table class="list-table table table-bordered">
        <col width="20px"/>
        <col width="50px"/>
        <col/>
        <thead>
        <tr>
            <th colspan="3"><b>Список зарегистрированных команд</b></th>
        </tr>
        <tr>
            <td><b>№</b></td>
            <td><b>Категория</b></td>
            <td><b>Название</b></td>
        </tr>
        </thead>
        <tbody>
        <s:iterator var="team" value="tournament.teams">
            <s:url var="editTeamUrl" action="edit-team">
                <s:param name="teamId">${team.id}</s:param>
            </s:url>
            <tr onclick="javascript:document.location='<s:property value="%{editTeamUrl}"/>'">
                <td>${team.number}</td>
                <td>${team.type.name == 'JUNIOR' ? 'мш' : 'сш'}</td>
                <td>${team.name}</td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
</s:if>
<s:else>
    <div>Пока ни одна команда не зарегистрирована на турнир</div>
</s:else>