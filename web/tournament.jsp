<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<script type="text/javascript">
    $(function () {
        $.datepicker.setDefaults($.datepicker.regional[""]);
        $("#datepicker").datepicker($.datepicker.regional["ru"]);
    });

    $(document).ready(function () {
        $('#deleteButton').click(function (e) {
            $.Dialog({
                'title': 'Удаление турнира',
                'content': 'Вы точно хотите удалить турнир? Вся информация о турнире будет полностью удалена.',
                'overlay': true,
                'buttonsAlign': 'right',
                'buttons': {
                    'Да': {
                        'action': function () {
                            $('<input />').attr('type', 'hidden')
                                    .attr('name', 'action:delete-tournament')
                                    .attr('value', 'Удалить')
                                    .appendTo('#edit-tournament');
                            document.getElementById("edit-tournament").submit();
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
<s:form theme="simple" id="edit-tournament">
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
                    <input id="deleteButton" type="button" class="button bg-color-red fg-color-white" value="Удалить"/>
                </s:else>
                <input class="button" type="button" value="Отмена" onclick="history.back()">
            </td>
        </tr>
    </table>
</s:form>