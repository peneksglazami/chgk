package org.cherchgk.actions.team;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.TeamCategory;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TeamService;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

import java.util.Map;

/**
 * Действие создания и редактирования описания команды
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class EditTeamAction extends ActionSupport implements Preparable {

    private TournamentService tournamentService;
    private TeamService teamService;
    private Team team;
    private Tournament tournament;

    public EditTeamAction(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @Override
    public void validate() {
        if ("".equals(team.getName())) {
            addFieldError("team.name", "Не указано название команды");
        }
        for (Team t : tournament.getTeams()) {
            if (!t.getId().equals(team.getId()) && t.getName().equals(team.getName())) {
                addFieldError("team.name", "Команда с таким названием уже зарегистрирована на турнир");
            }
        }
        if (team.getNumber() == null) {
            addFieldError("team.number", "Не указан номер команды");
        } else if (team.getNumber() < 1) {
            addFieldError("team.number", "Номер команды должен быть больше нуля");
        } else {
            // проверим, что указанный номер ещё не занят
            for (Team t : tournament.getTeams()) {
                if (!t.getId().equals(team.getId()) && team.getNumber().equals(t.getNumber())) {
                    addFieldError("team.number", "Такой номер уже присвоен другой команде");
                    break;
                }
            }
        }
    }

    public void prepare() throws Exception {
        String teamId = ActionContextHelper.getRequestParameterValue("team.id");
        if ((teamId != null) && !"".equals(teamId)) {
            team = teamService.find(Long.valueOf(teamId));
        }
        Long tournamentId = Long.valueOf(ActionContextHelper.getRequestParameterValue("tournament.id"));
        tournament = tournamentService.find(tournamentId);
    }

    public String save() {
        synchronized (EditTeamAction.class) {
            if (team.getTournament() == null) {
                team.setTournament(tournament);
                tournament.getTeams().add(team);
            }
            teamService.save(team);
        }
        return Action.SUCCESS;
    }

    public String delete() {
        teamService.delete(team);
        return Action.SUCCESS;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTeamCategory(Long teamCategoryId) {
        for (TeamCategory category : tournament.getTeamCategories()) {
            if (category.getId().equals(teamCategoryId)) {
                team.setTeamCategory(category);
                break;
            }
        }
    }

    public Map<Long, String> getTeamCategories() {
        return tournament.getTeamCategoriesMap();
    }
}