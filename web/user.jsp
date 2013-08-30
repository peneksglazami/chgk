<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
    $(document).ready(function () {
        $('#deleteButton').click(function (e) {
            $.Dialog({
                'title': 'Удаление пользователя',
                'content': 'Вы точно хотите удалить пользователя? Вся информация о пользователя будет полностью удалена.',
                'overlay': true,
                'buttonsAlign': 'right',
                'buttons': {
                    'Да': {
                        'action': function () {
                            var form = $('<form>' +
                                    '<input type="hidden" name="action:delete-user">' +
                                    '<input type="hidden" name="userId" value="${user.id}"' +
                                    '</form>');
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
<s:form theme="simple" id="edit-user">
    <s:hidden name="user.id"/>
    <table>
        <tr>
            <td colspan="2">
                <s:fielderror/>
            </td>
        </tr>
        <tr>
            <td align="right">Логин</td>
            <td><s:textfield name="user.username" size="50" maxlength="20"/></td>
        </tr>
        <tr>
            <td align="right">Пароль</td>
            <td><s:password name="user.password" size="50" maxlength="50"/></td>
        </tr>
        <shiro:hasRole name="administrator">
            <tr>
                <td align="right">Роль</td>
                <td></td>
            </tr>
        </shiro:hasRole>
        <tr>
            <td colspan="2" align="right">
                <s:if test="user == null || user.id == null">
                    <s:submit value="Создать" cssClass="bg-color-green fg-color-white" action="save-user" method="user"/>
                </s:if>
                <s:else>
                    <s:submit value="Сохранить" cssClass="bg-color-green fg-color-white" action="save-user" method="user"/>
                    <input id="deleteButton" type="button" class="button bg-color-red fg-color-white" value="Удалить"/>
                </s:else>
                <input class="button" type="button" value="Отмена" onclick="history.back()">
            </td>
        </tr>
    </table>
</s:form>