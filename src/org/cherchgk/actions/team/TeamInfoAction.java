package org.cherchgk.actions.team;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TeamService;
import org.cherchgk.utils.ActionContextHelper;

import java.util.Map;

/**
 * Действие получения информации по команде
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TeamInfoAction extends ActionSupport implements Preparable {

    private TeamService teamService;
    private Team team;

    public TeamInfoAction(TeamService teamService) {
        this.teamService = teamService;
    }

    public void prepare() throws Exception {
        long teamId = Long.valueOf(ActionContextHelper.getRequestParameterValue("teamId"));
        team = teamService.find(teamId);
    }

    public Team getTeam() {
        return team;
    }

    public long tournamentId() {
        return team.getTournament().getId();
    }

    public Tournament getTournament() {
        return team.getTournament();
    }

    public Map<Long, String> getTeamCategories() {
        return team.getTournament().getTeamCategoriesMap();
    }
}
