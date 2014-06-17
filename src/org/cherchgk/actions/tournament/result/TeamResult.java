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

import org.cherchgk.domain.Team;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TeamResult implements Comparable<TeamResult> {
    private Team team;
    private int rightAnswers;
    private int ranking;
    private String rank;

    public TeamResult(Team team, int rightAnswers, int ranking) {
        this.team = team;
        this.rightAnswers = rightAnswers;
        this.ranking = ranking;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int compareTo(TeamResult o) {
        if (this.rightAnswers > o.rightAnswers) {
            return -1;
        }
        if ((this.rightAnswers == o.rightAnswers) && (this.ranking > o.ranking)) {
            return -1;
        }
        if ((this.rightAnswers == o.rightAnswers) && (this.ranking == o.ranking)) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamResult)) return false;

        TeamResult that = (TeamResult) o;

        if (ranking != that.ranking) return false;
        if (rightAnswers != that.rightAnswers) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rightAnswers;
        result = 31 * result + ranking;
        return result;
    }
}
