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
package org.cherchgk.services;

import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Сервис для работы с командами.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TeamServiceImpl extends AbstractService<Team> implements TeamService {

    public Team find(Long id) {
        return entityManager.find(Team.class, id);
    }

    public List<Team> findAll() {
        TypedQuery<Team> query = entityManager.createQuery("select tournament from Tournament tournament", Team.class);
        query.setHint("org.hibernate.cacheable", true);
        return query.getResultList();
    }

    @Override
    public void delete(Team team) {
        Tournament tournament = team.getTournament();
        tournament.getTeams().remove(team);
        entityManager.merge(tournament);
    }

    public List<RightAnswer> getTeamRightAnswers(Team team) {
        TypedQuery<RightAnswer> query = entityManager.createQuery("select answer "
                + "from RightAnswer answer "
                + "where answer.team = :team", RightAnswer.class)
                .setParameter("team", team);
        query.setHint("org.hibernate.cacheable", true);
        return query.getResultList();
    }

    public void setAnswerVerdict(long teamId, int questionNumber, boolean verdict) {
        TypedQuery<RightAnswer> query = entityManager.createQuery("select answer "
                + "from RightAnswer answer "
                + "where answer.team.id = :teamId and answer.questionNumber = :questionNumber", RightAnswer.class)
                .setParameter("teamId", teamId)
                .setParameter("questionNumber", questionNumber);
        query.setHint("org.hibernate.cacheable", true);
        List<RightAnswer> rightAnswers = query.getResultList();
        RightAnswer rightAnswer = null;
        if (rightAnswers.size() == 1) {
            rightAnswer = rightAnswers.get(0);
        }
        if (verdict) {
            if (rightAnswer == null) {
                Team team = find(teamId);
                rightAnswer = new RightAnswer();
                rightAnswer.setTeam(team);
                rightAnswer.setQuestionNumber(questionNumber);
                entityManager.persist(rightAnswer);
            }
        } else {
            if (rightAnswer != null) {
                entityManager.remove(rightAnswer);
            }
        }
    }
}
