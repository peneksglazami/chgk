/*
 * Copyright 2012-2016 Andrey Grigorov, Anton Grigorov
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
package org.cherchgk.actions.tournament;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.Tournament;
import org.cherchgk.security.PermissionChecker;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие удаления турнира.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class DeleteTournamentAction extends ActionSupport {

    private TournamentService tournamentService;

    public DeleteTournamentAction(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Override
    public String execute() throws Exception {
        String tournamentId = ActionContextHelper.getRequestParameterValue("tournament.id");
        PermissionChecker.checkPermissions("tournament:edit:" + tournamentId);
        Tournament tournament = tournamentService.find(Long.valueOf(tournamentId));
        tournamentService.delete(tournament);
        return Action.SUCCESS;
    }
}
