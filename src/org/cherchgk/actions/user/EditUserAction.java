package org.cherchgk.actions.user;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.security.Role;
import org.cherchgk.domain.security.User;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Действие создания и редактирования пользователя
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class EditUserAction extends ActionSupport implements Preparable {

    private SecurityService securityService;
    private User user;
    private String previousPasswordHash;
    private String previousPasswordHashPrefix;
    private EntityManager entityManager;

    public EditUserAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void validate() {
        if (user != null) {
            if (user.getId() == null) { // создание нового пользователя
                if ("".equals(user.getUsername())) {
                    addFieldError("user.username", "Не указан логин пользователя");
                } else {
                    User existedUser = securityService.getUserByName(user.getUsername());
                    if (existedUser != null) {
                        addFieldError("user.username", "Пользователь с таким логином уже существует");
                    }
                }
            }
            if ("".equals(user.getPassword())) {
                addFieldError("user.password", "Не указан пароль");
            }
        }
    }

    public void prepare() throws Exception {
        String userId = ActionContextHelper.getRequestParameterValue("user.id");
        if ((userId != null) && !"".equals(userId)) {
            user = securityService.getUserById(Long.valueOf(userId));
            previousPasswordHash = user.getPassword();
            previousPasswordHashPrefix = previousPasswordHash.substring(0, 5);
        }
    }

    public String save() {
        if (user.getId() == null) { // создание нового пользователя
            Iterator<Role> roleIterator = user.getRoles().iterator();
            String roleName = null;
            if (roleIterator.hasNext()) {
                roleName = roleIterator.next().getName();
            }
            securityService.createUserIfNotExist(user.getUsername(), user.getPassword(), roleName, false);
        } else { // обновление существующего
            if (previousPasswordHashPrefix.equals(user.getPassword())) {
                user.setPassword(previousPasswordHash);
            } else {
                securityService.setUserPassword(user, user.getPassword());
            }
            entityManager.merge(user);
        }
        return Action.SUCCESS;
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

    public void setRole(String roleName) {
        user.getRoles().clear();
        user.getRoles().add(securityService.getRoleByName(roleName));
    }
}