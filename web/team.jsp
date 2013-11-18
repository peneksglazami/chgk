<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<shiro:hasPermission name="team:edit:${team.id}">
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
                                var form = $('<form>' +
                                        '<input type="hidden" name="action:delete-team">' +
                                        '<input type="hidden" name="teamId" value="${team.id}">' +
                                        '</form>').appendTo('body');
                                form.submit();
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
</shiro:hasPermission>
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
            <td>
                <shiro:hasPermission name="team:edit:${team.id}">
                    <s:textfield name="team.number" size="10"/>
                </shiro:hasPermission>
                <shiro:lacksPermission name="team:edit:${team.id}">
                    <s:label name="team.number"/>
                </shiro:lacksPermission>
            </td>
        </tr>
        <tr>
            <td align="right">Название</td>
            <td>
                <shiro:hasPermission name="team:edit:${team.id}">
                    <s:textfield name="team.name" size="50"/>
                </shiro:hasPermission>
                <shiro:lacksPermission name="team:edit:${team.id}">
                    <s:label name="team.name"/>
                </shiro:lacksPermission>
            </td>
        </tr>
        <s:if test="tournament.teamCategories.size > 0">
            <tr>
                <td align="right">Категория команды</td>
                <td>
                    <shiro:hasPermission name="team:edit:${team.id}">
                        <s:select name="teamCategory" list="teamCategories" value="team.teamCategory.id"/>
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="team:edit:${team.id}">
                        <s:label name="team.teamCategory.title"/>
                    </shiro:lacksPermission>
                </td>
            </tr>
        </s:if>
        <tr>
            <td colspan="2" align="right">
                <s:if test="team == null || team.id == null">
                    <shiro:hasPermission name="team:create">
                        <s:submit value="Создать" cssClass="bg-color-green fg-color-white" action="save-team"
                                  method="save"/>
                    </shiro:hasPermission>
                </s:if>
                <s:else>
                    <shiro:hasPermission name="team:edit:${team.id}">
                        <s:submit value="Сохранить" cssClass="bg-color-green fg-color-white" action="save-team"
                                  method="save"/>
                        <input id="deleteButton" type="button" class="button bg-color-red fg-color-white"
                               value="Удалить"/>
                    </shiro:hasPermission>
                </s:else>
                <input class="button" type="button" value="Назад" onclick="history.back()">
            </td>
        </tr>
    </table>
</s:form>