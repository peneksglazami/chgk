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
import org.cherchgk.domain.TeamCategory;
import org.cherchgk.domain.Tournament;

import java.util.List;

/**
 * Интерфейс сервиса для работы с хранилищем данных по
 * турнирам.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public interface TournamentService extends DataService<Tournament> {

    /**
     * Получение порядкового номера для создаваемой команды.
     *
     * @param tournamentId Идентификатор турнира.
     * @return Порядковый номер команды.
     */
    int getNextTeamNumber(long tournamentId);

    /**
     * Получить список всех правильных ответов, которые были
     * даны на турнире командами указанной категории.
     * Если категория команд не указана, то возвращается
     * список всех правильных ответов, данных на турнире.
     *
     * @param tournament   Турнир, для которого следует получить список
     *                     всех правильных ответов.
     * @param teamCategory Категория команд.
     * @return Список всех правильных ответов {@link org.cherchgk.domain.RightAnswer},
     * которые были даны на турнире для указанной категории команд.
     * Если категория команд не указана, то возвращается
     * список всех правильных ответов, данных на турнире.
     */
    List<RightAnswer> getAllRightAnswers(Tournament tournament, TeamCategory teamCategory);
}
