package org.cherchgk.actions.tournament;

import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие просмотра информации о турнире
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
}
