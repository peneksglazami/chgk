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
package org.cherchgk.utils;

import java.util.Properties;

/**
 * Класс для работы с настройками приложения, которые конфигурируются
 * через файл chgk.properties и параметры запуска сервера приложений.
 *
 * @author Andrey Grigorov
 */
public class ApplicationSettings {

    private static Properties properties;

    private ApplicationSettings() {
    }

    /**
     * Установка настроек из файла chgk.properties. Данный метод может вызываться
     * только из org.cherchgk.web.ApplicationSettingsLoaderListener.
     *
     * @param prop настройки из файла chgk.properties
     */
    public static void setProperties(Properties prop) {
        properties = prop;
    }

    public static boolean isDemoMode() {
        return Boolean.valueOf(properties.getProperty("demo-mode"));
    }
}