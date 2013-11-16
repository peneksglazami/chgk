package org.cherchgk.actions.tournament;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.Tournament;
import org.cherchgk.security.PermissionChecker;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие удаления турнира.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class DeleteTournamentAction extends ActionSupport {

    private TournamentService tournamentService;

    public DeleteTournamentAction(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Override
    public String execute() throws Exception {
        String tournamentId = ActionContextHelper.getRequestParameterValue("tournament.id");
        PermissionChecker.checkPermissions("tournament:delete:" + tournamentId);
        Tournament tournament = tournamentService.find(Long.valueOf(tournamentId));
        tournamentService.delete(tournament);
        return Action.SUCCESS;
    }
}
