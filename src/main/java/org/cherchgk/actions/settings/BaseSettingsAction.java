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
 * Базовый класс для операций со страницей редактирования настроек приложения..
 *
 * @author Andrey Grigorov
 */
public class BaseSettingsAction extends ActionSupport {

    protected SettingsService settingsService;
    protected String mailServerHostName;
    protected String mailServerPort;
    protected String mailServerUser;
    protected String mailServerPassword;

    public String getMailServerHostName() {
        return mailServerHostName;
    }

    public void setMailServerHostName(String mailServerHostName) {
        this.mailServerHostName = mailServerHostName;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public String getMailServerUser() {
        return mailServerUser;
    }

    public void setMailServerUser(String mailServerUser) {
        this.mailServerUser = mailServerUser;
    }

    public String getMailServerPassword() {
        return mailServerPassword;
    }

    public void setMailServerPassword(String mailServerPassword) {
        String password = settingsService.getMailServerPassword();
        if (password == null) {
            this.mailServerPassword = mailServerPassword;
        } else {
            String passwordHash = new Sha512Hash(password, SecurityUtils.getSubject().getPrincipal().toString()).toHex();
            if (passwordHash.substring(0, 7).equals(mailServerPassword)) {
                this.mailServerPassword = password;
            } else {
                this.mailServerPassword = mailServerPassword;
            }
        }
    }
}
