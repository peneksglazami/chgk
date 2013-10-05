package org.cherchgk.actions.user;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.shiro.SecurityUtils;
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
        roles.put("orginizer", "Организатор");
        return Collections.unmodifiableMap(roles);
    }

    public String getCurrentUserName() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }
}
