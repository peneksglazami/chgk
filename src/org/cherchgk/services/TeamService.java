package org.cherchgk.services;

import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;

import java.util.List;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public interface TeamService extends DataService<Team> {

    List<RightAnswer> getTeamRightAnswers(Team team);

    void setAnswerVerdict(long teamId, int questionNumber, boolean verdict);
}
