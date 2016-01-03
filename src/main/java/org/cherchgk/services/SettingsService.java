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
package org.cherchgk.services;

import org.cherchgk.domain.settings.Option;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Сервис для работы с настройками приложения.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Transactional(rollbackFor = Throwable.class)
public class SettingsService {

    private final static String mailServerHostNameOption = "mail.server.host.name";
    private final static String mailServerPortOption = "mail.server.port";
    private final static String mailServerUserOption = "mail.server.user";
    private final static String mailServerPasswordOption = "mail.server.password";
    private final static String hostNameOption = "host.name";

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Получение значения настройки по имени.
     *
     * @param name Имя настройки.
     * @return Значение настройки или null, если настройки
     * с данным именем не найдено.
     */
    private String getOptionValue(String name) {
        Option option = entityManager.find(Option.class, name);
        if (option != null) {
            return option.getValue();
        }
        return null;
    }

    /**
     * Сохранение значения настройки.
     *
     * @param name  Имя настройки.
     * @param value Значение настройки.
     */
    private void saveOptionValue(String name, String value) {
        Option option = entityManager.find(Option.class, name);
        if (option != null) {
            option.setValue(value);
            entityManager.merge(option);
        } else {
            option = new Option(name, value);
            entityManager.persist(option);
        }
    }

    /**
     * Получить имя хоста почтового сервера, через который
     * будет выполнятся отправка электронных писем.
     *
     * @return Имя хоста почтового сервера.
     */
    public String getMailServerHostName() {
        return getOptionValue(mailServerHostNameOption);
    }


    /**
     * Сохранение имени хоста почтового сервера.
     *
     * @param mailServerHostName Имя хоста почтового сервера.
     */
    public void saveMailServerHostName(String mailServerHostName) {
        saveOptionValue(mailServerHostNameOption, mailServerHostName);
    }

    /**
     * Получить порт почтового сервера, через который
     * будет выполнятся отправка электронных писем.
     *
     * @return Порт почтового сервера.
     */
    public String getMailServerPort() {
        return getOptionValue(mailServerPortOption);
    }

    /**
     * Сохранение порта почтового сервера.
     *
     * @param mailServerPort Порт почтового сервера.
     */
    public void saveMailServerPort(String mailServerPort) {
        saveOptionValue(mailServerPortOption, mailServerPort);
    }

    /**
     * Получить имя пользователя почтового сервера.
     *
     * @return Имя пользователя почтового сервера.
     */
    public String getMailServerUser() {
        return getOptionValue(mailServerUserOption);
    }

    /**
     * Сохранение имени пользователя почтового сервера.
     *
     * @param mailServerUser Имя пользователя почтового сервера.
     */
    public void saveMailServerUser(String mailServerUser) {
        saveOptionValue(mailServerUserOption, mailServerUser);
    }

    /**
     * Получить пароль к учётной записи почтового сервера.
     *
     * @return Пароль к учётной записи почтового сервера.
     */
    public String getMailServerPassword() {
        return getOptionValue(mailServerPasswordOption);
    }

    /**
     * Сохранение пароля к учётной записи почтового сервера.
     *
     * @param mailServerPassword Пароль к учётной записи почтового сервера.
     */
    public void saveMailServerPassword(String mailServerPassword) {
        saveOptionValue(mailServerPasswordOption, mailServerPassword);
    }

    /**
     * Получить имя хоста, на котором запущено приложение.
     * Данное имя будет использоваться при формировании
     * ссылок, которые будут указываться в отправляемых
     * электронных письмах.
     *
     * @return Имя хоста, на котором запущего
     */
    public String getHostName() {
        return getOptionValue(hostNameOption);
    }

    /**
     * Сохранить имя хоста, на котором запущено приложение.
     *
     * @param hostName Имя хоста, на котором запущено приложение.
     */
    public void saveHostName(String hostName) {
        saveOptionValue(hostNameOption, hostName);
    }
}
