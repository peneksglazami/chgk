/*
 * Copyright 2012-2019 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cherchgk.actions.team;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.TeamCategory;
import org.cherchgk.domain.Tournament;
import org.cherchgk.security.PermissionChecker;
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
        if (team.getId() == null) {
            PermissionChecker.checkPermissions("team:create");
        } else {
            PermissionChecker.checkPermissions("team:edit:" + team.getId());
        }
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