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
package org.cherchgk.actions.security;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.cherchgk.utils.ActionContextHelper;

import java.net.URLDecoder;

/**
 * Действие аутентификации по логину и паролю.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class LoginAction extends ActionSupport {

    private String currentPage;
    private String loginError;

    @Override
    public String execute() throws Exception {
        String login = ActionContextHelper.getRequestParameterValue("login");
        String password = ActionContextHelper.getRequestParameterValue("password");
        UsernamePasswordToken token = new UsernamePasswordToken(login, password);
        token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (DisabledAccountException ex) {
            loginError = "LOCKED";
            return Action.ERROR;
        } catch (AuthenticationException ex) {
            loginError = "FAILED";
            return Action.ERROR;
        }
        currentPage = URLDecoder.decode(ActionContextHelper.getRequestParameterValue("currentPage"), "UTF-8");
        return Action.SUCCESS;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getLoginError() {
        return loginError;
    }
}
