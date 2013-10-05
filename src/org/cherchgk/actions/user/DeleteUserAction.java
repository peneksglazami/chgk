package org.cherchgk.actions.user;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.shiro.SecurityUtils;
import org.cherchgk.services.SecurityService;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие удаления пользователя
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class DeleteUserAction extends ActionSupport {

    private SecurityService securityService;

    public DeleteUserAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public String execute() throws Exception {
        Long userId = Long.valueOf(ActionContextHelper.getRequestParameterValue("user.id"));
        if (SecurityUtils.getSubject().isPermitted("user:edit:" + userId)) {
            securityService.deleteUser(userId);
        }
        return Action.SUCCESS;
    }
}
