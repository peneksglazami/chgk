package org.cherchgk.actions;

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
}
