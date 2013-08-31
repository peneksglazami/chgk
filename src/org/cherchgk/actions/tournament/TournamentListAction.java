package org.cherchgk.actions.tournament;

import com.opensymphony.xwork2.Action;
import org.cherchgk.domain.Tournament;
import org.cherchgk.services.TournamentService;

import java.util.List;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class TournamentListAction implements Action {

    private TournamentService tournamentService;

    public TournamentListAction(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public List<Tournament> getTournaments() {
        return tournamentService.findAll();
    }

    public String execute() throws Exception {
        return Action.SUCCESS;
    }
}