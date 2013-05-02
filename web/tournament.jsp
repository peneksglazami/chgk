<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<script type="text/javascript">
    $(function () {
        $.datepicker.setDefaults($.datepicker.regional[""]);
        $("#datepicker").datepicker($.datepicker.regional["ru"]);
    });
</script>
<s:form theme="simple" action="save-tournament">
    <s:hidden name="tournament.id"/>
    <table>
        <tr>
            <td align="right">Название:</td>
            <td><s:textfield name="tournament.title" size="50"/></td>
        </tr>
        <tr>
            <td align="right">Дата:</td>
            <td>
                <input type="text" id="datepicker" name="tournament.date" value="${tournament.dateAsString}"/>
            </td>
        </tr>
        <tr>
            <td align="right">Количество вопросов:</td>
            <td><s:textfield name="tournament.questionAmount" size="2"/></td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <s:if test="tournament == null || tournament.id == null">
                    <s:submit cssClass="bg-color-green fg-color-white" value="Создать" action="save-tournament"/>
                </s:if>
                <s:else>
                    <s:submit cssClass="bg-color-green fg-color-white" value="Сохранить" action="save-tournament"/>
                    <s:submit cssClass="bg-color-red fg-color-white" value="Удалить" action="delete-tournament"/>
                </s:else>
                <input class="button" type="button" value="Отмена" onclick="history.back()">
            </td>
        </tr>
    </table>
</s:form>