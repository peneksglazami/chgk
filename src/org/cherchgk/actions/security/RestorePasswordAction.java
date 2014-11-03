/*
 * Copyright 2012-2014 Andrey Grigorov, Anton Grigorov
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
import org.cherchgk.domain.security.User;
import org.cherchgk.services.SecurityService;

import javax.mail.MessagingException;

/**
 * Действие восстановления пароля по логину или e-mail'у.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class RestorePasswordAction extends ActionSupport {

    private final static String successMessage = "На адрес вашей электронной почты отправлена инструкция по " +
            "восстановлению пароля.";
    private final static String userNotFound = "Пользователь с указанным логином или адресом электронной " +
            "почты не зарегистрирован в системе.";
    private final static String mailSendingFailure = "Не удалось выполнить отправку письма с интрукцией по" +
            "восстановлению пароля. Попробуйте повторить попытку позже.";

    private SecurityService securityService;
    private String loginOrEmail;
    private String message;
    private boolean restoreResult;

    public RestorePasswordAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public String execute() throws Exception {
        restoreResult = false;
        User user = securityService.getUserByName(loginOrEmail);
        if (user == null) {
            user = securityService.getUserByEmail(loginOrEmail);
            if (user == null) {
                message = userNotFound;
                return Action.SUCCESS;
            }
        } else if (user.getEmail() == null) {
            message = userNotFound;
            return Action.SUCCESS;
        }

        try {
            securityService.restorePassword(user);
            restoreResult = true;
            message = successMessage;
        } catch (MessagingException ex) {
            message = mailSendingFailure;
        }

        return Action.SUCCESS;
    }

    public void setLoginOrEmail(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRestoreResult() {
        return restoreResult;
    }
}
