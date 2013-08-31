package org.cherchgk.actions.user;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.security.User;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;

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
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
