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