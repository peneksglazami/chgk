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

import com.opensymphony.xwork2.ActionSupport;
import org.cherchgk.domain.security.User;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Действие добавления нового пользователя
 *
 * @author Andrey Grigorov
 */
public class NewUserAction extends ActionSupport {

    private User user = new User();

    public User getUser() {
        return user;
    }

    public Map<String, String> getRoles() {
        Map<String, String> roles = new LinkedHashMap<String, String>();
        roles.put("administrator", "Администратор");
        roles.put("organizer", "Организатор");
        return Collections.unmodifiableMap(roles);
    }
}
