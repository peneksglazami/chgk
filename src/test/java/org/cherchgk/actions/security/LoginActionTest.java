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
package org.cherchgk.actions.security;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import junit.framework.TestSuite;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.StrutsSpringTestCase;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit3.PowerMockSuite;

/**
 * Тесты действия аутентификации по логину и паролю.
 */
@PrepareForTest(SecurityUtils.class)
public class LoginActionTest extends StrutsSpringTestCase {

    public static TestSuite suite() throws Exception {
        return new PowerMockSuite(LoginActionTest.class);
    }

    public void testCorrectUserLogin() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("password", "password");
        request.setParameter("currentPage", "http://localhost/main.action");

        ActionProxy proxy = getActionProxy("/login");

        PowerMockito.mockStatic(SecurityUtils.class);
        Subject subjectMock = Mockito.mock(Subject.class);
        Mockito.when(SecurityUtils.getSubject()).thenReturn(subjectMock);
        Mockito.doNothing().when(subjectMock).login(Mockito.<AuthenticationToken>any());

        LoginAction loginAction = (LoginAction) proxy.getAction();
        String result = proxy.execute();

        assertEquals(Action.SUCCESS, result);
        assertEquals("http://localhost/main.action", loginAction.getCurrentPage());
    }

    public void testUndefinedUserLogin() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("password", "password");
        request.setParameter("currentPage", "http://localhost/main.action");

        ActionProxy proxy = getActionProxy("/login");
        LoginAction loginAction = (LoginAction) proxy.getAction();

        PowerMockito.mockStatic(SecurityUtils.class);
        Subject subjectMock = Mockito.mock(Subject.class);
        Mockito.when(SecurityUtils.getSubject()).thenReturn(subjectMock);
        Mockito.doThrow(new AuthenticationException()).when(subjectMock).login(Mockito.<AuthenticationToken>any());

        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("FAILED", loginAction.getLoginError());
    }

    public void testLockedUserLogin() throws Exception {
        request.setParameter("login", "login");
        request.setParameter("password", "password");
        request.setParameter("currentPage", "http://localhost/main.action");

        ActionProxy proxy = getActionProxy("/login");
        LoginAction loginAction = (LoginAction) proxy.getAction();

        PowerMockito.mockStatic(SecurityUtils.class);
        Subject subjectMock = Mockito.mock(Subject.class);
        Mockito.when(SecurityUtils.getSubject()).thenReturn(subjectMock);
        Mockito.doThrow(new DisabledAccountException()).when(subjectMock).login(Mockito.<AuthenticationToken>any());

        String result = proxy.execute();

        assertEquals(Action.ERROR, result);
        assertEquals("LOCKED", loginAction.getLoginError());
    }
}