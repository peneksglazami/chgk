/*
 * Copyright 2012-2015 Andrey Grigorov, Anton Grigorov
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
package org.cherchgk.actions.tournament.result.show;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.actions.tournament.result.ResultUtils;
import org.cherchgk.actions.tournament.result.TournamentResult;
import org.cherchgk.domain.TeamCategory;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Базовый класс для действий отображения результатов турниров.
 * Классы наследники должны использовать результаты турнира,
 * которые сохраняются в методе {@link BaseShowTournamentResultAction#execute()}
 * в свойство {@link BaseShowTournamentResultAction#tournamentResult}.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public abstract class BaseShowTournamentResultAction extends ActionSupport {

    protected TournamentService tournamentService;
    protected Tournament tournament;
    protected TeamCategory teamCategory;
    protected TournamentResult tournamentResult;

    protected BaseShowTournamentResultAction(TournamentService tournamentService) {
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
}
