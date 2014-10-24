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
import org.apache.commons.validator.routines.EmailValidator;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;
import org.cherchgk.utils.EntityManagerProvider;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.net.URLDecoder;

/**
 * Действие регистрации нового пользователя.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class SingUpAction extends ActionSupport {

    @Autowired
    private SecurityService securityService;
    private String currentPage;

    @Override
    public String execute() throws Exception {
        String login = ActionContextHelper.getRequestParameterValue("login");
        String email = ActionContextHelper.getRequestParameterValue("email");
        String password = ActionContextHelper.getRequestParameterValue("password");
        String password2 = ActionContextHelper.getRequestParameterValue("password2");

        if (securityService.getUserByName(login) != null) {
            return Action.ERROR;
        }

        if (password == null) {
            return Action.ERROR;
        }

        if (!password.equals(password2)) {
            return Action.ERROR;
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            return Action.ERROR;
        }

        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            securityService.createUserIfNotExist(login, password, "organizer");

            // TODO: send email
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        }

        currentPage = URLDecoder.decode(ActionContextHelper.getRequestParameterValue("currentPage"), "UTF-8");
        return Action.SUCCESS;
    }

    public String getCurrentPage() {
        return currentPage;
    }
}
