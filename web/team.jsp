<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
    $(document).ready(function () {
        $('#deleteButton').click(function (e) {
            $.Dialog({
                'title': 'Удаление команды',
                'content': 'Вы точно хотите удалить команду? Вся информация о команде будет полностью удалена.',
                'overlay': true,
                'buttonsAlign': 'right',
                'buttons': {
                    'Да': {
                        'action': function () {
                            $('<input />').attr('type', 'hidden')
                                    .attr('name', 'action:delete-team')
                                    .attr('value', 'Удалить')
                                    .appendTo('#edit-team');
                            document.getElementById("edit-team").submit();
                        }
                    },
                    'Нет': {
                        'action': function () {
                        }
                    }
                }
            });
        });
    });
</script>
<s:form theme="simple" id="edit-team">
    <s:hidden name="tournament.id"/>
    <s:hidden name="team.id"/>
    <table>
        <tr>
            <td colspan="2">
                <s:fielderror/>
            </td>
        </tr>
        <tr>
            <td align="right">Номер</td>
            <td><s:textfield name="team.number" size="10"/></td>
        </tr>
        <tr>
            <td align="right">Название</td>
            <td><s:textfield name="team.name" size="50"/></td>
        </tr>
        <s:if test="tournament.teamCategories.size > 0">
            <tr>
                <td align="right">Категория команды</td>
                <td><s:select name="teamCategory" list="teamCategories" value="team.teamCategory.id"/></td>
            </tr>
        </s:if>
        <tr>
            <td colspan="2" align="right">
                <s:if test="team == null || team.id == null">
                    <s:submit value="Создать" cssClass="bg-color-green fg-color-white" action="save-team" method="save"/>
                </s:if>
                <s:else>
                    <s:submit value="Сохранить" cssClass="bg-color-green fg-color-white" action="save-team" method="save"/>
                    <input id="deleteButton" type="button" class="button bg-color-red fg-color-white" value="Удалить"/>
                </s:else>
                <input class="button" type="button" value="Отмена" onclick="history.back()">
            </td>
        </tr>
    </table>
</s:form>