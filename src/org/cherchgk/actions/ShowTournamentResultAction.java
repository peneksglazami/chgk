package org.cherchgk.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.TeamCategory;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TeamService;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Показать результаты турнира
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ShowTournamentResultAction extends ActionSupport {

    private TournamentService tournamentService;
    private TeamService teamService;
    private List<TeamResult> teamResults;
    private Tournament tournament;
    private TeamCategory teamCategory;

    public ShowTournamentResultAction(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @Override
    public String execute() throws Exception {
        long tournamentId = Long.valueOf(ActionContextHelper.getRequestParameterValue("tournamentId"));
        tournament = tournamentService.find(tournamentId);
        if (ActionContextHelper.getRequestParameterValue("teamCategoryId") != null) {
            long teamCategoryId = Long.valueOf(ActionContextHelper.getRequestParameterValue("teamCategoryId"));
            for (TeamCategory category : tournament.getTeamCategories()) {
                if (category.getId().equals(teamCategoryId)) {
                    teamCategory = category;
                    break;
                }
            }
        }
        List<Team> teams = tournament.getTeams();

        int[] questionsRanking = new int[tournament.getQuestionAmount()];
        for (int i = 0; i < tournament.getQuestionAmount(); i++) {
            questionsRanking[i] = teams.size() + 1;
        }

        int[] teamSum = new int[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            List<RightAnswer> rightAnswers = teamService.getTeamRightAnswers(teams.get(i));
            teamSum[i] = rightAnswers.size();
            for (RightAnswer rightAnswer : rightAnswers) {
                questionsRanking[rightAnswer.getQuestionNumber() - 1]--;
            }
        }

        teamResults = new ArrayList<TeamResult>();
        int[] teamRanking = new int[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            List<RightAnswer> rightAnswers = teamService.getTeamRightAnswers(teams.get(i));
            teamRanking[i] = 0;
            for (RightAnswer rightAnswer : rightAnswers) {
                teamRanking[i] += questionsRanking[rightAnswer.getQuestionNumber() - 1];
            }
            teamResults.add(new TeamResult(teams.get(i), teamSum[i], teamRanking[i]));
        }

        if (teamCategory != null) {
            List<TeamResult> filteredTeamResults = new ArrayList<TeamResult>();
            for (TeamResult teamResult : teamResults) {
                if (teamCategory.equals(teamResult.getTeam().getTeamCategory())) {
                    filteredTeamResults.add(teamResult);
                }
            }
            teamResults = filteredTeamResults;
        }

        Collections.sort(teamResults);

        int i = 0;
        while (i < teamResults.size()) {
            int j = i;
            while ((j < teamResults.size()) && (teamResults.get(i).compareTo(teamResults.get(j)) == 0)) {
                j++;
            }
            j--;
            if (i == j) {
                teamResults.get(i).setRank(String.valueOf(i + 1));
            } else {
                for (int g = i; g <= j; g++) {
                    teamResults.get(g).setRank(String.valueOf(i + 1) + "-" + String.valueOf(j + 1));
                }
            }
            i = j + 1;
        }

        return Action.SUCCESS;
    }

    public List<TeamResult> getTeamResults() {
        return teamResults;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public TeamCategory getTeamCategory() {
        return teamCategory;
    }
}