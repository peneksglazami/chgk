/*
 * Copyright 2012-2016 Andrey Grigorov, Anton Grigorov
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
package org.cherchgk.actions.tournament.result.ranking;

import org.cherchgk.domain.RightAnswer;
import org.cherchgk.domain.Team;
import org.cherchgk.domain.Tournament;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Алгоритм ранжирования, который всем командам устанавливает
 * одинаковый балл.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class DummyAlgorithm implements RankingAlgorithm {

    private static class DummyPoint extends RankingPoint<DummyPoint> {

        @Override
        public int compareTo(DummyPoint o) {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return (this == o) || !((o == null) || (getClass() != o.getClass()));
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "";
        }
    }

    @Override
    public Map<Team, RankingPoint> getRankingPoints(Tournament tournament, Collection<Team> teams,
                                                    Collection<RightAnswer> rightAnswers) {
        Map<Team, RankingPoint> rankingPointMap = new HashMap<Team, RankingPoint>();
        for (Team team : teams) {
            rankingPointMap.put(team, new DummyPoint());
        }
        return Collections.unmodifiableMap(rankingPointMap);
    }

    @Override
    public String getPointName() {
        return "";
    }
}
