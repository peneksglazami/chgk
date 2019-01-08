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
package org.cherchgk.test.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.cherchgk.actions.security.RestorePasswordAction;
import org.cherchgk.actions.security.SetNewPasswordAction;
import org.cherchgk.actions.security.ShowSetNewPasswordPageAction;
import org.cherchgk.domain.security.Role;
import org.cherchgk.services.SecurityService;
import org.cherchgk.services.SettingsService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        settingsService.saveHostName(Configuration.baseUrl);
        settingsService.saveMailServerHostName(mailServerHostName);
        settingsService.saveMailServerPort(String.valueOf(mailServerPort));
        settingsService.saveMailServerUser("mail-server-user");
        settingsService.saveMailServerPassword("mail-server-user-password");

        SecurityService securityService = applicationContext.getBean(SecurityService.class);
        Role role = securityService.getRoleByName("organizer");
        securityService.createUser("testUser", "12345", "test-user@example.com", role, false);
    }

    @AfterClass
    public static void destroyContext() {
        mailServer.stop();
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
    public void testSuccessfulRestorePassword() throws MessagingException, IOException {
        openRestorePasswordPage();
        restorePassword("test-user@example.com");
        $(Selectors.withText(RestorePasswordAction.SUCCESS_MESSAGE)).shouldBe(Condition.visible);
        assertEquals(1, mailServer.getReceivedMessages().length);
        MimeMessage message = mailServer.getReceivedMessages()[0];
        assertEquals("test-user@example.com", message.getAllRecipients()[0].toString());

        // посмотрим, какая ссылка пришла в письме, и перейдём по ней для установки нового пароля
        String text = message.getContent().toString();
        Matcher matcher = Pattern.compile(".*<a href=\"(.*)\">.*").matcher(text);
        assertTrue(matcher.find());
        String setNewPasswordPageUrl = matcher.group(1);
        open(setNewPasswordPageUrl);

        SelenideElement passwordField = $(By.name("password"));
        SelenideElement password2Field = $(By.name("password2"));
        SelenideElement setNewPasswordButton = $(By.id("set-new-password-button"));
        passwordField.shouldBe(Condition.visible);
        password2Field.shouldBe(Condition.visible);
        setNewPasswordButton.shouldBe(Condition.visible);

        passwordField.setValue("");
        password2Field.setValue("321");
        setNewPasswordButton.click();
        $(Selectors.withText(SetNewPasswordAction.PASSWORD_CANNOT_BE_EMPTY)).shouldBe(Condition.visible);

        passwordField.setValue("123");
        password2Field.setValue("321");
        setNewPasswordButton.click();
        $(Selectors.withText(SetNewPasswordAction.PASSWORDS_MUST_BE_EQUAL)).shouldBe(Condition.visible);

        passwordField.setValue("123");
        password2Field.setValue("123");
        setNewPasswordButton.click();
        $(Selectors.withText(SetNewPasswordAction.PASSWORD_SUCCESSFULLY_CHANGED)).shouldBe(Condition.visible);

        open(setNewPasswordPageUrl);
        $(Selectors.withText(ShowSetNewPasswordPageAction.TOKEN_IS_INVALID)).shouldBe(Condition.visible);
        $(By.name("password")).shouldBe(Condition.not(Condition.exist));
        $(By.name("password2")).shouldBe(Condition.not(Condition.exist));
        $(By.id("set-new-password-button")).shouldBe(Condition.not(Condition.exist));
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
