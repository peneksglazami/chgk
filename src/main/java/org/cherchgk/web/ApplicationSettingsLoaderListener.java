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
package org.cherchgk.web;

import org.cherchgk.utils.ApplicationSettings;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс, выполняющий начальную загрузку настроек приложения.
 * Осуществялется чтение конфигурационного файла WEB-INF/chgk.properties.
 *
 * @author Andrey Grigorov
 */
public class ApplicationSettingsLoaderListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = new Properties();
        try {
            properties.load(servletContextEvent.getServletContext().getResourceAsStream("/WEB-INF/chgk.properties"));
            ApplicationSettings.setProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
