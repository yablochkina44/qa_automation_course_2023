import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class StudentRegistrationFormTest {

    @BeforeAll
    static void beforehand() {
        Configuration.holdBrowserOpen = true;
        open("https://demoqa.com/automation-practice-form");
        Configuration.browserSize = "1920x1080";
    }
    // Примечание:
    // Элемент	         Метод
    // input	         setValue()
    // textarea	         setValue()
    // select	         selectOption()
    // radio/checkbox	 click()
    @Test
    void CorrectValueInAllInput(){

        $("#firstName").setValue("Наталья");
        $("#lastName").setValue("Силаева");
        $("#userEmail").setValue("regis@gmail.com");
        // radioButton
        $("#genterWrapper").$(byText("Female")).click();

        $("#userNumber").setValue("9082332380");
        // Указываем значение в react data-picker
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").selectOption("2002");
        $(".react-datepicker__month-select").selectOption("August");
        $$(".react-datepicker__day--031")
                .filter(not(cssClass("react-datepicker__day--outside-month"))) //фильтрую предыдущий месяц, т.к подхватывается 31 число от предыдущего месяца
                .first()
                .click();
        // Элемент  react-select / autocomplete. Список значений появляется после ввода текста + нужно подтверждение, напр. Enter
        $("#subjectsInput").setValue("Maths").pressEnter();
        $("#subjectsInput").setValue("Arts").pressEnter();
        // checkbox
        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("#hobbiesWrapper").$(byText("Music")).click();
        //Загрузка файла (изображение)
        File file = new File("src/test/resources/test_picture.png");
        $("#uploadPicture").uploadFile(file);

        $("#currentAddress").setValue("Нижний Новгород, Ул. Максима Горького");
        // два react-select. Зависимые выпадающие списки.(City) зависит от (State)
        $("#react-select-3-input").setValue("NCR").pressEnter();
        $("#react-select-4-input").setValue("Delhi").pressEnter();

        $("#submit").click();


        //Проверка - введенные значения, отображаются корректно в таблице в модальном окне. Отдельно проверяем соотвествие ключ-значение
        $(".modal-content").shouldBe(visible);
        $(".table-responsive").$(byText("Student Name"))
                .parent().shouldHave(text("Наталья Силаева"));
        $(".table-responsive").$(byText("Student Email"))
                .parent().shouldHave(text("regis@gmail.com"));
        $(".table-responsive").$(byText("Gender"))
                .parent().shouldHave(text("Female"));
        $(".table-responsive").$(byText("Mobile"))
                .parent().shouldHave(text("9082332380"));
        $(".table-responsive").$(byText("Date of Birth"))
                .parent().shouldHave(text("31 August,2002"));
        $(".table-responsive").$(byText("Subjects"))
                .parent().shouldHave(text("Maths, Arts"));
        $(".table-responsive").$(byText("Hobbies"))
                .parent().shouldHave(text("Sports, Reading, Music"));
        $(".table-responsive").$(byText("Picture"))
                .parent().shouldHave(text("test_picture.png"));
        $(".table-responsive").$(byText("Address"))
                .parent().shouldHave(text("Нижний Новгород, Ул. Максима Горького"));
        $(".table-responsive").$(byText("State and City"))
                .parent().shouldHave(text("NCR Delhi"));
    }

}
