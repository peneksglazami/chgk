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
package org.cherchgk.actions.settings;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.cherchgk.services.SettingsService;

/**
 * Действие отображения страницы с настройками приложения.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class SettingsAction extends ActionSupport {

    private SettingsService settingsService;

    public SettingsAction(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public String getMailServerHostName() {
        return settingsService.getMailServerHostName();
    }

    public String getMailServerPort() {
        return settingsService.getMailServerPort();
    }

    public String getMailServerUser() {
        return settingsService.getMailServerUser();
    }

    public String getMailServerPassword() {
        String password = settingsService.getMailServerPassword();
        if (password == null) {
            return null;
        }
        String passwordHash = new Sha512Hash(password, SecurityUtils.getSubject().getPrincipal().toString()).toHex();
        return passwordHash.substring(0, 7);
    }

    public String getHostName() {
        return settingsService.getHostName();
    }
}