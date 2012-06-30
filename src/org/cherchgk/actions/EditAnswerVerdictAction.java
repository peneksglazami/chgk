package org.cherchgk.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.services.TeamService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие изменения результата ответа на вопрос
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class EditAnswerVerdictAction extends ActionSupport {

    private TeamService teamService;

    public EditAnswerVerdictAction(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public String execute() throws Exception {
        long teamId = Long.valueOf(ActionContextHelper.getRequestParameterValue("teamId"));
        int questionNumber = Integer.valueOf(ActionContextHelper.getRequestParameterValue("questionNumber"));
        int verdict = Integer.valueOf(ActionContextHelper.getRequestParameterValue("verdict"));
        teamService.setAnswerVerdict(teamId, questionNumber, verdict == 1);
        return Action.SUCCESS;
    }
}
