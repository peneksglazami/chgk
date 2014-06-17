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
import org.atmosphere.cpr.BroadcasterFactory;
import org.cherchgk.domain.Team;
import org.cherchgk.security.PermissionChecker;
import org.cherchgk.services.TeamService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие изменения результата ответа на вопрос.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class EditAnswerVerdictAction extends ActionSupport {

    private TeamService teamService;

    public EditAnswerVerdictAction(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public String execute() throws Exception {
        long teamId = Long.valueOf(ActionContextHelper.getRequestParameterValue("teamId"));
        Team team = teamService.find(teamId);
        String tournamentId = team.getTournament().getId().toString();
        PermissionChecker.checkPermissions("tournament:edit:" + tournamentId);
        int questionNumber = Integer.valueOf(ActionContextHelper.getRequestParameterValue("questionNumber"));
        int verdict = Integer.valueOf(ActionContextHelper.getRequestParameterValue("verdict"));
        teamService.setAnswerVerdict(teamId, questionNumber, verdict == 1);
        BroadcasterFactory.getDefault().lookup(tournamentId, true)
                .broadcast(ResultUtils.getJSONResult(team.getTournament(), teamService));
        return Action.SUCCESS;
    }
}
