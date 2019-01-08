/*
 * Copyright 2012-2019 Andrey Grigorov, Anton Grigorov
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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.StrutsSpringTestCase;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Тесты операции выхода из учетной записи.
 *
 * @author Andrey Grigorov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class LogoutActionTest extends StrutsSpringTestCase {

    public void testCorrectUserLogout() throws Exception {
        ActionProxy proxy = getActionProxy("/logout");

        PowerMockito.mockStatic(SecurityUtils.class);
        Subject subjectMock = Mockito.mock(Subject.class);
        Mockito.when(SecurityUtils.getSubject()).thenReturn(subjectMock);
        Mockito.doNothing().when(subjectMock).logout();

        proxy.getAction();
        String result = proxy.execute();

        assertEquals(Action.SUCCESS, result);
        Mockito.verify(subjectMock, Mockito.times(1)).logout();
    }
}