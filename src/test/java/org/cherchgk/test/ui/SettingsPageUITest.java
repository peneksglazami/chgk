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
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * UI-тесты для страницы "Настройки".
 *
 * @author Andrey Grigorov
 */
public class SettingsPageUITest extends BaseUITest {

    private static final int mailServerPort = 23456;
    private static final String mailServerHostName = "127.0.0.1";

    @Test
    public void testCheckMailServerSettings() {
        open("");
        loginUser("admin", "admin");
        SelenideElement settingsLink = $(By.id("settings-link")).$(By.tagName("div"));
        settingsLink.shouldBe(Condition.visible);
        settingsLink.click();
        $(By.name("mailServerHostName")).setValue(mailServerHostName);
        $(By.name("mailServerPort")).setValue(String.valueOf(mailServerPort));
        $(By.name("mailServerUser")).setValue("test-user");
        $(By.name("mailServerPassword")).setValue("test-user-password");

        SelenideElement checkMailServerSettingsButton = $(By.id("check-mail-server-settings-button"));
        checkMailServerSettingsButton.shouldBe(Condition.visible);
        checkMailServerSettingsButton.click();
        $(Selectors.withText("Подключиться не удалось")).waitUntil(Condition.visible, 30000);

        GreenMail mailServer = new GreenMail(new ServerSetup(mailServerPort, mailServerHostName, ServerSetup.PROTOCOL_SMTP));
        try {
            mailServer.start();
            checkMailServerSettingsButton.click();
            $(Selectors.withText("Подключение выполнено успешно")).waitUntil(Condition.visible, 30000);
            logout();
        } finally {
            mailServer.stop();
        }
    }

    @Test
    public void testSettingsSaving() {
        open("");
        loginUser("admin", "admin");
        SelenideElement settingsLink = $(By.id("settings-link")).$(By.tagName("div"));
        settingsLink.shouldBe(Condition.visible);
        settingsLink.click();
        $(By.name("mailServerHostName")).setValue(mailServerHostName);
        $(By.name("mailServerPort")).setValue(String.valueOf(mailServerPort));
        $(By.name("mailServerUser")).setValue("test-user");
        $(By.name("mailServerPassword")).setValue("test-user-password");
        $(By.name("hostName")).setValue("http://127.0.0.1");
        $(By.id("save-settings-button")).click();

        settingsLink = $(By.id("settings-link")).$(By.tagName("div"));
        settingsLink.shouldBe(Condition.visible);
        settingsLink.click();
        Assert.assertEquals(mailServerHostName, $(By.name("mailServerHostName")).val());
        Assert.assertEquals(String.valueOf(mailServerPort), $(By.name("mailServerPort")).val());
        Assert.assertEquals("test-user", $(By.name("mailServerUser")).val());
        Assert.assertEquals("http://127.0.0.1", $(By.name("hostName")).val());
    }
}
