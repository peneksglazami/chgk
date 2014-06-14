package org.cherchgk.actions.tournament.result;

import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TeamService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Набор утилитарных методов для работы со страницей редактирования
 * результатов турнира.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ResultUtils {

    /**
     * Подготовка информации о результатах турнира в формате JSON.
     *
     * @param tournament  турнир
     * @param teamService сервис работы с командами
     * @return JSON-объект с результатами турнира
     */
    public static String getJSONResult(Tournament tournament, TeamService teamService) {
        StringBuilder array = new StringBuilder("{");
        List<Team> teams = tournament.getTeams();
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            array.append("\"").append(team.getId()).append("\":[");
            List<RightAnswer> rightAnswers = teamService.getTeamRightAnswers(team);
            Set<Integer> rightAnswerNumbers = new HashSet<Integer>();
            for (RightAnswer rightAnswer : rightAnswers) {
                rightAnswerNumbers.add(rightAnswer.getQuestionNumber());
            }
            for (int j = 1; j <= tournament.getQuestionAmount(); j++) {
                array.append(rightAnswerNumbers.contains(j) ? "1" : "0");
                if (j != tournament.getQuestionAmount()) {
                    array.append(",");
                }
            }
            array.append("]");
            if (i != (teams.size() - 1)) {
                array.append(",");
            }
        }
        array.append("}");
        return array.toString();
    }
}
