/*
 * Copyright 2012-2014 Andrey Grigorov, Anton Grigorov
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
import org.cherchgk.services.TeamService;
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
     * Подготовка информации о результатах турнира в формате JSON.
     *
     * @param tournament  турнир
     * @param teamService сервис работы с командами
     * @return JSON-объект с результатами турнира
     */
    public static String getJSONResult(Tournament tournament, TeamService teamService) {
        StringBuilder array = new StringBuilder("{");
        List<Team> teams = tournament.getTeams();
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            array.append("\"").append(team.getId()).append("\":[");
            List<RightAnswer> rightAnswers = teamService.getTeamRightAnswers(team);
            Set<Integer> rightAnswerNumbers = new HashSet<Integer>();
            for (RightAnswer rightAnswer : rightAnswers) {
                rightAnswerNumbers.add(rightAnswer.getQuestionNumber());
            }
            for (int j = 1; j <= tournament.getQuestionAmount(); j++) {
                array.append(rightAnswerNumbers.contains(j) ? "1" : "0");
                if (j != tournament.getQuestionAmount()) {
                    array.append(",");
                }
            }
            array.append("]");
            if (i != (teams.size() - 1)) {
                array.append(",");
            }
        }
        array.append("}");
        return array.toString();
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
            teams = tournament.getTeams();
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
