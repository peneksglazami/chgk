package org.cherchgk.actions.tournament.result;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.atmosphere.cpr.BroadcasterFactory;
import org.cherchgk.domain.Team;
import org.cherchgk.security.PermissionChecker;
import org.cherchgk.services.TeamService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие изменения результата ответа на вопрос.
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
        Team team = teamService.find(teamId);
        String tournamentId = team.getTournament().getId().toString();
        PermissionChecker.checkPermissions("tournament:edit:" + tournamentId);
        int questionNumber = Integer.valueOf(ActionContextHelper.getRequestParameterValue("questionNumber"));
        int verdict = Integer.valueOf(ActionContextHelper.getRequestParameterValue("verdict"));
        teamService.setAnswerVerdict(teamId, questionNumber, verdict == 1);
        BroadcasterFactory.getDefault().lookup(tournamentId, true)
                .broadcast(ResultUtils.getJSONResult(team.getTournament(), teamService));
        return Action.SUCCESS;
    }
}
