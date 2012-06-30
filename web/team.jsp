<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:form theme="xhtml">
    <s:hidden name="tournamentId" value="%{#parameters.tournamentId}"/>
    <s:hidden name="team.id"/>
    <s:textfield name="team.number" label="Номер"/>
    <s:textfield name="team.name" label="Название"/>
    <s:select name="team.type" list="#{'JUNIOR':'Младшие школьники', 'SENIOR':'Старшие школьники'}" label="Возрастная группа"/>
    <s:if test="team == null || team.id == null">
        <s:submit value="Создать" cssClass="btn btn-success" action="save-team" method="save"/>
    </s:if>
    <s:else>
        <s:submit value="Сохранить" cssClass="btn btn-success" action="save-team" method="save"/>
        <s:submit value="Удалить" cssClass="btn btn-danger" action="delete-team"/>
    </s:else>
</s:form>