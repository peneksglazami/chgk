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
package org.cherchgk.services;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.cherchgk.domain.security.Permission;
import org.cherchgk.domain.security.Role;
import org.cherchgk.domain.security.Token;
import org.cherchgk.domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Сервис по работе с подсистемой безопасности.
 * <p/>
 * Все методы данного сервиса вызываются в транзакции, которая
 * откатывается в случае бросания методом исключения.
 *
 * @author Andrey Grigorov
 */
@Transactional(rollbackFor = Throwable.class)
public class SecurityService {

    /**
     * Количество итераций, выполняющихся при хешировании пароля.
     * Должно совпадать со значением passwordMatcher.hashIterations, указанным
     * в .../web/WEB-INF/shiro.ini.
     */
    private static final int hashIterations = 1024;

    private EntityManager entityManager;
    @Autowired
    private MailService mailService;
    @Autowired
    private SettingsService settingsService;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Назначить указанной роли набор разрешений.
     * Если роли с указанным названием не существовало, то такая роль
     * будет создана. Если роль с указанным названием сущеставала, то
     * ранее назначенные разрешения будут заменены на новые.
     *
     * @param roleName    Название роли.
     * @param permissions Множество разрешений.
     */
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

    /**
     * Создать пользователя, если он ещё не существует.
     *
     * @param username Имя пользователя.
     * @param password Пароль.
     * @param roleName Название роли.
     */
    public void createUserIfNotExist(String username, String password, String roleName) {
        User user = getUserByName(username);
        if (user == null) {
            createUser(username, password, null, getRoleByName(roleName), false);
        }
    }

    /**
     * Создать пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль.
     * @param email    Адрес электронной почты.
     * @param role     Роль.
     * @param blocked  Признак заблокированности пользователя. true - пользователь заблокирован,
     *                 false - пользователь не заблокирован.
     * @return Созданный пользователь.
     */
    public User createUser(String username, String password, String email, Role role, boolean blocked) {
        User user = new User();
        user.setUsername(username);
        setUserPassword(user, password);
        if (email != null) {
            user.setEmail(email.trim().toLowerCase());
        }
        user.setBlocked(blocked);
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        entityManager.persist(user);
        return user;
    }

    /**
     * Зарегистрировать в системе нового пользователя и отправить на
     * электронный адрес пользователя письмо с ссылкой для подтверждения
     * регистрации.
     * Созданному пользователю назначается роль "organizer".
     * В случае, если не при отправке письма произошла ошибка,
     * то созданный новый пользователь удаляется из БД приложения.
     *
     * @param username Имя пользователя.
     * @param password Пароль.
     * @param email    Адрес электронной почты.
     * @throws MessagingException в случае невозможности отправить письма с
     *                            ссылкой на подтверждение регистрации.
     */
    public void registerNewUser(String username, String password, String email) throws MessagingException {
        Role role = getRoleByName("organizer");
        User user = createUser(username, password, email, role, true);
        Token token = new Token();
        token.setType(Token.Type.SIGN_UP);
        token.setUuid(UUID.randomUUID().toString());
        token.setUser(user);
        token.setCreateDate(new Date());
        entityManager.persist(token);
        mailService.sendMail(email, "Регистрация в системе ведения турниров \"Что? Где? Когда?\"",
                "Для подтвержения регистрации пройдите по <a href=\"" +
                        settingsService.getHostName() + "/confirm-sign-up?tokenUUID=" +
                        token.getUuid() + "\">ссылке</a>.");
    }

    /**
     * Выполняется верификация токена регистрации.
     * В случае, если найден регистрационный токен с указанным
     * идентификатором, то производится разблокировка пользователя
     * и удаление токена.
     *
     * @param tokenUUID Идентификатор токена.
     * @return true - регистрация пользователя подтверждена,
     * false - токен не найден.
     */
    public boolean confirmRegistration(String tokenUUID) {
        Token token = getTokenByUUID(tokenUUID);

        if ((token == null) || !Token.Type.SIGN_UP.equals(token.getType())) {
            return false;
        }

        User user = token.getUser();

        entityManager.remove(token);

        if (!user.getBlocked()) {
            return true;
        }

        user.setBlocked(false);
        entityManager.merge(user);

        return true;
    }

    /**
     * Поиск верификационного токена по идентификатору.
     *
     * @param tokenUUID Идентификатор токена.
     * @return Найденный токен или null.
     */
    private Token getTokenByUUID(String tokenUUID) {
        TypedQuery<Token> tokenQuery = entityManager.createQuery("select token " +
                "from Token token " +
                "where uuid = :tokenUUID", Token.class)
                .setParameter("tokenUUID", tokenUUID);
        tokenQuery.setHint("org.hibernate.cacheable", true);
        List<Token> tokens = tokenQuery.getResultList();

        if (tokens.isEmpty()) {
            return null;
        }

        return tokens.get(0);
    }

    /**
     * Удалить все верификационный токены, относящиеся к указанному пользователю
     * и имеющие указанный тип.
     *
     * @param user      Пользователь, верификационные токены которого необходимо удалить.
     * @param tokenType Тип токенов, которые надо удалить.
     */
    private void deleteTokens(User user, Token.Type tokenType) {
        entityManager.createQuery("delete from Token where user = :user and type = :tokenType")
                .setParameter("user", user)
                .setParameter("tokenType", tokenType)
                .executeUpdate();
    }

    /**
     * Проверка существования верификационного токена с указанными
     * идентификатором и типом.
     *
     * @param tokenUUID Идентификатор токена.
     * @param tokenType Тип токена.
     * @return true - токен существует, false - токен не найден.
     */
    public boolean isValidToken(String tokenUUID, Token.Type tokenType) {
        Token token = getTokenByUUID(tokenUUID);
        return (token != null) && token.getType().equals(tokenType);
    }

    /**
     * Отправить пользователю электронное письмо с инструкцией по восстановлению пароля.
     *
     * @param user Пользователь.
     * @throws MessagingException в случае невозможности отправить письма с
     *                            инструкцией по восстановлению пароля.
     */
    public void restorePassword(User user) throws MessagingException {
        deleteTokens(user, Token.Type.RESTORE_PASSWORD);
        Token token = new Token();
        token.setType(Token.Type.RESTORE_PASSWORD);
        token.setUuid(UUID.randomUUID().toString());
        token.setUser(user);
        token.setCreateDate(new Date());
        entityManager.persist(token);
        mailService.sendMail(user.getEmail(), "Восстановление пароля в системе ведения турниров \"Что? Где? Когда?\"",
                "Для установки нового пароля пройдите по <a href=\"" +
                        settingsService.getHostName() + "/show-set-new-password-page?tokenUUID=" +
                        token.getUuid() + "\">ссылке</a>.");
    }

    /**
     * Установка нового пароля пользователю.
     *
     * @param tokenUUID Идентификатор токена, связанного с операцией восстановления пароля.
     * @param password  Новый пароль.
     * @return true - смена пароля произошла успешно, false - смена пароля не произведена
     * по причиние недействительности пароля.
     */
    public boolean setNewPassword(String tokenUUID, String password) {
        Token token = getTokenByUUID(tokenUUID);

        if ((token == null) || !Token.Type.RESTORE_PASSWORD.equals(token.getType())) {
            return false;
        }

        setUserPassword(token.getUser(), password);
        entityManager.remove(token);
        return true;
    }

    /**
     * Установка пользователю нового пароля.
     *
     * @param user     Пользователь
     * @param password Пароль.
     */
    public void setUserPassword(User user, String password) {
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        ByteSource salt = rng.nextBytes();
        String passwordHash = new Sha512Hash(password, salt, hashIterations).toHex();
        user.setPassword(passwordHash);
        user.setPasswordSalt(salt.toHex());
    }

    /**
     * Удаление пользователя, имеющего указанный индентификатор.
     *
     * @param userId Идентификатор пользоваетеля, которого необходимо удалить.
     */
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        entityManager.remove(user);
    }

    /**
     * Сохранение изменений в сущности "Пользователей".
     *
     * @param user Пользователь.
     */
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    /**
     * Получить роль по её названию.
     *
     * @param roleName Название роли.
     * @return Роль или null, если роль с указанным названием не существует.
     */
    public Role getRoleByName(String roleName) {
        TypedQuery<Role> roleQuery = entityManager.createQuery("select role "
                + "from Role role "
                + "where role.name = :roleName", Role.class)
                .setParameter("roleName", roleName);
        roleQuery.setHint("org.hibernate.cacheable", true);
        List<Role> roles = roleQuery.getResultList();
        return roles.isEmpty() ? null : roles.get(0);
    }

    /**
     * Получить пользователя (объект класса {@link org.cherchgk.domain.security.User})
     * по его имени (логину).
     *
     * @param username Имя (логин) пользователя.
     * @return Пользователь или null, если пользователя с таким именем не найдено.
     */
    public User getUserByName(String username) {
        TypedQuery<User> userQuery = entityManager.createQuery("select user "
                + "from User user "
                + "where user.username = :username", User.class)
                .setParameter("username", username);
        userQuery.setHint("org.hibernate.cacheable", true);
        List<User> users = userQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Получить пользователя (объект класса {@link org.cherchgk.domain.security.User}
     * по адресу электронной почты.
     *
     * @param email Адрес электронной почты.
     * @return Пользователь или null, если пользователь с указанным адресом
     * электронной почты не найден.
     */
    public User getUserByEmail(String email) {
        TypedQuery<User> userQuery = entityManager.createQuery("select user "
                + "from User user "
                + "where user.email = :email", User.class)
                .setParameter("email", email.trim().toLowerCase());
        userQuery.setHint("org.hibernate.cacheable", true);
        List<User> users = userQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Получить пользователя (обеъкт класса {@link org.cherchgk.domain.security.User})
     * по идентификатору.
     *
     * @param userId Идентификатор пользователя.
     * @return Пользователь или null, если пользователь с указанным идентификатором
     * не найден.
     */
    public User getUserById(Long userId) {
        TypedQuery<User> userQuery = entityManager.createQuery("select user "
                + "from User user "
                + "where user.id = :userId", User.class)
                .setParameter("userId", userId);
        userQuery.setHint("org.hibernate.cacheable", true);
        List<User> users = userQuery.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Получить список всех зарегистрированных в системе пользователей.
     *
     * @return Список всех зарегистрированных в системе пользователей.
     */
    public List<User> getAllUsers() {
        TypedQuery<User> usersQuery = entityManager.createQuery("select user "
                + "from User user "
                + "order by user.username", User.class);
        usersQuery.setHint("org.hibernate.cacheable", true);
        return usersQuery.getResultList();
    }
}
