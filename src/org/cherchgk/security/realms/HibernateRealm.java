package org.cherchgk.security.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cherchgk.utils.EntityManagerProvider;

import javax.persistence.EntityManager;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class HibernateRealm extends AuthorizingRealm {

    private EntityManager entityManager;

    public HibernateRealm() {
        entityManager = EntityManagerProvider.getEntityManager();
    }

    @Override
    public String getName() {
        return HibernateRealm.class.getName();
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return true;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // TODO: необходимо реализовать аутентификацию
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
}