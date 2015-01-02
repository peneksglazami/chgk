/*
 * Copyright 2012-2015 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cherchgk.security.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.cherchgk.domain.security.Permission;
import org.cherchgk.domain.security.Role;
import org.cherchgk.domain.security.User;
import org.cherchgk.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

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
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user;
        try {
            Query query = entityManager.createQuery("select user from User user where user.username = :username", User.class)
                    .setParameter("username", username);
            query.setHint("org.hibernate.cacheable", true);
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        if (user.getBlocked()) {
            throw new DisabledAccountException("Account for user [" + username + "] is locked");
        }

        return new SimpleAuthenticationInfo(username, ByteSource.Util.bytes(Hex.decode(user.getPassword())),
                ByteSource.Util.bytes(Hex.decode(user.getPasswordSalt())), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) getAvailablePrincipal(principals);
        Query query = entityManager.createQuery("select user from User user where user.username = :username", User.class)
                .setParameter("username", username);
        query.setHint("org.hibernate.cacheable", true);
        User user = (User) query.getSingleResult();

        Set<String> roleNames = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();

        for (Role role : user.getRoles()) {
            roleNames.add(role.getName());
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getPermission());
            }
        }

        for (Permission permission : user.getPermissions()) {
            permissions.add(permission.getPermission());
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }
}