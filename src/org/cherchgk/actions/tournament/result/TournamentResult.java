/*
 * Copyright 2012-2015 Andrey Grigorov, Anton Grigorov
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
import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Результаты турнира.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TournamentResult {

    public static class TeamResult {
        private Team team;
        private String place;

        public TeamResult(Team team, String place) {
            this.team = team;
            this.place = place;
        }

        public Team getTeam() {
            return team;
        }

        public String getPlace() {
            return place;
        }
    }

    private Tournament tournament;
    private Collection<RightAnswer> rightAnswers;
    private List<TeamResult> teamResultList;
    private List<Map<Team, RankingPoint>> rankingPointsList;
    private List<RankingAlgorithm> rankingAlgorithms;

    public TournamentResult(Tournament tournament, Collection<RightAnswer> rightAnswers,
                            List<TeamResult> teamResultList, List<Map<Team, RankingPoint>> rankingPointsList,
                            List<RankingAlgorithm> rankingAlgorithms) {
        this.tournament = tournament;
        this.rightAnswers = rightAnswers;
        this.teamResultList = teamResultList;
        this.rankingPointsList = rankingPointsList;
        this.rankingAlgorithms = rankingAlgorithms;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Collection<RightAnswer> getRightAnswers() {
        return rightAnswers;
    }

    public List<TeamResult> getTeamResultList() {
        return teamResultList;
    }

    public List<Map<Team, RankingPoint>> getRankingPointsList() {
        return rankingPointsList;
    }

    public List<RankingAlgorithm> getRankingAlgorithms() {
        return rankingAlgorithms;
    }
}
