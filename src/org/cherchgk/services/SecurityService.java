package org.cherchgk.services;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.cherchgk.domain.security.Permission;
import org.cherchgk.domain.security.Role;
import org.cherchgk.domain.security.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Andrey Grigorov
 */
@Transactional
public class SecurityService {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setRolePermissions(String roleName, Set<Permission> permissions) {
        Role role = getRoleByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role.setPermissions(permissions);
            entityManager.persist(role);
        } else {
            role.getPermissions().clear();
            role.getPermissions().addAll(permissions);
            entityManager.merge(role);
        }
    }

    public void createUserIfNotExist(String username, String password, String roleName, boolean blocked) {
        User user = getUserByName(username);
        if (user == null) {
            createUser(username, password, getRoleByName(roleName), blocked);
        }
    }

    private void createUser(String username, String password, Role role, boolean blocked) {
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        ByteSource salt = rng.nextBytes();
        String hashedPassword = new Sha512Hash(password, salt, 1024).toHex();

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setPasswordSalt(salt.toHex());
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        user.setBlocked(blocked);
        entityManager.persist(user);
    }

    public Role getRoleByName(String roleName) {
        Query roleQuery = entityManager.createQuery("select role from Role role where role.name = :roleName")
                .setParameter("roleName", roleName);
        List<Role> roles = roleQuery.getResultList();
        return roles.isEmpty() ? null : roles.get(0);
    }

    public User getUserByName(String username) {
        Query userQuery = entityManager.createQuery("select user from User user where user.username = :username")
                .setParameter("username", username);
        List<User> users = userQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }
}
