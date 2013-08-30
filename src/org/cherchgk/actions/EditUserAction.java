package org.cherchgk.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.cherchgk.domain.security.User;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие создания и редактирования пользователя
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class EditUserAction extends ActionSupport implements Preparable {

    private SecurityService securityService;
    private User user;

    public EditUserAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void validate() {
        if (user != null) {
            if ("".equals(user.getUsername())) {
                addFieldError("user.username", "Не указан логин пользователя");
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
        }
    }

    public String save() {

        return Action.SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
