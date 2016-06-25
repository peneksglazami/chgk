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
import org.cherchgk.services.SettingsService;

/**
 * Действие сохранения настроек приложения.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class SaveSettingsAction extends BaseSettingsAction {

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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
