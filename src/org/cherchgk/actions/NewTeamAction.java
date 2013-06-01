package org.cherchgk.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

import java.util.Map;

/**
 * Действие подготовки к созданию новой комадны
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class NewTeamAction extends ActionSupport implements Preparable {

    private TournamentService tournamentService;
    private Tournament tournament;
    private Team team = new Team();

    public NewTeamAction(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public void prepare() throws Exception {
        long tournamentId = Long.valueOf(ActionContextHelper.getRequestParameterValue("tournamentId"));
        tournament = tournamentService.find(tournamentId);
        team.setNumber(tournamentService.getNextTeamNumber(tournamentId));
    }

    public Team getTeam() {
        return team;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Map<Long, String> getTeamCategories() {
        return tournament.getTeamCategoriesMap();
    }
}
