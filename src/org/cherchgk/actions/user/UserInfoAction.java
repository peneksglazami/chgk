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
package org.cherchgk.actions.user;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.shiro.SecurityUtils;
import org.cherchgk.domain.security.Role;
import org.cherchgk.domain.security.User;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Действие просмотра информации о пользователе
 *
 * @author Andrey Grigorov
 */
public class UserInfoAction extends ActionSupport implements Preparable {

    private SecurityService securityService;
    private User user;

    public UserInfoAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void prepare() throws Exception {
        Long userId = Long.valueOf(ActionContextHelper.getRequestParameterValue("userId"));
        user = securityService.getUserById(userId);
        user.setPassword(user.getPassword().substring(0, 5));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, String> getRoles() {
        Map<String, String> roles = new LinkedHashMap<String, String>();
        roles.put("administrator", "Администратор");
        roles.put("organizer", "Организатор");
        return Collections.unmodifiableMap(roles);
    }

    public String getCurrentRoleName() {
        for (Role role : user.getRoles()) {
            return role.getName();
        }
        return null;
    }

    public String getCurrentUserName() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }
}
