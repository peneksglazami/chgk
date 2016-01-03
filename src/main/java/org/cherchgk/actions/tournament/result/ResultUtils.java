/*
 * Copyright 2012-2016 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cherchgk.actions.tournament.result;

import org.cherchgk.actions.tournament.result.ranking.RankingAlgorithm;
import org.cherchgk.actions.tournament.result.ranking.RankingPoint;
import org.cherchgk.actions.tournament.result.ranking.RightAnswerAlgorithm;
import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.TeamCategory;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;

import java.util.*;

/**
 * Набор утилитарных методов для работы со страницей редактирования
 * результатов турнира.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ResultUtils {

    /**
     * Получение информации о результатах турнира в виде JSON сообщения.
     * JSON имеет следующий вид
     * <pre>
     * {
     *   "questionAmount": 36,
     *   "rankingPointNames": ["Очки", "Рейтинг"],
     *   "result" : {
     *       "1": {
     *           "rightAnswers": [1, 12, 13],
     *           "rankingPoints": ["3", "6"],
     *           "place": "1"
     *       },
     *       "2": {
     *           "rightAnswers": [2, 11],
     *           "rankingPoints": ["2", "4"],
     *           "place": "2"
     *       }
     *   }
     * }
     * </pre>
     *
     * @param tournament        турнир
     * @param tournamentService сервис потучения информации о турнирах
     * @return JSON-объект с результатами турнира
     */
    public static String getJSONResult(Tournament tournament, TournamentService tournamentService) {
        TournamentResult tournamentResult = getTournamentResult(tournament, null, tournamentService);
        return tournamentResultToJSON(tournamentResult);
    }

    public static String tournamentResultToJSON(TournamentResult tournamentResult) {
        StringBuilder json = new StringBuilder("{\"questionAmount\":")
                .append(tournamentResult.getTournament().getQuestionAmount())
                .append(",\"rankingPointNames\":[");
        boolean isFirst = true;
        for (RankingAlgorithm rankingAlgorithm : tournamentResult.getRankingAlgorithms()) {
            if (isFirst) {
                isFirst = false;
            } else {
                json.append(",");
            }
            json.append("\"").append(rankingAlgorithm.getPointName()).append("\"");
        }
        json.append("],\"result\":{");

        Map<Team, List<Integer>> teamRightAnswers = new HashMap<Team, List<Integer>>();
        for (RightAnswer rightAnswer : tournamentResult.getRightAnswers()) {
            List<Integer> rightAnswers = teamRightAnswers.get(rightAnswer.getTeam());
            if (rightAnswers == null) {
                rightAnswers = new ArrayList<Integer>();
            }
            rightAnswers.add(rightAnswer.getQuestionNumber());
            teamRightAnswers.put(rightAnswer.getTeam(), rightAnswers);
        }
        isFirst = true;
        for (TournamentResult.TeamResult teamResult : tournamentResult.getTeamResultList()) {
            if (isFirst) {
                isFirst = false;
            } else {
                json.append(",");
            }
            json.append("\"").append(teamResult.getTeam().getId()).append("\": {\"rightAnswers\":[");
            List<Integer> questionNumbers = teamRightAnswers.get(teamResult.getTeam());
            if (questionNumbers != null) {
                boolean isFirstRightAnswer = true;
                for (Integer questionNumber : teamRightAnswers.get(teamResult.getTeam())) {
                    if (isFirstRightAnswer) {
                        isFirstRightAnswer = false;
                    } else {
                        json.append(",");
                    }
                    json.append(questionNumber);
                }
            }
            json.append("],\"rankingPoints\":[");
            boolean isFirstRankingPoint = true;
            for (Map<Team, RankingPoint> teamRankingPointMap : tournamentResult.getRankingPointsList()) {
                if (isFirstRankingPoint) {
                    isFirstRankingPoint = false;
                } else {
                    json.append(",");
                }
                json.append("\"").append(teamRankingPointMap.get(teamResult.getTeam())).append("\"");
            }
            json.append("],\"place\":\"").append(teamResult.getPlace()).append("\"}");
        }
        json.append("}}");

        return json.toString();
    }

    public static TournamentResult getTournamentResult(Tournament tournament, TeamCategory teamCategory,
                                                       TournamentService tournamentService) {
        // фильтрация команд по указанной категории
        List<Team> teams;
        if (teamCategory != null) {
            teams = new ArrayList<Team>();
            for (Team team : tournament.getTeams()) {
                if (teamCategory.equals(team.getTeamCategory())) {
                    teams.add(team);
                }
            }
        } else {
            teams = new ArrayList<Team>(tournament.getTeams());
        }

        List<RightAnswer> rightAnswers = tournamentService.getAllRightAnswers(tournament, teamCategory);

        // указание основных и дополнительных алгоритмов расчёта результирующих показателей
        List<RankingAlgorithm> rankingAlgorithms = new ArrayList<RankingAlgorithm>(2);
        rankingAlgorithms.add(new RightAnswerAlgorithm()); // основной показатель - количество правильных ответов
        if (!tournament.getRankingMethod().equals(Tournament.RankingMethod.NOTHING)) {
            rankingAlgorithms.add(tournament.getRankingMethod().getRankingAlgorithm());
        }

        // расчёт результирующих показателей
        final List<Map<Team, RankingPoint>> rankingPointsList = new ArrayList<Map<Team, RankingPoint>>();
        for (RankingAlgorithm rankingAlgorithm : rankingAlgorithms) {
            rankingPointsList.add(rankingAlgorithm.getRankingPoints(tournament, teams, rightAnswers));
        }

        // Сортировка команд в соответствии с результирующими показателями.
        // Сначала сортируем команды по основному показателю, если значения
        // основных показателей совпадают, то команды сортируются по дополнительному
        // показателю.
        Collections.sort(teams, new Comparator<Team>() {

            @Override
            public int compare(Team firstTeam, Team secondTeam) {
                return compareTeamResult(firstTeam, secondTeam, rankingPointsList);
            }
        });

        // формирование итогового результата
        List<TournamentResult.TeamResult> teamResults = getTeamResults(teams, rankingPointsList);

        return new TournamentResult(tournament, rightAnswers, teamResults, rankingPointsList, rankingAlgorithms);
    }

    private static int compareTeamResult(Team firstTeam, Team secondTeam,
                                         List<Map<Team, RankingPoint>> rankingPointsList) {
        for (Map<Team, RankingPoint> rankingPointMap : rankingPointsList) {
            RankingPoint firstTeamRankingPoint = rankingPointMap.get(firstTeam);
            RankingPoint secondTeamRankingPoint = rankingPointMap.get(secondTeam);
            int res = firstTeamRankingPoint.compareTo(secondTeamRankingPoint);
            if (res != 0) {
                return (res > 0) ? -1 : 1;
            }
        }
        return 0;
    }

    private static List<TournamentResult.TeamResult> getTeamResults(List<Team> teams,
                                                                    List<Map<Team, RankingPoint>> rankingPointsList) {
        List<TournamentResult.TeamResult> teamResults = new ArrayList<TournamentResult.TeamResult>(teams.size());
        int i = 0;
        while (i < teams.size()) {
            int j = i;
            while ((j < teams.size())
                    && (compareTeamResult(teams.get(i), teams.get(j), rankingPointsList) == 0)) {
                j++;
            }
            j--;
            if (i == j) {
                teamResults.add(new TournamentResult.TeamResult(teams.get(i), String.valueOf(i + 1)));
            } else {
                String place = String.valueOf(i + 1) + "-" + String.valueOf(j + 1);
                for (int g = i; g <= j; g++) {
                    teamResults.add(new TournamentResult.TeamResult(teams.get(g), place));
                }
            }
            i = j + 1;
        }
        return teamResults;
    }
}
