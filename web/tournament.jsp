<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<s:form action="save-tournament">
    <s:hidden name="tournament.id"/>
    <s:textfield name="tournament.title" label="Название"/>
    <sx:datetimepicker name="tournament.date" label="Дата" displayFormat="dd.MM.yyyy"/>
    <s:textfield name="tournament.questionAmount" label="Количество вопросов"/>
    <s:if test="tournament == null || tournament.id == null">
        <s:submit cssClass="btn btn-success" value="Создать"/>
    </s:if>
    <s:else>
        <s:submit cssClass="btn btn-success" value="Сохранить"/>
    </s:else>
</s:form>