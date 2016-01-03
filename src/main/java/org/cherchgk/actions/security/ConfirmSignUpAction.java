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
package org.cherchgk.actions.security;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие подтверждения регистрации пользователя.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ConfirmSignUpAction extends ActionSupport {

    private SecurityService securityService;
    private String message;

    public ConfirmSignUpAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public String execute() throws Exception {
        String tokenUUID = ActionContextHelper.getRequestParameterValue("tokenUUID");

        if ((tokenUUID == null) || !securityService.confirmRegistration(tokenUUID)) {
            message = "Подтверждение регистрации учётной записи не выполнено.";
            return Action.ERROR;
        }

        message = "Регистрация учётной записи подтвержена. Добро пожаловать!";
        return Action.SUCCESS;
    }

    public String getMessage() {
        return message;
    }
}