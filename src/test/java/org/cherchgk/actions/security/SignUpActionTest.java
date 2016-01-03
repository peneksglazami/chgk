/*
 * Copyright 2012-2016 Andrey Grigorov, Anton Grigorov
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
package org.cherchgk.actions.security;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import org.cherchgk.BaseStrutsSpringTestCase;
import org.cherchgk.domain.security.User;
import org.cherchgk.services.SecurityService;
import org.mockito.Mockito;

import javax.mail.MessagingException;

/**
 * Тесты действия регистрации нового пользователя.
 */
public class SignUpActionTest extends BaseStrutsSpringTestCase {

    private static SecurityService prepareSecurityServiceMock(boolean userByNameExists,
                                                              boolean userByEmailExists,
                                                              boolean successfulRegistration) throws Exception {
        SecurityService securityServiceMock = Mockito.mock(SecurityService.class);
        User userMock = Mockito.mock(User.class);
        Mockito.when(securityServiceMock.getUserByName(Mockito.anyString()))
                .thenReturn(userByNameExists ? userMock : null);
        Mockito.when(securityServiceMock.getUserByEmail(Mockito.anyString()))
                .thenReturn(userByEmailExists ? userMock : null);
        if (successfulRegistration) {
            Mockito.doNothing().when(securityServiceMock)
                    .registerNewUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        } else {
            Mockito.doThrow(new MessagingException()).when(securityServiceMock)
                    .registerNewUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        }
        return securityServiceMock;
    }

    public void testCorrectSignUp() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("email", "test@email.com");
        request.setParameter("password", "password");
        request.setParameter("password2", "password");

        ActionProxy proxy = getActionProxy("/sign-up");
        SignUpAction signUpAction = (SignUpAction) proxy.getAction();
        signUpAction.setSecurityService(prepareSecurityServiceMock(false, false, true));
        String result = proxy.execute();

        assertEquals(Action.SUCCESS, result);
    }

    public void testRejectSignUpBecauseLoginIsEmpty() throws Exception {
        request.setParameter("login", "");
        request.setParameter("email", "test@email.com");
        request.setParameter("password", "password");
        request.setParameter("password2", "password");

        ActionProxy proxy = getActionProxy("/sign-up");
        SignUpAction signUpAction = (SignUpAction) proxy.getAction();
        signUpAction.setSecurityService(prepareSecurityServiceMock(false, false, true));
        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("Логин не может быть пустым", signUpAction.getActionErrors().iterator().next());
    }

    public void testRejectSignUpBecauseLoginIsAlreadyUsed() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("email", "test@email.com");
        request.setParameter("password", "password");
        request.setParameter("password2", "password");

        ActionProxy proxy = getActionProxy("/sign-up");
        SignUpAction signUpAction = (SignUpAction) proxy.getAction();
        signUpAction.setSecurityService(prepareSecurityServiceMock(true, false, true));
        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("Пользователь с данным логином уже существует", signUpAction.getActionErrors().iterator().next());
    }

    public void testRejectSignUpBecauseEmailIsInvalid() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("email", "test@test@email.com");
        request.setParameter("password", "password");
        request.setParameter("password2", "password");

        ActionProxy proxy = getActionProxy("/sign-up");
        SignUpAction signUpAction = (SignUpAction) proxy.getAction();
        signUpAction.setSecurityService(prepareSecurityServiceMock(false, false, true));
        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("Указан некорректный адрес электронной почты", signUpAction.getActionErrors().iterator().next());
    }

    public void testRejectSignUpBecauseEmailIsAlreadyUsed() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("email", "test@email.com");
        request.setParameter("password", "password");
        request.setParameter("password2", "password");

        ActionProxy proxy = getActionProxy("/sign-up");
        SignUpAction signUpAction = (SignUpAction) proxy.getAction();
        signUpAction.setSecurityService(prepareSecurityServiceMock(false, true, true));
        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("Пользователь с таким адресом электронной почты уже зарегистрирован",
                signUpAction.getActionErrors().iterator().next());
    }

    public void testRejectSignUpBecausePasswordIsEmpty() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("email", "test@email.com");
        request.setParameter("password", "");
        request.setParameter("password2", "password");

        ActionProxy proxy = getActionProxy("/sign-up");
        SignUpAction signUpAction = (SignUpAction) proxy.getAction();
        signUpAction.setSecurityService(prepareSecurityServiceMock(false, false, true));
        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("Пароль не может быть пустым", signUpAction.getActionErrors().iterator().next());
    }

    public void testRejectSignUpBecausePasswordsAreNotEqual() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("email", "test@email.com");
        request.setParameter("password", "password1");
        request.setParameter("password2", "password2");

        ActionProxy proxy = getActionProxy("/sign-up");
        SignUpAction signUpAction = (SignUpAction) proxy.getAction();
        signUpAction.setSecurityService(prepareSecurityServiceMock(false, false, true));
        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("Пароль и повторно введённый пароль не совпадают",
                signUpAction.getActionErrors().iterator().next());
    }

    public void testRejectSignUpBecauseMessagingException() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("email", "test@email.com");
        request.setParameter("password", "password");
        request.setParameter("password2", "password");

        ActionProxy proxy = getActionProxy("/sign-up");
        SignUpAction signUpAction = (SignUpAction) proxy.getAction();
        signUpAction.setSecurityService(prepareSecurityServiceMock(false, false, false));
        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("Регистрация временно недоступна.", signUpAction.getActionErrors().iterator().next());
    }
}