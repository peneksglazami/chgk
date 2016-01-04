<img src="https://code.google.com/p/chgk/logo?cct=1403206452"/>
# Веб-приложение для проведения турниров по спортивному варианту игры "Что? Где? Когда?"

[![Build Status](https://semaphoreci.com/api/v1/projects/badce8e9-a5b8-462d-9ff5-402794bfca36/648898/badge.svg)](https://semaphoreci.com/peneksglazami/chgk)
[![Coverage Status](https://coveralls.io/repos/peneksglazami/chgk/badge.svg?branch=master&service=github)](https://coveralls.io/github/peneksglazami/chgk?branch=master)
[![](https://img.shields.io/badge/License-Apache%202-blue.svg)](LICENSE)

Основной целью данного проекта является создание веб-приложения для проведения турниров по спортивной версии игры "Что? Где? Когда?".

Текущая версия *v0.5* выпущена 26.09.2014.

Дистрибутивные архивы
-----
Дистрибутивные архивы с приложением можно скачать на https://sourceforge.net/projects/chgk/files/.
Инструкцию по запуску приложения читайте тут https://code.google.com/p/chgk/wiki/Install.

Демо-версии
-----
Демо-версия приложения (v0.5) доступна по адресу http://demo-chgk.rhcloud.com. Милости просим.

Основные возможности и особенности (v0.1)
-----
  * Ведение списков турниров и команд, принимающих участие в турнире
  * Редактирование результатов турнира
  * Формирование итоговой таблицы с результатами турнира (ранжирование команд производится по сумме ответов и рейтингу вопросов, на которые команда дала правильный ответ)
  * Приложение поддерживает только две категории команд: младшие школьники, старшие школьники (в следующей версии список категорий будет настраиваемым)

Основные возможности и особенности (v0.2.1)
-----
  * Поддержка настраиваемого списка категорий команд

Основные возможности и особенности (v0.3)
-----
  * Разработана система авторизации
  * Выполнено разделение пользователей приложения на администраторов и организаторов, которые будут иметь доступ к редактированию информации о турнирах, и зрителей (неавторизованных пользователей), которые могут только просматривать информацию о турнирах. Изначально доступны пользователи admin/admin и organizer/organizer.

Основные возможности и особенности (v0.4)
-----
  * Добавлена возможность деления турнира на туры
  * Реализована поддержка сквозной и потуровой нумерации вопросов в турнире
  * Редактирование результатов в разрезе туров
  * Оптимизация скорости работы с базой данных

Основные возможности и особенности (v0.5)
-----
  * Реализована поддержка ранжирования команд по сумме мест в туре при формировании итоговых результатов
  * Добавлена возможность выгрузки результатов турнира в формате PDF

Направления дальнейшей работы
-----
  * Реализация механизма регистрации пользователей и восстановления пароля
  * Ведение состава команд
  * Загрузка состава команд с сайта рейтинга МАК (http://rating.chgk.info/)
  * Выгрузка результатов турнира в формате CSV для импорта на сайт рейтинга МАК (http://rating.chgk.info/)
  * Подготовка бланков карточек для ответов
  * Ввод результатов турнира с помощью USB-сканера штрих-кодов

Скриншоты
-----
<table>
  <tr>
    <td width="200px">Стартовая страница</td>
    <td width="200px">Создание турнира</td>
    <td width="200px">Список турниров</td>
  </tr>
  <tr>
    <td>
      <a href="https://a.fsdn.com/con/app/proj/chgk/screenshots/page1.png"><img src="https://a.fsdn.com/con/app/proj/chgk/screenshots/page1.png/182/137"/></a>
    </td>
    <td>
    <a href="https://a.fsdn.com/con/app/proj/chgk/screenshots/page2.png"><img src="https://a.fsdn.com/con/app/proj/chgk/screenshots/page2.png/182/137"/></a>
    </td>
    <td>
<a href="https://a.fsdn.com/con/app/proj/chgk/screenshots/page3.png"><img src="https://a.fsdn.com/con/app/proj/chgk/screenshots/page3.png/182/137"/></a>
    </td>
  </tr>
  <tr>
    <td>Регистрация команды на турнир</td>
    <td>Список команд</td>
    <td>Редактирование результатов турнира</td>
  </tr>
  <tr>
    <td>
<a href="https://a.fsdn.com/con/app/proj/chgk/screenshots/page4.png"><img src="https://a.fsdn.com/con/app/proj/chgk/screenshots/page4.png/182/137"/></a>
    </td>
    <td>
<a href="https://a.fsdn.com/con/app/proj/chgk/screenshots/page5.png"><img src="https://a.fsdn.com/con/app/proj/chgk/screenshots/page5.png/182/137"/></a>
    </td>
    <td>
<a href="https://a.fsdn.com/con/app/proj/chgk/screenshots/page6.png"><img src="https://a.fsdn.com/con/app/proj/chgk/screenshots/page6.png/182/137"/></a>
    </td>
  </tr>
</table>

При разработке мы используем
-----
<a href="http://www.jetbrains.com/idea/"><img src="http://www.jetbrains.com/idea/opensource/img/all/banners/idea468x60_white.gif"/></a>
<a href="http://projects.spring.io/spring-framework/"><img src="http://assets.spring.io/drupal/files/header/logo-spring-103x60.png"/></a>
<a href="http://www.hibernate.org/"><img src="https://forum.hibernate.org/styles/hibernate/imageset/site_logo.gif"/></a>
<a href="http://struts.apache.org/"><img src="http://upload.wikimedia.org/wikipedia/commons/e/e9/Struts.png"/></a>

Для тестирования мы используем
-----
<a href="http://selenide.org/"><img src="http://ru.selenide.org/images/selenide-logo-big.png"/></a>
<a href="http://mockito.org/"><img src="http://site.mockito.org/img/mockito-logo-small.svg"/></a>

Для хостинга демо-приложений мы используем
-----
<a href="http://openshift.com/"><img src="http://upload.wikimedia.org/wikipedia/en/3/3a/OpenShift-LogoType.svg"/></a>
