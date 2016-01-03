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
package org.cherchgk;

import org.apache.struts2.StrutsSpringTestCase;

/**
 * Базовый класс для всех юнит-тестов. Выполняет инициализацию тестового контекста.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public abstract class BaseStrutsSpringTestCase extends StrutsSpringTestCase {

    @Override
    protected String[] getContextLocations() {
        return new String[]{"/WEB-INF/applicationContext.xml"};
    }

    @Override
    public void setUp() throws Exception {
        // тесты будут работать с БД, загружаемой в память
        System.setProperty("jdbc.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.showSql", "false");
        super.setUp();
    }
}
