/*
 * Copyright 2012-2018 Andrey Grigorov, Anton Grigorov
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

import org.cherchgk.actions.tournament.result.TournamentResult;
import org.cherchgk.domain.TeamCategory;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;

/**
 * Показать результаты турнира на html странице.
 * Смотрите .../web/tournament-result.jsp.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ShowTournamentResultAction extends BaseShowTournamentResultAction {

    public ShowTournamentResultAction(TournamentService tournamentService) {
        super(tournamentService);
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