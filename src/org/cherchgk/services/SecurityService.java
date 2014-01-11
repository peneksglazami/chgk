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
 * Сервис по работе с подсистемой безопасности
 *
 * @author Andrey Grigorov
 */
@Transactional
public class SecurityService {

    /**
     * Количество итераций, выполняющихся при хешировании пароля.
     * Должно совпадать со значением passwordMatcher.hashIterations, указанным
     * в .../web/WEB-INF/shiro.ini.
     */
    private static final int hashIterations = 1024;

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

    public void createUserIfNotExist(String username, String password, String roleName) {
        User user = getUserByName(username);
        if (user == null) {
            createUser(username, password, getRoleByName(roleName));
        }
    }

    private void createUser(String username, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        setUserPassword(user, password);
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        entityManager.persist(user);
    }

    public void setUserPassword(User user, String password) {
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        ByteSource salt = rng.nextBytes();
        String passwordHash = new Sha512Hash(password, salt, hashIterations).toHex();
        user.setPassword(passwordHash);
        user.setPasswordSalt(salt.toHex());
    }

    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        entityManager.remove(user);
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public Role getRoleByName(String roleName) {
        Query roleQuery = entityManager.createQuery("select role from Role role where role.name = :roleName")
                .setParameter("roleName", roleName);
        roleQuery.setHint("org.hibernate.cacheable", true);
        List<Role> roles = roleQuery.getResultList();
        return roles.isEmpty() ? null : roles.get(0);
    }

    public User getUserByName(String username) {
        Query userQuery = entityManager.createQuery("select user from User user where user.username = :username")
                .setParameter("username", username);
        userQuery.setHint("org.hibernate.cacheable", true);
        List<User> users = userQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    public User getUserById(Long userId) {
        Query userQuery = entityManager.createQuery("select user from User user where user.id = :userId")
                .setParameter("userId", userId);
        userQuery.setHint("org.hibernate.cacheable", true);
        List<User> users = userQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> getAllUsers() {
        Query usersQuery = entityManager.createQuery("select user from User user order by user.username");
        usersQuery.setHint("org.hibernate.cacheable", true);
        return usersQuery.getResultList();
    }
}
