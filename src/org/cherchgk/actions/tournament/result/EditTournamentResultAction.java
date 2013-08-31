package org.cherchgk.actions.tournament.result;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TeamService;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Действие открытия на редактирование результатов выполнения запроса
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class EditTournamentResultAction extends ActionSupport {

    private TournamentService tournamentService;
    private TeamService teamService;
    private Tournament tournament;

    public EditTournamentResultAction(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @Override
    public String execute() throws Exception {
        long tournamentId = Long.valueOf(ActionContextHelper.getRequestParameterValue("tournamentId"));
        tournament = tournamentService.find(tournamentId);

        return Action.SUCCESS;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public String getJsonResult() {
        StringBuilder array = new StringBuilder("{");
        List<Team> teams = tournament.getTeams();
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            array.append(team.getId()).append(":[");
            Set<Integer> rightAnswerNumbers = getRightAnswerNumberSet(teamService.getTeamRightAnswers(team));
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

    private Set<Integer> getRightAnswerNumberSet(List<RightAnswer> rightAnswers) {
        Set<Integer> numbers = new HashSet<Integer>();
        for (RightAnswer rightAnswer : rightAnswers) {
            numbers.add(rightAnswer.getQuestionNumber());
        }
        return numbers;
    }
}
