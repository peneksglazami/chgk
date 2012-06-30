<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<script type="text/javascript">
    var results = <s:property value="jsonResult"/>;
    function changeVerdict(teamId, questionNumber) {
        results[teamId][questionNumber - 1] = 1 - results[teamId][questionNumber - 1];

        dojo.io.bind({
            url:"edit-answer-verdict.action",
            content:{
                "teamId":teamId,
                "questionNumber":questionNumber,
                "verdict":results[teamId][questionNumber - 1]
            },
            sync:false,
            method:"post",
            transport:"XMLHTTPTransport",
            load:function (type, data, http, args) {
                setRightAnswerImage(teamId, questionNumber, results[teamId][questionNumber - 1] == 1);
                refreshTable();
            },
            error:function (type, error, http) {
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
            dojo.byId("sum_" + teamId).innerHTML = teamSum[teamId];
            dojo.byId("ranking_" + teamId).innerHTML = teamRanking[teamId];
        }

        for (var questionNumber in questionRanking) {
            dojo.byId("question_ranking_" + questionNumber).innerHTML = questionRanking[questionNumber];
        }

        var teamsInfo = [];
        for (var teamId in results) {
            var info = {
                "teamId":teamId,
                "sum":teamSum[teamId],
                "ranking":teamRanking[teamId]
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
                dojo.byId("rank_" + teamsInfo[g].teamId).innerHTML = rank;
            }
            i = j + 1;
        }
    }

    function refreshTeamSum(teamId) {
        var sum = 0;
        for (var i = 0; i < results[teamId].length; i++) {
            sum += results[teamId][i];
        }
        dojo.byId("sum_" + teamId).innerHTML = sum;
    }

    function setRightAnswerImage(teamId, questionNumber, verdict) {
        dojo.byId("img_" + teamId + "_" + questionNumber).style.display = verdict ? '' : 'none';
    }
</script>
Название: <b><s:property value="tournament.title"/></b><br/>
Дата: <b><s:date name="tournament.date" format="dd.MM.yyyy"/></b><br/>
<s:set var="questionAmount" value="tournament.questionAmount"/>
<table class="edit-result-table">
    <col>
    <col>
    <c:forEach var="questionNumber" begin="1" end="${questionAmount}">
        <col class="answer-col">
    </c:forEach>
    <tr>
        <td>№</td>
        <td>Команда</td>
        <c:forEach var="questionNumber" begin="1" end="${questionAmount}">
            <td align="center">${questionNumber}</td>
        </c:forEach>
        <td align="center">Сумма</td>
        <td align="center">Рейтинг</td>
        <td align="center">Место</td>
    </tr>
    <s:iterator var="team" value="tournament.teams">
        <tr class="answer-row">
            <td>${team.number}</td>
            <td>${team.name}</td>
            <c:forEach var="questionNumber" begin="1" end="${questionAmount}">
                <td id="answer_${team.id}_${questionNumber}" align="center" style="cursor: pointer;"
                    onclick="changeVerdict(${team.id}, ${questionNumber});">
                    <img id="img_${team.id}_${questionNumber}" src="images/right-answer.gif" alt="+" border="0"
                         style="display: none;"/>
                </td>
            </c:forEach>
            <td id="sum_${team.id}" align="center"></td>
            <td id="ranking_${team.id}" align="center"></td>
            <td id="rank_${team.id}" align="center"></td>
        </tr>
    </s:iterator>
    <tr>
        <td colspan="2">Рейтинг вопроса</td>
        <c:forEach var="questionNumber" begin="1" end="${questionAmount}">
            <td id="question_ranking_${questionNumber}" align="center"></td>
        </c:forEach>
        <td colspan="3" align="center">Что? Где? Когда?</td>
    </tr>
</table>