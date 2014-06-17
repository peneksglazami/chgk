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
package org.cherchgk.services;

import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TournamentServiceImpl extends AbstractService<Tournament> implements TournamentService {

    private TeamService teamService;

    public TournamentServiceImpl(TeamService teamService) {
        this.teamService = teamService;
    }

    public Tournament find(Long id) {
        return entityManager.find(Tournament.class, id);
    }

    public List<Tournament> findAll() {
        Query query = entityManager.createQuery("select tournament from Tournament tournament");
        query.setHint("org.hibernate.cacheable", true);
        return query.getResultList();
    }

    public void delete(Tournament tournament) {
        for (Team team : tournament.getTeams()) {
            teamService.delete(team);
        }
        entityManager.remove(tournament);
    }

    public int getNextTeamNumber(long tournamentId) {
        //FIXME: возможно следует переделать механизм определения номера команды
        Tournament tournament = find(tournamentId);
        Integer maxNumber = (Integer) entityManager.createQuery("select max(team.number) from Team team where team.tournament = :tournament")
                .setParameter("tournament", tournament).getSingleResult();
        return maxNumber == null ? 1 : maxNumber + 1;
    }
}