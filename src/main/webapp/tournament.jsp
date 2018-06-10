<%--
  Copyright 2012-2018 Andrey Grigorov, Anton Grigorov

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy of
  the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations under
  the License.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<shiro:hasPermission name="tournament:edit:${tournament.id}">
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
</shiro:hasPermission>
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
            <td>
                <shiro:hasPermission name="tournament:edit:${tournament.id}">
                    <s:textfield name="tournament.title" size="50"/>
                </shiro:hasPermission>
                <shiro:lacksPermission name="tournament:edit:${tournament.id}">
                    <s:label name="tournament.title"/>
                </shiro:lacksPermission>
            </td>
        </tr>
        <tr>
            <td align="right">Дата:</td>
            <td>
                <shiro:hasPermission name="tournament:edit:${tournament.id}">
                    <input type="text" id="datepicker" name="tournament.date" size="7"
                           value="${tournament.dateAsString}"/>
                </shiro:hasPermission>
                <shiro:lacksPermission name="tournament:edit:${tournament.id}">
                    <s:label name="tournament.dateAsString"/>
                </shiro:lacksPermission>
            </td>
        </tr>
        <tr>
            <td align="right">Количество вопросов:</td>
            <td>
                <shiro:hasPermission name="tournament:edit:${tournament.id}">
                    <s:textfield name="tournament.questionAmount" size="2"/>
                </shiro:hasPermission>
                <shiro:lacksPermission name="tournament:edit:${tournament.id}">
                    <s:label name="tournament.questionAmount"/>
                </shiro:lacksPermission>
            </td>
        </tr>
        <tr>
            <td align="right">Количество туров:</td>
            <td>
                <shiro:hasPermission name="tournament:edit:${tournament.id}">
                    <s:textfield name="tournament.roundAmount" size="2"/>
                </shiro:hasPermission>
                <shiro:lacksPermission name="tournament:edit:${tournament.id}">
                    <s:label name="tournament.roundAmount"/>
                </shiro:lacksPermission>
            </td>
        </tr>
        <tr>
            <td align="right">Нумерация вопросов:</td>
            <td>
                <shiro:hasPermission name="tournament:edit:${tournament.id}">
                    <s:select name="tournament.questionNumberingType"
                              listValue="title"
                              list="questionNumberingTypes"/>
                </shiro:hasPermission>
                <shiro:lacksPermission name="tournament:edit:${tournament.id}">
                    <s:label name="tournament.questionNumberingType.title"/>
                </shiro:lacksPermission>
            </td>
        </tr>
        <tr>
            <td align="right">Дополнительный показатель:</td>
            <td>
                <shiro:hasPermission name="tournament:edit:${tournament.id}">
                    <s:select name="tournament.rankingMethod"
                              listValue="title"
                              list="rankingMethods"/>
                </shiro:hasPermission>
                <shiro:lacksPermission name="tournament:edit:${tournament.id}">
                    <s:label name="tournament.rankingMethod.title"/>
                </shiro:lacksPermission>
            </td>
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
                                <shiro:hasPermission name="tournament:edit:${tournament.id}">
                                    <s:if test="#category.id != null">
                                        <a id="delete_category_${category.id}" style="cursor: pointer">Удалить</a>
                                        <input type="hidden" name="category_${category.id}" value="${category.title}">
                                    </s:if>
                                    <s:else>
                                        <s:set var="newCategoryId" value="%{#newCategoryId + 1}"/>
                                        <a id="delete_category_${newCategoryId}" style="cursor: pointer">Удалить</a>
                                        <input type="hidden" name="new_category_${newCategoryId}"
                                               value="${category.title}"/>
                                    </s:else>
                                </shiro:hasPermission>
                            </div>
                        </s:iterator>
                    </s:if>
                </div>
                <shiro:hasPermission name="tournament:edit:${tournament.id}">
                    <a id="addCategoryLink" style="cursor: pointer">Добавить категорию</a>
                </shiro:hasPermission>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <s:if test="tournament == null || tournament.id == null">
                    <shiro:hasPermission name="tournament:create">
                        <s:submit cssClass="bg-color-green fg-color-white" value="Создать" action="save-tournament"/>
                    </shiro:hasPermission>
                </s:if>
                <s:else>
                    <shiro:hasPermission name="tournament:edit:${tournament.id}">
                        <s:submit cssClass="bg-color-green fg-color-white" value="Сохранить" action="save-tournament"/>
                        <input id="deleteButton" type="button" class="button bg-color-red fg-color-white"
                               value="Удалить"/>
                    </shiro:hasPermission>
                </s:else>
                <s:if test="tournament.id != null">
                    <s:url var="backButtonUrl" action="tournament-info">
                        <s:param name="tournamentId"><s:property value="tournament.id"/></s:param>
                    </s:url>
                </s:if>
                <s:else>
                    <s:url var="backButtonUrl" action="tournament-list"/>
                </s:else>
                <s:a href="%{backButtonUrl}" cssClass="button">Назад</s:a>
            </td>
        </tr>
    </table>
</s:form>