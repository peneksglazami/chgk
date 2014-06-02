package org.cherchgk.actions.tournament;

import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.Tournament;

/**
 * Действие подготовки к созданию нового турнира.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class NewTournamentAction extends ActionSupport {

    public Tournament.QuestionNumberingType[] getQuestionNumberingTypes() {
        return Tournament.QuestionNumberingType.values();
    }
}
