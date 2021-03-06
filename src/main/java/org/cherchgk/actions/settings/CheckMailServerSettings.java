/*
 * Copyright 2012-2019 Andrey Grigorov, Anton Grigorov
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
import org.cherchgk.services.MailService;
import org.cherchgk.services.SettingsService;

/**
 * Действие проверки возможности отправки электронных писем
 * с указанными параметрами подключения к почтовому серверу.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class CheckMailServerSettings extends BaseSettingsAction {

    private MailService mailService;
    private boolean canSendEmail;

    public CheckMailServerSettings(MailService mailService, SettingsService settingsService) {
        this.mailService = mailService;
        this.settingsService = settingsService;
    }

    @Override
    public String execute() throws Exception {
        canSendEmail = mailService.checkMailSending(mailServerHostName, mailServerPort,
                mailServerUser, mailServerPassword);
        return ActionSupport.SUCCESS;
    }

    public boolean isCanSendEmail() {
        return canSendEmail;
    }
}
