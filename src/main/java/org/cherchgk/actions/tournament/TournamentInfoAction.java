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
package org.cherchgk.actions.tournament;

import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие просмотра информации о турнире.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TournamentInfoAction extends ActionSupport {

    private TournamentService tournamentService;
    private Tournament tournament;

    public TournamentInfoAction(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public Tournament getTournament() {
        if (tournament == null) {
            long tournamentId = Long.parseLong(ActionContextHelper.getRequestParameterValue("tournamentId"));
            tournament = tournamentService.find(tournamentId);
        }
        return tournament;
    }

    public Tournament.QuestionNumberingType[] getQuestionNumberingTypes() {
        return Tournament.QuestionNumberingType.values();
    }

    public Tournament.RankingMethod[] getRankingMethods() {
        return Tournament.RankingMethod.values();
    }
}
