/*
 * Copyright 2012-2019 Andrey Grigorov, Anton Grigorov
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
import org.cherchgk.domain.Team;

import java.util.List;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public interface TeamService extends DataService<Team> {

    List<RightAnswer> getTeamRightAnswers(Team team);

    void setAnswerVerdict(long teamId, int questionNumber, boolean verdict);
}
