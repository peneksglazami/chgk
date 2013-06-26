package org.cherchgk.security.realms;

import org.apache.shiro.authc.*;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class HibernateRealm implements org.apache.shiro.realm.Realm {

    @Override
    public String getName() {
        return HibernateRealm.class.getName();
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return true;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // TODO: необходимо реализовать аутентификацию
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
        return authenticationInfo;
    }
}