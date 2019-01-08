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
package org.cherchgk.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Хелпер-класс для проверки доступности разрешений на действия
 * у текущего пользователя.
 *
 * @author Andrey Grigorov
 */
public class PermissionChecker {

    private PermissionChecker() {
    }

    /**
     * Проверка доступности разрешений для текущего пользователя.
     * Если хотя бы одно из разрешений не доступно пользователю, то
     * вызов метода сгенерирует исключение org.apache.shiro.authz.AuthorizationException.
     *
     * @param permissions список проверяемых привилегий
     * @throws org.apache.shiro.authz.AuthorizationException
     *          если хотя бы одно из разрешений не доступно пользователю.
     */
    public static void checkPermissions(String... permissions) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.checkPermissions(permissions);
    }
}
