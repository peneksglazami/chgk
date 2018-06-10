/*
 * Copyright 2012-2018 Andrey Grigorov, Anton Grigorov
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
package org.cherchgk.actions.tournament.result.ranking;

import junit.framework.TestCase;
import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Тестирование алгоритма расчёта рейтинга взятых вопросов.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class RatingAlgorithmTest extends TestCase {

    public void testAllTeamsHaveEqualResults() throws Exception {
        RankingAlgorithm algorithm = new RatingAlgorithm();

        Tournament tournament = new Tournament();
        tournament.setQuestionAmount(36);

        Collection<Team> teams = new ArrayList<Team>();
        Team team1 = new Team();
        team1.setId(2L);
        team1.setName("Команда №2");
        team1.setNumber(1);
        team1.setTournament(tournament);

        Team team2 = new Team();
        team2.setId(1L);
        team2.setName("Команда №1");
        team2.setNumber(1);
        team2.setTournament(tournament);

        teams.add(team1);
        teams.add(team2);

        Collection<RightAnswer> rightAnswers = new ArrayList<RightAnswer>();
        RightAnswer rightAnswer1 = new RightAnswer();
        rightAnswer1.setId(1L);
        rightAnswer1.setQuestionNumber(1);
        rightAnswer1.setTeam(team1);

        RightAnswer rightAnswer2 = new RightAnswer();
        rightAnswer2.setId(3L);
        rightAnswer2.setQuestionNumber(2);
        rightAnswer2.setTeam(team2);

        RightAnswer rightAnswer3 = new RightAnswer();
        rightAnswer3.setId(2L);
        rightAnswer3.setQuestionNumber(36);
        rightAnswer3.setTeam(team1);

        RightAnswer rightAnswer4 = new RightAnswer();
        rightAnswer4.setId(4L);
        rightAnswer4.setQuestionNumber(36);
        rightAnswer4.setTeam(team2);

        rightAnswers.addAll(Arrays.asList(rightAnswer1, rightAnswer2, rightAnswer3, rightAnswer4));

        Map<Team, RankingPoint> rankingPointMap = algorithm.getRankingPoints(tournament, teams, rightAnswers);
        RankingPoint rankingPoint1 = rankingPointMap.get(team1);
        RankingPoint rankingPoint2 = rankingPointMap.get(team2);

        assertTrue(rankingPoint1.equals(rankingPoint2));
    }
}