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
import org.cherchgk.domain.Team;
import org.cherchgk.security.PermissionChecker;
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
        PermissionChecker.checkPermissions("team:edit:" + teamId);
        Team team = teamService.find(Long.valueOf(teamId));
        tournamentId = team.getTournament().getId();
        teamService.delete(team);
        return Action.SUCCESS;
    }

    public long getTournamentId() {
        return tournamentId;
    }
}
