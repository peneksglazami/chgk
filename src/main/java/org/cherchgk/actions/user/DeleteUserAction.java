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
package org.cherchgk.actions.user;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.security.PermissionChecker;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие удаления пользователя.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class DeleteUserAction extends ActionSupport {

    private SecurityService securityService;

    public DeleteUserAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public String execute() throws Exception {
        Long userId = Long.valueOf(ActionContextHelper.getRequestParameterValue("user.id"));
        PermissionChecker.checkPermissions("user:edit:" + userId);
        securityService.deleteUser(userId);
        return Action.SUCCESS;
    }
}
