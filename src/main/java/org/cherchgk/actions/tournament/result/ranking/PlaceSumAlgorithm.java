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
package org.cherchgk.actions.tournament.result.ranking;

import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;

import java.math.BigDecimal;
import java.util.*;

/**
 * Алгоритм ранжирования команд на основе суммы мест в турах.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class PlaceSumAlgorithm implements RankingAlgorithm {

    private static class PlaceSumPoint extends RankingPoint<PlaceSumPoint> {

        private BigDecimal placeSum;

        private PlaceSumPoint(BigDecimal placeSum) {
            this.placeSum = placeSum;
        }

        @Override
        public int compareTo(PlaceSumPoint o) {
            if (placeSum.compareTo(o.placeSum) < 0) {
                return 1;
            } else if (placeSum.compareTo(o.placeSum) > 0) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return placeSum.toPlainString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if ((o == null) || (getClass() != o.getClass())) {
                return false;
            }

            PlaceSumPoint that = (PlaceSumPoint) o;
            return placeSum.equals(that.placeSum);
        }

        @Override
        public int hashCode() {
            return placeSum.hashCode();
        }
    }

    private static class TeamResult implements Comparable<TeamResult> {
        private Team team;
        private int rightAnswer;

        private TeamResult(Team team, int rightAnswer) {
            this.team = team;
            this.rightAnswer = rightAnswer;
        }

        @Override
        public int compareTo(TeamResult o) {
            return o.rightAnswer - rightAnswer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if ((o == null) || (getClass() != o.getClass())) {
                return false;
            }

            TeamResult that = (TeamResult) o;
            return rightAnswer == that.rightAnswer;
        }

        @Override
        public int hashCode() {
            return rightAnswer;
        }
    }

    @Override
    public Map<Team, RankingPoint> getRankingPoints(Tournament tournament, Collection<Team> teams,
                                                    Collection<RightAnswer> rightAnswers) {
        Map<Team, BigDecimal> teamPlaceSumMap = new HashMap<Team, BigDecimal>();
        for (Team team : teams) {
            teamPlaceSumMap.put(team, BigDecimal.ZERO);
        }
        int questionsInRound = tournament.getQuestionAmount() / tournament.getRoundAmount();
        for (int round = 0; round < tournament.getRoundAmount(); round++) {

            Map<Team, Integer> teamSum = new HashMap<Team, Integer>();
            for (Team team : teams) {
                teamSum.put(team, 0);
            }
            for (RightAnswer rightAnswer : rightAnswers) {
                if ((rightAnswer.getQuestionNumber() > round * questionsInRound)
                        && (rightAnswer.getQuestionNumber() <= (round + 1) * questionsInRound)) {
                    Team team = rightAnswer.getTeam();
                    teamSum.put(team, teamSum.get(team) + 1);
                }
            }

            List<TeamResult> roundTeamResults = new ArrayList<TeamResult>(teams.size());
            for (Map.Entry<Team, Integer> teamResult : teamSum.entrySet()) {
                roundTeamResults.add(new TeamResult(teamResult.getKey(), teamResult.getValue()));
            }

            Collections.sort(roundTeamResults);

            int i = 0;
            while (i < roundTeamResults.size()) {
                int j = i;
                while ((j < roundTeamResults.size())
                        && (roundTeamResults.get(i).compareTo(roundTeamResults.get(j)) == 0)) {
                    j++;
                }
                j--;
                if (i == j) {
                    Team team = roundTeamResults.get(i).team;
                    teamPlaceSumMap.put(team, teamPlaceSumMap.get(team).add(BigDecimal.valueOf(i + 1)));
                } else {
                    BigDecimal place = BigDecimal.valueOf(i + j + 2).divide(BigDecimal.valueOf(2));
                    for (int g = i; g <= j; g++) {
                        Team team = roundTeamResults.get(g).team;
                        teamPlaceSumMap.put(team, teamPlaceSumMap.get(team).add(place));
                    }
                }
                i = j + 1;
            }
        }

        Map<Team, RankingPoint> placeSumPointMap = new HashMap<Team, RankingPoint>();
        for (Map.Entry<Team, BigDecimal> teamPlaceSum : teamPlaceSumMap.entrySet()) {
            placeSumPointMap.put(teamPlaceSum.getKey(), new PlaceSumPoint(teamPlaceSum.getValue()));
        }
        return Collections.unmodifiableMap(placeSumPointMap);
    }

    @Override
    public String getPointName() {
        return "Сумма мест в турах";
    }
}
