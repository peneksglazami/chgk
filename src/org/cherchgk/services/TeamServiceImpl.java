package org.cherchgk.services;

import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.utils.ActionContextHelper;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TeamServiceImpl extends AbstractService<Team> implements TeamService {

    public Team find(Long id) {
        return entityManager.find(Team.class, id);
    }

    public List<Team> findAll() {
        Query query = entityManager.createQuery("select tournament from Tournament tournament");
        return query.getResultList();
    }

    @Override
    public void delete(Team team) {
        Query query = entityManager.createQuery("select answer from RightAnswer answer where answer.team.id = :teamId")
                .setParameter("teamId", team.getId());
        for (RightAnswer rightAnswer : (List<RightAnswer>) query.getResultList()) {
            entityManager.remove(rightAnswer);
        }
        entityManager.remove(team);
    }

    public List<RightAnswer> getTeamRightAnswers(Team team) {
        Query query = entityManager.createQuery("select answer from RightAnswer answer where answer.team = :team")
                .setParameter("team", team);
        return query.getResultList();
    }

    public void setAnswerVerdict(long teamId, int questionNumber, boolean verdict) {
        Query query = entityManager.createQuery("select answer from RightAnswer answer where answer.team.id = :teamId and answer.questionNumber = :questionNumber")
                .setParameter("teamId", teamId)
                .setParameter("questionNumber", questionNumber);
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
