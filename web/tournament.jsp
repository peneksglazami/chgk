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

        $('#addCategoryLink').click(function (e) {
            $.Dialog({
                'title': 'Добавление категории',
                'content': 'Название категории: <input id="categoryTitle" type="text">',
                'overlay': true,
                'buttonsAlign': 'right',
                'buttons': {
                    'Добавить': {
                        'action': function () {
                            var value = Utils.htmlEncode($("#categoryTitle").val());
                            var id = new Date().getTime();
                            $("#categoriesList").append('<div id="new_category_' + id + '">'
                                    + value + ' <a id="delete_new_category_' + id + '" style="cursor: pointer"'
                                    + id + '>Удалить</a>' +
                                    '<input type="hidden" name="new_category_' + id + '" value="' + value + '"></div>');
                            $("#delete_new_category_" + id).click(function (e) {
                                $("#new_category_" + id).remove();
                            });
                        }
                    },
                    'Отмена': {
                        'action': function () {
                        }
                    }
                }
            });
        });

        $("a[id^='delete_category']").click(function (e) {
            $(this).parent().remove();
        });
    });
</script>
<s:form theme="simple" id="edit-tournament">
    <s:hidden name="tournament.id"/>
    <table>
        <tr>
            <td colspan="2">
                <s:fielderror/>
            </td>
        </tr>
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
            <td align="right">Категории команд:</td>
            <td>
                <div id="categoriesList">
                    <s:if test="tournament != null || tournament.id != null || tournament.teamCategories.size > 0">
                        <s:set var="newCategoryId" value="%{0}"/>
                        <s:iterator var="category" value="tournament.teamCategories">
                            <div>
                                    ${category.title}
                                <s:if test="#category.id != null">
                                    <a id="delete_category_${category.id}" style="cursor: pointer">Удалить</a>
                                    <input type="hidden" name="category_${category.id}" value="${category.title}">
                                </s:if>
                                <s:else>
                                    <s:set var="newCategoryId" value="%{#newCategoryId + 1}"/>
                                    <a id="delete_category_${newCategoryId}" style="cursor: pointer">Удалить</a>
                                    <input type="hidden" name="new_category_${newCategoryId}" value="${category.title}"/>
                                </s:else>
                            </div>
                        </s:iterator>
                    </s:if>
                </div>
                <a id="addCategoryLink" style="cursor: pointer">Добавить категорию</a>
            </td>
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