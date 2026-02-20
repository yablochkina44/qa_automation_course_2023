package examples;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.DragAndDropOptions.to;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class BestContributorToSelenide {
    @Test
    void solntsevShouldBeTheTopContributorSelenide(){
        Configuration.holdBrowserOpen = true;
        //открываем страницу Selenide
        open("https://github.com/selenide/selenide");
        // находим блок Contributors  и наводим курсор на первый элемент в списке
        $$("div.BorderGrid-cell").findBy(text("Contributors")).$$("ul li").first().hover();
        sleep(2000);

        //проверка: первый Contributor Андрей Солнцев
        $(".Popover").shouldHave(text("Andrei Solntsev"));


    }

    // HOMEWORK 1
    // 1. Есть ли разница между $("h1 div"); и $("h1").$("div"); - может ли привести к тому что, поиск найдёт разные элементы?
    // Если может - приведите пример, когда.

    // Ответ: Разница есть. Первый вариант ищет любого потомка div у элемента h1, А Второй вариант ещет у первого h1 первого потомка с названиев div
    // Пример: <h1> <span>Заголовок</span> </h1>
    //         <h1> <div id="target">Нужный div</div> </h1>
    // Тогда:  $("h1 div") найдет <div id="target">
    // А  $("h1").$("div") упадет NoSuchElementException, так как первый h1 не содержит div

    // 2. Разработайте следующий автотест:

    @Test
    void SoftAssertionsShouldHaveJUnit5 () {
        Configuration.holdBrowserOpen = true;
        // - Откройте страницу Selenide в Github
        open("https://github.com/selenide/selenide");
        // - Перейдите в раздел Wiki проекта
        $("#wiki-tab").click();
        $("#wiki-pages-box").$(withText("Show")).click();
        // - Убедитесь, что в списке страниц (Pages) есть страница SoftAssertions
        $("#wiki-pages-box").shouldHave(text("SoftAssertions"));
        // - Откройте страницу SoftAssertions,
        $("#wiki-pages-box").$(byText("SoftAssertions")).click();
        // проверка: внутри есть пример кода для JUnit5
        $(".markdown-body").shouldHave(text("Using JUnit5 extend test class"));

    }

    // HOMEWORK 2
    // 1. На главной странице GitHub выберите меню Solutions -> Enterprize с помощью команды hover для Solutions.
    // Убедитесь что загрузилась нужная страница (например что заголовок - "Build like the best."
    @Test
    void testWithHover (){
        Configuration.holdBrowserOpen = true;
        open("https://github.com/");
        $$("button").findBy(text("Solutions")).hover();
        $$("a").findBy(text("Enterprises")).click();
        $("[data-testid='Hero']").shouldHave(text("The AI-powered developer platform for the agent-ready enterprise"));
    }
    // 2. Запрограммируйте Drag&Drop с помощью Selenide.actions()
    // - Откройте https://the-internet.herokuapp.com/drag_and_drop
    // - Перенесите прямоугольник А на место В
    // - Проверьте, что прямоугольники действительно поменялись
    // - В Selenide есть команда $(element).dragAndDrop($(to-element)), проверьте работает ли тест, если использовать её вместо actions()
@Test
    void testDragAndDrop(){
        Configuration.holdBrowserOpen = true;
        open("https://the-internet.herokuapp.com/drag_and_drop");
        $("#columns header").shouldBe(text("A"));
        $("#column-a").dragAndDrop(to($("#column-b")));
        $("#columns header").shouldBe(text("B"));
    }

    @Test
    void dragAndDropUsingActions() {
        Configuration.holdBrowserOpen = true;
        open("https://the-internet.herokuapp.com/drag_and_drop");

        var columnA = $("#column-a");
        var columnB = $("#column-b");

        $("#columns header").shouldBe(text("A"));
        actions()
                .clickAndHold(columnA)   // захватываем элемент A
                .moveToElement(columnB)   // перемещаем к элементу B
                .release()                // отпускаем
                .perform();               // выполняем все действия

        $("#columns header").shouldBe(text("B"));
    }


}

//withText - ищет часть строки, а byText() полное совпадение
