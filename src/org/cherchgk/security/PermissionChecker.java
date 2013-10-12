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
