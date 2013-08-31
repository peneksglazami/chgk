package org.cherchgk.actions.team;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.Team;
import org.cherchgk.services.TeamService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class DeleteTeamAction extends ActionSupport {

    private TeamService teamService;
    private long tournamentId;

    public DeleteTeamAction(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public String execute() throws Exception {
        String teamId = ActionContextHelper.getRequestParameterValue("teamId");
        Team team = teamService.find(Long.valueOf(teamId));
        tournamentId = team.getTournament().getId();
        teamService.delete(team);
        return Action.SUCCESS;
    }

    public long getTournamentId() {
        return tournamentId;
    }
}
