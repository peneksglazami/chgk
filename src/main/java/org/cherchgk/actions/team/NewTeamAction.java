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
package org.cherchgk.actions.team;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

import java.util.Map;

/**
 * Действие подготовки к созданию новой комадны
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class NewTeamAction extends ActionSupport implements Preparable {

    private TournamentService tournamentService;
    private Tournament tournament;
    private Team team = new Team();

    public NewTeamAction(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public void prepare() throws Exception {
        long tournamentId = Long.parseLong(ActionContextHelper.getRequestParameterValue("tournamentId"));
        tournament = tournamentService.find(tournamentId);
        team.setNumber(tournamentService.getNextTeamNumber(tournamentId));
    }

    public Team getTeam() {
        return team;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Map<Long, String> getTeamCategories() {
        return tournament.getTeamCategoriesMap();
    }
}
