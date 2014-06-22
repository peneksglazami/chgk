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
package org.cherchgk.actions.tournament.result.ranking;

import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Алгоритм ранжирования команд по количеству данных правильных
 * ответов.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class RightAnswerAlgorithm implements RankingAlgorithm {

    private static class RightAnswerPoint extends RankingPoint<RightAnswerPoint> {

        private Integer rightAnswerCount;

        private RightAnswerPoint(Integer rightAnswerCount) {
            this.rightAnswerCount = rightAnswerCount;
        }

        @Override
        public int compareTo(RightAnswerPoint o) {
            return rightAnswerCount.compareTo(o.rightAnswerCount);
        }

        @Override
        public String toString() {
            return rightAnswerCount.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if ((o == null) || (getClass() != o.getClass())) {
                return false;
            }

            RightAnswerPoint that = (RightAnswerPoint) o;
            return rightAnswerCount.equals(that.rightAnswerCount);
        }

        @Override
        public int hashCode() {
            return rightAnswerCount.hashCode();
        }
    }

    @Override
    public Map<Team, RankingPoint> getRankingPoints(Tournament tournament, Collection<Team> teams,
                                                    Collection<RightAnswer> rightAnswers) {
        Map<Team, Integer> teamSum = new HashMap<Team, Integer>();
        for (Team team : teams) {
            teamSum.put(team, 0);
        }
        for (RightAnswer rightAnswer : rightAnswers) {
            Team team = rightAnswer.getTeam();
            teamSum.put(team, teamSum.get(team) + 1);
        }
        Map<Team, RankingPoint> rankingPointMap = new HashMap<Team, RankingPoint>();
        for (Map.Entry<Team, Integer> result : teamSum.entrySet()) {
            rankingPointMap.put(result.getKey(), new RightAnswerPoint(result.getValue()));
        }
        return Collections.unmodifiableMap(rankingPointMap);
    }

    @Override
    public String getPointName() {
        return "Очки";
    }
}
