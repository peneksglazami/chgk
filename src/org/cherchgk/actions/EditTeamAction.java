package org.cherchgk.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TeamService;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие создания и редактирования описания команды
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class EditTeamAction extends ActionSupport implements Preparable {

    private TournamentService tournamentService;
    private TeamService teamService;
    private Team team;
    private Long tournamentId;

    public EditTeamAction(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @Override
    public void validate() {
        long tournamentId = (team.getTournament() == null) ? Long.valueOf(ActionContextHelper.getRequestParameterValue("tournamentId")) : team.getTournament().getId();
        Tournament tournament = tournamentService.find(tournamentId);
        if ("".equals(team.getName())) {
            addFieldError("team.name", "Не указано название команды");
        }
        for (Team t : tournament.getTeams()) {
            if (!t.getId().equals(team.getId()) && t.getName().equals(team.getName())) {
                addFieldError("team.name", "Команда с таким названием уже зарегистрирована на турнир");
            }
        }
        if (team.getNumber() == null) {
            addFieldError("team.number", "Не указано номер команды");
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
    }

    public String save() {
        synchronized (EditTeamAction.class) {
            if (team.getTournament() == null) {
                tournamentId = Long.valueOf(ActionContextHelper.getRequestParameterValue("tournamentId"));
                Tournament tournament = tournamentService.find(tournamentId);
                team.setTournament(tournament);
                tournament.getTeams().add(team);
            } else {
                tournamentId = team.getTournament().getId();
            }
            teamService.save(team);
        }
        return Action.SUCCESS;
    }

    public String delete() {
        teamService.delete(team);
        return Action.SUCCESS;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }
}