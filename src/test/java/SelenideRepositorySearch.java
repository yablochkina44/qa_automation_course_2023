import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SelenideRepositorySearch {

    @Test
    void shouldFindSelenideRepositoryAtTheTop() {
        Configuration.holdBrowserOpen = true;

        // открыть главную страницу
        open("https://github.com");
        //кликнуть на кнопку поиска для открытия поля ввода
        $(".header-search-button").click(); // поиск по классу
        // ввести в поле поиска selenide и нажать enter
        $("#query-builder-test").setValue("selenide").pressEnter(); // поиск по id элемента
        // кликнуть на первый репозиторий из списка найденных
        $$("div.iRVXIo a").first().click();
        // проверка: заголовок selenide/selenide
        $("#repository-container-header").shouldHave(text("selenide\n/\nselenide"));
        $("#repository-container-header").shouldHave(text("selenide / selenide"));//все переносы строки трасформируются в пробел


        // $(".header-search-button") указан класс элемента через . HTML: <div class="header-search-button"> или <button class="header-search-button">
        // $("#query-builder-test") указан id через #,   HTML: <input id="query-builder-test">
        //  Синтаксис	Что означает	Пример HTML	    Пример Selenide
        //   [attr]   	Атрибут	       name="email"	    $("[name='email']")
        //    tag	    Тег         	<button>	        $("button")
        //  tag.class	Тег + класс	  <div class="menu">	$("div.menu")

    }

    }

