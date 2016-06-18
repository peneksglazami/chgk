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
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * UI-тесты для главной страницы приложения.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class MainPageUITest extends BaseUITest {

    @Test
    public void testCorrectRedirectToMainPage() {
        open("");
        $(By.id("tournament-list-link")).shouldBe(Condition.exist);
    }

    @Test
    public void testGuestShouldNotSeeUserListLink() {
        open("");
        $(By.id("user-list-link")).shouldNot(Condition.exist);
    }

    @Test
    public void testGuestShouldNotSeeSettingsLink() {
        open("");
        $(By.id("settings-link")).shouldNot(Condition.exist);
    }

    @Test
    public void testGuestShouldSeeLoginLink() {
        open("");
        $(By.id("loginAction")).shouldBe(Condition.visible);
    }

    @Test
    public void testGuestShouldSeeSignUpLink() {
        open("");
        $(By.id("signUpAction")).shouldBe(Condition.visible);
    }

    @Test
    public void testAuthorizedUserShouldNotSeeSignUpLink() {
        open("");
        loginUser("organizer", "organizer");
        $(By.id("signUpAction")).shouldNotBe(Condition.exist);
    }

    @Test
    public void testCorrectUserLoginAndLogout() {
        open("");
        $(By.id("loginAction")).shouldBe(Condition.visible);
        loginUser("admin", "admin");
        $(By.id("loginAction")).shouldNotBe(Condition.exist);
        logout();
    }

    @Test
    public void testIncorrectUserLogin() {
        open("");
        loginUser("", "");
        $(By.id("dialogBox")).shouldBe(Condition.visible);
        $(By.id("loginAction")).shouldBe(Condition.visible);
    }

    @Test
    public void testAdminShouldSeeUserListLink() {
        open("");
        loginUser("admin", "admin");
        $(By.id("user-list-link")).should(Condition.visible);
    }

    @Test
    public void testAdminShouldSeeSettingsLink() {
        open("");
        loginUser("admin", "admin");
        $(By.id("settings-link")).should(Condition.visible);
    }

    @Test
    public void testOrganizerShouldSeeUserListLink() {
        open("");
        loginUser("organizer", "organizer");
        $(By.id("user-list-link")).should(Condition.visible);
    }

    @Test
    public void testOrganizerShouldNotSeeSettingsLink() {
        open("");
        loginUser("organizer", "organizer");
        $(By.id("settings-link")).shouldNot(Condition.exist);
    }
}
