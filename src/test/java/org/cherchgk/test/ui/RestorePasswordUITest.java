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
package org.cherchgk.test.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.cherchgk.actions.security.RestorePasswordAction;
import org.cherchgk.domain.security.Role;
import org.cherchgk.services.SecurityService;
import org.cherchgk.services.SettingsService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

import javax.mail.MessagingException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;

/**
 * UI-тесты для страницы восстановления пароля.
 *
 * @author Andrey Grigorov
 */
public class RestorePasswordUITest extends BaseUITest {

    private static final int mailServerPort = 12345;
    private static final String mailServerHostName = "127.0.0.1";
    private static final GreenMail mailServer = new GreenMail(new ServerSetup(mailServerPort, mailServerHostName, ServerSetup.PROTOCOL_SMTP));

    @BeforeClass
    public static void initContext() throws Exception {
        System.setProperty("jdbc.url", TestWebAppLauncher.TEST_APPLICATION_DATABASE_CONNECTION_URL);
        System.setProperty("hibernate.showSql", "false");
        GenericXmlContextLoader xmlContextLoader = new GenericXmlContextLoader();
        ApplicationContext applicationContext = xmlContextLoader.loadContext("/WEB-INF/applicationContext.xml");

        SettingsService settingsService = applicationContext.getBean(SettingsService.class);
        settingsService.saveHostName("test");
        settingsService.saveMailServerHostName(mailServerHostName);
        settingsService.saveMailServerPort(String.valueOf(mailServerPort));
        settingsService.saveMailServerUser("mail-server-user");
        settingsService.saveMailServerPassword("mail-server-user-password");

        SecurityService securityService = applicationContext.getBean(SecurityService.class);
        Role role = securityService.getRoleByName("organizer");
        securityService.createUser("testUser", "12345", "test-user@example.com", role, false);
    }

    @Before
    public void clearMailServer() throws FolderException {
        mailServer.reset();
    }

    @Test
    public void testUserNotFound() {
        openRestorePasswordPage();
        restorePassword("unknown@example.com");
        $(Selectors.withText(RestorePasswordAction.USER_NOT_FOUND)).shouldBe(Condition.visible);
    }

    @Test
    public void testSuccessfulRestorePassword() throws MessagingException {
        openRestorePasswordPage();
        restorePassword("test-user@example.com");
        $(Selectors.withText(RestorePasswordAction.SUCCESS_MESSAGE)).shouldBe(Condition.visible);
        assertEquals(1, mailServer.getReceivedMessages().length);
        assertEquals("test-user@example.com", mailServer.getReceivedMessages()[0].getAllRecipients()[0].toString());
    }

    @Test
    public void testMailSendingFailure() throws MessagingException {
        mailServer.stop();
        openRestorePasswordPage();
        restorePassword("test-user@example.com");
        $(Selectors.withText(RestorePasswordAction.MAIL_SENDING_FAILURE)).shouldBe(Condition.visible);
    }

    private void openRestorePasswordPage() {
        open("");
        $(By.id("loginAction")).shouldBe(Condition.visible);
        $(By.id("loginAction")).click();
        $(By.linkText("Забыли пароль?")).click();
    }

    private void restorePassword(String loginOrEmail) {
        SelenideElement loginOrEmailField = $(By.name("loginOrEmail"));
        loginOrEmailField.shouldBe(Condition.visible);
        loginOrEmailField.setValue(loginOrEmail);
        SelenideElement submitButton = $(By.id("restore-password-button"));
        submitButton.shouldBe(Condition.visible);
        submitButton.click();
    }
}
