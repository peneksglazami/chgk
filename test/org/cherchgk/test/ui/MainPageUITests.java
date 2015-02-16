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
public class MainPageUITests extends BaseUITest {

    @Test
    public void correctRedirectToMainPage() {
        open("");
        $(By.id("tournament-list-link")).exists();
    }

    @Test
    public void guestCannotSeeUserListLink() {
        open("");
        $(By.id("user-list-link")).shouldNot(Condition.exist);
    }

    @Test
    public void guestCannotSeeSettingsLink() {
        open("");
        $(By.id("settings-link")).shouldNot(Condition.exist);
    }
}
