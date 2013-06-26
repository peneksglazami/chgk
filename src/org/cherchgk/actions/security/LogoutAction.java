package org.cherchgk.actions.security;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Обработка действия выхода пользователя из системы
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class LogoutAction extends ActionSupport {

    @Override
    public String execute() throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return Action.SUCCESS;
    }
}
