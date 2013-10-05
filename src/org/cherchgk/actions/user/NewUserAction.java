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
