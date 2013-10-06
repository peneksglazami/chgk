package org.cherchgk.security;

import org.cherchgk.domain.security.Permission;
import org.cherchgk.domain.security.User;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ApplicationSettings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, выполняющий инициализацию системы разграничения прав.
 * При старте приложения в БД выполняется обновление информации
 * о разрешениях, которые назначены той или иной роли.
 *
 * @author Andrey Grigorov
 */
public class InitSecurityBean implements InitializingBean {

    @Autowired
    private SecurityService securityService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Permission> administratorPermissions = new HashSet<Permission>();
        administratorPermissions.add(new Permission("*"));
        securityService.setRolePermissions("administrator", administratorPermissions);

        Set<Permission> organizerPermissions = new HashSet<Permission>();
        organizerPermissions.add(new Permission("tournament:create"));
        organizerPermissions.add(new Permission("tournament:edit"));
        organizerPermissions.add(new Permission("team:create"));
        organizerPermissions.add(new Permission("team:edit"));
        securityService.setRolePermissions("organizer", organizerPermissions);

        if (ApplicationSettings.isDemoMode()) {
            User admin = securityService.getUserByName("admin");
            if (admin != null) {
                securityService.deleteUser(admin.getId());
            }
            User organizer = securityService.getUserByName("organizer");
            if (organizer != null) {
                securityService.deleteUser(organizer.getId());
            }
        }

        securityService.createUserIfNotExist("admin", "admin", "administrator");
        securityService.createUserIfNotExist("organizer", "organizer", "organizer");
    }
}