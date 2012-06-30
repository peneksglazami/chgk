package org.cherchgk.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.Team;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие подготовки к созданию новой комадны
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class NewTeamAction extends ActionSupport implements Preparable {

    private TournamentService tournamentService;
    private Team team = new Team();

    public NewTeamAction(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public void prepare() throws Exception {
        long tournamentId = Long.valueOf(ActionContextHelper.getRequestParameterValue("tournamentId"));
        team.setNumber(tournamentService.getNextTeamNumber(tournamentId));
    }

    public Team getTeam() {
        return team;
    }
}
