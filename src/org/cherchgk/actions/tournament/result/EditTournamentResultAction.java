/*
 * Copyright 2012-2014 Andrey Grigorov, Anton Grigorov
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
package org.cherchgk.actions.tournament.result;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.Tournament;
import org.cherchgk.security.PermissionChecker;
import org.cherchgk.services.TeamService;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие открытия на редактирование результатов турнира.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class EditTournamentResultAction extends ActionSupport {

    private TournamentService tournamentService;
    private TeamService teamService;
    private Tournament tournament;

    public EditTournamentResultAction(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @Override
    public String execute() throws Exception {
        long tournamentId = Long.valueOf(ActionContextHelper.getRequestParameterValue("tournamentId"));
        PermissionChecker.checkPermissions("tournament:edit:" + tournamentId);
        tournament = tournamentService.find(tournamentId);

        return Action.SUCCESS;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public String getJsonResult() {
        return ResultUtils.getJSONResult(tournament, teamService);
    }
}
