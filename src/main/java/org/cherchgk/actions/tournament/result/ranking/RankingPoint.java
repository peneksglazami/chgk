/*
 * Copyright 2012-2015 Andrey Grigorov, Anton Grigorov
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

/**
 * Рейтинговый балл.
 * Метод {@link #compareTo(Object)} должен возвращать значение больше 0, если
 * команда, которой установлен данный балл, должна занять более высокое место
 * по сравнению с командой, балл которой передаётся в качестве параметра метода.
 * Если команды должны поделить место, то метод {@link #compareTo(Object)}
 * должен возвратить значение равное 0.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public abstract class RankingPoint<T extends RankingPoint<T>> implements Comparable<T> {
}
