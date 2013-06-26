package org.cherchgk.actions.security;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.cherchgk.utils.ActionContextHelper;

/**
 * Действие аутентификации по логину и паролю
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class LoginAction extends ActionSupport {

    @Override
    public String execute() throws Exception {
        String login = ActionContextHelper.getRequestParameterValue("login");
        String password = ActionContextHelper.getRequestParameterValue("password");
        UsernamePasswordToken token = new UsernamePasswordToken(login, password);
        token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (AuthenticationException ex) {
        }
        return Action.SUCCESS;
    }
}
