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
package org.cherchgk.utils;

import org.cherchgk.services.SettingsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Класс, выполняющий инициализацию настроек системы на основе
 * значений переменных окружения.
 *
 * @author Andrey Grigorov
 */
public class ApplicationSettingsInitBean  implements InitializingBean {

    @Autowired
    private SettingsService settingsService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (System.getProperty("mailServerHostName") != null) {
            settingsService.saveMailServerHostName(System.getProperty("mailServerHostName"));
        }
        if (System.getProperty("mailServerPort") != null) {
            settingsService.saveMailServerPort(System.getProperty("mailServerPort"));
        }
        if (System.getProperty("mailServerUser") != null) {
            settingsService.saveMailServerUser(System.getProperty("mailServerUser"));
        }
        if (System.getProperty("mailServerPassword") != null) {
            settingsService.saveMailServerPassword(System.getProperty("mailServerPassword"));
        }
        if (System.getProperty("hostName") != null) {
            settingsService.saveHostName(System.getProperty("hostName"));
        }
    }
}
