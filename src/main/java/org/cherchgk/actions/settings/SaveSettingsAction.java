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

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.services.SettingsService;

/**
 * Действие сохранения настроек приложения.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class SaveSettingsAction extends ActionSupport {

    private SettingsService settingsService;
    private String mailServerHostName;
    private String mailServerPort;
    private String mailServerUser;
    private String mailServerPassword;
    private String hostName;

    public SaveSettingsAction(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public String execute() throws Exception {
        settingsService.saveMailServerHostName(mailServerHostName);
        settingsService.saveMailServerPort(mailServerPort);
        settingsService.saveMailServerUser(mailServerUser);
        settingsService.saveMailServerPassword(mailServerPassword);
        settingsService.saveHostName(hostName);
        return Action.SUCCESS;
    }

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
        this.mailServerPassword = mailServerPassword;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
