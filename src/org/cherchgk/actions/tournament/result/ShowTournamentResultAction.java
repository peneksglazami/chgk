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
import org.cherchgk.domain.TeamCategory;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Показать результаты турнира
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ShowTournamentResultAction extends ActionSupport {

    private TournamentService tournamentService;
    private Tournament tournament;
    private TeamCategory teamCategory;
    private TournamentResult tournamentResult;

    public ShowTournamentResultAction(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Override
    public String execute() throws Exception {
        long tournamentId = Long.parseLong(ActionContextHelper.getRequestParameterValue("tournamentId"));
        tournament = tournamentService.find(tournamentId);
        if (ActionContextHelper.getRequestParameterValue("teamCategoryId") != null) {
            long teamCategoryId = Long.parseLong(ActionContextHelper.getRequestParameterValue("teamCategoryId"));
            for (TeamCategory category : tournament.getTeamCategories()) {
                if (category.getId().equals(teamCategoryId)) {
                    teamCategory = category;
                    break;
                }
            }
        }

        tournamentResult = ResultUtils.getTournamentResult(tournament, teamCategory, tournamentService);
        return Action.SUCCESS;
    }

    public TournamentResult getTournamentResult() {
        return tournamentResult;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public TeamCategory getTeamCategory() {
        return teamCategory;
    }
}