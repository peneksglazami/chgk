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
        long teamId = Long.parseLong(ActionContextHelper.getRequestParameterValue("teamId"));
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
