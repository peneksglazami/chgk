<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:form theme="simple">
    <s:hidden name="tournamentId" value="%{#parameters.tournamentId}"/>
    <s:hidden name="team.id"/>
    <table>
        <tr>
            <td align="right">Номер</td>
            <td><s:textfield name="team.number" size="10"/></td>
        </tr>
        <tr>
            <td align="right">Название</td>
            <td><s:textfield name="team.name" size="50"/></td>
        </tr>
        <tr>
            <td align="right">Возрастная группа</td>
            <td><s:select name="team.type" list="#{'JUNIOR':'Младшие школьники', 'SENIOR':'Старшие школьники'}"/></td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <s:if test="team == null || team.id == null">
                    <s:submit value="Создать" cssClass="bg-color-green fg-color-white" action="save-team" method="save"/>
                </s:if>
                <s:else>
                    <s:submit value="Сохранить" cssClass="bg-color-green fg-color-white" action="save-team" method="save"/>
                    <s:submit value="Удалить" cssClass="bg-color-red fg-color-white" action="delete-team"/>
                </s:else>
                <input class="button" type="button" value="Отмена" onclick="history.back()">
            </td>
        </tr>
    </table>
</s:form>