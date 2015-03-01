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
package org.cherchgk.actions.security;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.validator.routines.EmailValidator;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Действие регистрации нового пользователя.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class SignUpAction extends ActionSupport {

    private SecurityService securityService;
    
    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public String execute() throws Exception {
        String login = ActionContextHelper.getRequestParameterValue("login");
        String email = ActionContextHelper.getRequestParameterValue("email");
        String password = ActionContextHelper.getRequestParameterValue("password");
        String password2 = ActionContextHelper.getRequestParameterValue("password2");

        boolean validationResult = true;

        if ((login == null) || "".equals(login)) {
            addActionError("Логин не может быть пустым");
            validationResult = false;
        } else {
            if (securityService.getUserByName(login) != null) {
                addActionError("Пользователь с данным логином уже существует");
                validationResult = false;
            }
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            addActionError("Указан некорректный адрес электронной почты");
            validationResult = false;
        } else {
            if (securityService.getUserByEmail(email) != null) {
                addActionError("Пользователь с таким адресом электронной почты уже зарегистрирован");
                validationResult = false;
            }
        }

        if ((password == null) || "".equals(password)) {
            addActionError("Пароль не может быть пустым");
            validationResult = false;
        }

        if ((password != null) && !password.equals(password2)) {
            addActionError("Пароль и повторно введённый пароль не совпадают");
            validationResult = false;
        }

        if (!validationResult) {
            return Action.ERROR;
        }

        try {
            securityService.registerNewUser(login, password, email);
            addActionMessage("На ящик вашей электронной почты отправлено письмо для подтверждения активации.");
        } catch (Exception ex) {
            addActionError("Регистрация временно недоступна.");
            return Action.ERROR;
        }

        return Action.SUCCESS;
    }

    @Override
    public Collection<String> getActionErrors() {
        return super.getActionErrors();
    }

    @Override
    public Collection<String> getActionMessages() {
        return super.getActionMessages();
    }
}