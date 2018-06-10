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

import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Алгоритм ранжирования команд на основе рейтинга взятых вопросов.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class RatingAlgorithm implements RankingAlgorithm {

    private static class RatingPoint extends RankingPoint<RatingPoint> {

        private Integer ratingPoint;

        private RatingPoint(Integer ratingPoint) {
            this.ratingPoint = ratingPoint;
        }

        @Override
        public int compareTo(RatingPoint o) {
            return ratingPoint.compareTo(o.ratingPoint);
        }

        @Override
        public String toString() {
            return ratingPoint.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if ((o == null) || (getClass() != o.getClass())) {
                return false;
            }

            RatingPoint that = (RatingPoint) o;
            return ratingPoint.equals(that.ratingPoint);
        }

        @Override
        public int hashCode() {
            return ratingPoint.hashCode();
        }
    }

    @Override
    public Map<Team, RankingPoint> getRankingPoints(Tournament tournament, Collection<Team> teams,
                                                    Collection<RightAnswer> rightAnswers) {
        int[] questionsRanking = new int[tournament.getQuestionAmount()];
        for (int i = 0; i < tournament.getQuestionAmount(); i++) {
            questionsRanking[i] = teams.size() + 1;
        }
        for (RightAnswer rightAnswer : rightAnswers) {
            questionsRanking[rightAnswer.getQuestionNumber() - 1]--;
        }

        Map<Team, Integer> teamRating = new HashMap<Team, Integer>();
        for (Team team : teams) {
            teamRating.put(team, 0);
        }
        for (RightAnswer rightAnswer : rightAnswers) {
            Team team = rightAnswer.getTeam();
            teamRating.put(team, teamRating.get(team) + questionsRanking[rightAnswer.getQuestionNumber() - 1]);
        }

        Map<Team, RankingPoint> rankingPointMap = new HashMap<Team, RankingPoint>();
        for (Team team : teams) {
            rankingPointMap.put(team, new RatingPoint(teamRating.get(team)));
        }
        return Collections.unmodifiableMap(rankingPointMap);
    }

    @Override
    public String getPointName() {
        return "Рейтинг";
    }
}
