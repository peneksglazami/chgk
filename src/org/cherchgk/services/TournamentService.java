package org.cherchgk.services;

import org.cherchgk.domain.Tournament;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public interface TournamentService extends DataService<Tournament> {

    int getNextTeamNumber(long tournamentId);
}
