package org.cherchgk.actions;

import com.opensymphony.xwork2.Action;
import org.cherchgk.domain.security.User;
import org.cherchgk.services.SecurityService;

import java.util.List;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class UserListAction implements Action {

    private SecurityService securityService;

    public UserListAction(SecurityService securityService) {
        this.securityService = securityService;
    }

    public List<User> getUsers() {
        return securityService.getAllUsers();
    }

    @Override
    public String execute() throws Exception {
        return Action.SUCCESS;
    }
}
