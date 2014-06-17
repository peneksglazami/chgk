<%--
  Copyright 2012-2014 Andrey Grigorov, Anton Grigorov
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/frameworks/atmosphere/atmosphere-min.js"></script>
<script type="text/javascript">
    var results = <s:property value="jsonResult" escape="false"/>;
    function changeVerdict(teamId, questionNumber) {
        results[teamId][questionNumber - 1] = 1 - results[teamId][questionNumber - 1];

        dojo.io.bind({
            url: "edit-answer-verdict.action",
            content: {
                "teamId": teamId,
                "questionNumber": questionNumber,
                "verdict": results[teamId][questionNumber - 1]
            },
            sync: false,
            method: "post",
            transport: "XMLHTTPTransport",
            load: function (type, data, http, args) {
                setRightAnswerImage(teamId, questionNumber, results[teamId][questionNumber - 1] == 1);
                refreshTable();
            },
            error: function (type, error, http) {
            }
        });
    }
    dojo.addOnLoad(function () {
        refreshTable();
        for (var teamId in results) {
            for (var i = 0; i < results[teamId].length; i++) {
                if (results[teamId][i] == 1) {
                    setRightAnswerImage(teamId, i + 1, true);
                }
            }
        }
    });

    function refreshTable() {
        var questionRanking = [];
        var teamSum = [];
        var teamAmount = 0;
        for (var teamId in results) {
            teamAmount++;
        }
        for (var teamId in results) {
            teamSum[teamId] = 0;
            for (var i = 0; i < results[teamId].length; i++) {
                teamSum[teamId] += results[teamId][i];
                if ((questionRanking[i + 1] == undefined) || (questionRanking[i + 1] == null)) {
                    questionRanking[i + 1] = teamAmount + 1;
                }
                questionRanking[i + 1] -= results[teamId][i];
            }
        }
        var teamRanking = [];
        for (var teamId in results) {
            teamRanking[teamId] = 0;
            for (var i = 0; i < results[teamId].length; i++) {
                if (results[teamId][i] == 1) {
                    teamRanking[teamId] += questionRanking[i + 1];
                }
            }
        }

        for (var teamId in results) {
            $('td[id^="sum_' + teamId + '"]').each(function (index, elem) {
                elem.innerHTML = teamSum[teamId];
            });
            $('td[id^="ranking_' + teamId + '"]').each(function (index, elem) {
                elem.innerHTML = teamRanking[teamId];
            });
        }

        for (var questionNumber in questionRanking) {
            dojo.byId("question_ranking_" + questionNumber).innerHTML = questionRanking[questionNumber];
        }

        var teamsInfo = [];
        for (var teamId in results) {
            var info = {
                "teamId": teamId,
                "sum": teamSum[teamId],
                "ranking": teamRanking[teamId]
            };
            teamsInfo.push(info);
        }
        for (var i = 0; i < teamsInfo.length; i++) {
            for (var j = i + 1; j < teamsInfo.length; j++) {
                if ((teamsInfo[i].sum < teamsInfo[j].sum) ||
                        ((teamsInfo[i].sum == teamsInfo[j].sum) && (teamsInfo[i].ranking < teamsInfo[j].ranking))) {
                    var tmp = teamsInfo[i];
                    teamsInfo[i] = teamsInfo[j];
                    teamsInfo[j] = tmp;
                }
            }
        }
        var i = 0;
        while (i < teamsInfo.length) {
            var j = i;
            while ((j < teamsInfo.length) &&
                    (teamsInfo[i].sum == teamsInfo[j].sum) &&
                    (teamsInfo[i].ranking == teamsInfo[j].ranking)) {
                j++;
            }
            j--;
            for (var g = i; g <= j; g++) {
                var rank = (i == j) ? i + 1 : (i + 1) + "-" + (j + 1);
                if (i < 3) {
                    rank = "<span style='color: red;'>" + rank + "</span>";
                }
                $('td[id^="rank_' + teamsInfo[g].teamId + '"]').each(function (index, elem) {
                    elem.innerHTML = rank;
                });
            }
            i = j + 1;
        }
    }

    function refreshTeamSum(teamId) {
        var sum = 0;
        for (var i = 0; i < results[teamId].length; i++) {
            sum += results[teamId][i];
        }
        $('td[id^="sum_' + teamId + '"]').each(function (index, elem) {
            elem.innerHTML = sum;
        });
    }

    function setRightAnswerImage(teamId, questionNumber, verdict) {
        dojo.byId("img_" + teamId + "_" + questionNumber).style.display = verdict ? '' : 'none';
    }

    function showRoundTab(roundNumber) {
        dojo.html.getElementsByClassName("tab-round").forEach(function (tab) {
            dojo.html.removeClass(tab.id, "active");
        });
        dojo.html.getElementsByClassName("frame").forEach(function (frame) {
            frame.style.display = 'none';
        });

        dojo.byId("frame_round_" + roundNumber).style.display = 'block';
        dojo.html.addClass("tab_round_" + roundNumber, "active");
    }

    var request = {
        url: '${pageContext.request.contextPath}' + '/meteor',
        contentType: "application/json",
        transport: "websocket",
        reconnectInterval: 5000,
        fallbackTransport: "long-polling",
        async: true,
        headers: {
            "chgk-tournament-id": "<s:property value="tournament.id"/>"
        },
        attachHeadersAsQueryString: true,
        onMessage: function (response) {
            var message = response.responseBody;
            try {
                results = jQuery.parseJSON(message);
                refreshTable();
                for (var teamId in results) {
                    for (var i = 0; i < results[teamId].length; i++) {
                        setRightAnswerImage(teamId, i + 1, results[teamId][i] == 1);
                    }
                }
            } catch (e) {
                return;
            }
        }
    };
    atmosphere.subscribe(request);

</script>
Название: <b><s:property value="tournament.title"/></b><br/>
Дата: <b><s:date name="tournament.date" format="dd.MM.yyyy"/></b><br/>
<s:set var="questionAmount" value="tournament.questionAmount"/>
<s:set var="roundAmount" value="tournament.roundAmount"/>
<div class="tab-control">
    <ul class="tabs">
        <c:forEach var="roundNumber" begin="1" end="${roundAmount}">
            <c:if test="${roundNumber == 1}">
                <li id="tab_round_${roundNumber}" class="tab-round active">
            </c:if>
            <c:if test="${roundNumber > 1}">
                <li id="tab_round_${roundNumber}" class="tab-round">
            </c:if>
                <a onclick="showRoundTab(${roundNumber});">Тур ${roundNumber}</a>
            </li>
        </c:forEach>
    </ul>

    <div class="frames">
        <c:forEach var="roundNumber" begin="1" end="${roundAmount}">
            <c:if test="${roundNumber == 1}">
                <div class="frame" id="frame_round_${roundNumber}" style="display: block;">
            </c:if>
            <c:if test="${roundNumber > 1}">
                <div class="frame" id="frame_round_${roundNumber}" style="display: none;">
            </c:if>
                <table class="edit-result-table">
                    <col>
                    <col>
                    <c:forEach var="questionNumber" begin="${(roundNumber - 1) * (questionAmount div roundAmount) + 1}"
                               end="${roundNumber * (questionAmount div roundAmount)}">
                        <col class="answer-col">
                    </c:forEach>
                    <col>
                    <col>
                    <col>
                    <tr>
                        <td>№</td>
                        <td>Команда</td>
                        <s:if test="tournament.questionNumberingType.name == 'SEQUENTIALLY'">
                            <c:forEach var="questionNumber"
                                       begin="${(roundNumber - 1) * (questionAmount div roundAmount) + 1}"
                                       end="${roundNumber * (questionAmount div roundAmount)}">
                                <td align="center">${questionNumber}</td>
                            </c:forEach>
                        </s:if>
                        <s:else>
                            <c:forEach var="questionNumber"
                                       begin="1"
                                       end="${questionAmount div roundAmount}">
                                <td align="center">${questionNumber}</td>
                            </c:forEach>
                        </s:else>
                        <td align="center">Сумма</td>
                        <td align="center">Рейтинг</td>
                        <td align="center">Место</td>
                    </tr>
                    <s:iterator var="team" value="tournament.teams">
                        <tr class="answer-row">
                            <td>${team.number}</td>
                            <td>${team.name}</td>
                            <c:forEach var="questionNumber"
                                       begin="${(roundNumber - 1) * (questionAmount div roundAmount) + 1}"
                                       end="${roundNumber * (questionAmount div roundAmount)}">
                                <td id="answer_${team.id}_${questionNumber}" align="center" style="cursor: pointer;"
                                    onclick="changeVerdict(${team.id}, ${questionNumber});">
                                    <img id="img_${team.id}_${questionNumber}" src="images/right-answer.gif" alt="+"
                                         border="0"
                                         style="display: none;"/>
                                </td>
                            </c:forEach>
                            <td id="sum_${team.id}_${roundNumber}" align="center"></td>
                            <td id="ranking_${team.id}_${roundNumber}" align="center"></td>
                            <td id="rank_${team.id}_${roundNumber}" align="center"></td>
                        </tr>
                    </s:iterator>
                    <tr>
                        <td colspan="2">Рейтинг вопроса</td>
                        <c:forEach var="questionNumber"
                                   begin="${(roundNumber - 1) * (questionAmount div roundAmount) + 1}"
                                   end="${roundNumber * (questionAmount div roundAmount)}">
                            <td id="question_ranking_${questionNumber}" align="center"></td>
                        </c:forEach>
                        <td colspan="3" align="center">Что? Где? Когда?</td>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </div>
</div>

<input class="button" style="margin-top: 10px;" type="button" value="Назад" onclick="history.back()">