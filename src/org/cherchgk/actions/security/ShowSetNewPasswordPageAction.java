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
import org.cherchgk.domain.security.Token;
import org.cherchgk.services.SecurityService;

/**
 * Действие открытия страницы установки нового пароля.
 * Используется при выполнении операции восстановления пароля.
 * Идентификатор токена передаётся на страницу установки пароля
 * для того, чтобы далее быть переданным в качестве параметра
 * в действие смены пароля ({@link org.cherchgk.actions.security.SetNewPasswordAction}).
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class ShowSetNewPasswordPageAction extends ActionSupport {

    private SecurityService securityService;
    private String tokenUUID;
    private String message;

    public ShowSetNewPasswordPageAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public String execute() throws Exception {
        if (securityService.isValidToken(tokenUUID, Token.Type.RESTORE_PASSWORD)) {
            return Action.SUCCESS;
        } else {
            message = "Ссылка на страницу установки нового пароля устарела либо недействительна.";
            return Action.ERROR;
        }
    }

    public String getTokenUUID() {
        return tokenUUID;
    }

    public void setTokenUUID(String tokenUUID) {
        this.tokenUUID = tokenUUID;
    }

    public String getMessage() {
        return message;
    }
}
