package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

import java.io.File;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("RegistrationForm")
public class StudentRegistrationFormTest {

    Faker faker = new Faker(Locale.ENGLISH);
    RegistrationPage registrationPage = new RegistrationPage();
    File file = new File("src/test/resources/test_picture.png");

    //String firstName = "Наталья";
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    String address = faker.address().fullAddress();

    String gender = "Female";
    String phone = "9082332380";
    String birthDay = "31";
    String birthMonth = "August";
    String birthYear = "2002";
    String subject1 = "Maths";
    String subject2 = "Arts";
    String hobby1 = "Sports";
    String hobby2 = "Reading";
    String hobby3 = "Music";
    String state = "NCR";
    String city = "Delhi";

    @BeforeAll
    static void beforehand() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
    }
    // Примечание:
    // Элемент	         Метод
    // input	         setValue()
    // textarea	         setValue()
    // select	         selectOption()
    // radio/checkbox	 click()
    @Test
    @DisplayName("Проверка корректных значений во всех полях ввода")
    @Description("Тест проверяет, что форма регистрации принимает корректные значения во всех полях")
    @Severity(SeverityLevel.CRITICAL) // BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL
    @Owner("Наталья Силаева")
    @Link(name = "Задача №1", url = "https://tracker.yandex.ru/AUTOTEST-001")
    void CorrectValueInAllInput(){
        SelenideLogger.addListener("allure", new AllureSelenide());
        registrationPage.openPage();
        registrationPage.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setPhone(phone)
                .setDateOfBird(birthDay, birthMonth, birthYear)
                .setSubject(subject1)
                .setSubject(subject2)
                .setHobbies(hobby1)
                .setHobbies(hobby2)
                .setHobbies(hobby3)
                .setAddress(address)
                .uploadFile(file)
                .setState(state)
                .setCity(city)
                .clickSubmit();

        // Проверка - введенные значения, отображаются корректно в таблице в модальном окне.
        // Отдельно проверяем соотвествие ключ-значение
        registrationPage.verifyResultModalAppears()
                .verifyResult("Student Name", firstName + " " + lastName)
                .verifyResult("Student Email", email)
                .verifyResult("Gender", gender)
                .verifyResult("Mobile", phone)
                .verifyResult("Date of Birth", birthDay + " " + birthMonth + "," + birthYear)
                .verifyResult("Subjects", subject1 + ", " + subject2)
                .verifyResult("Hobbies", hobby1 + ", " + hobby2 + ", " + hobby3)
                .verifyResult("Picture", "test_picture.png")
                .verifyResult("Address", address)
                .verifyResult("State and City", state + " " + city);

    }

    @Test
    @DisplayName("Проверка ввода только обязательных параметров")
    @Description("Тест проверяет, что форма регистрации разрешает подтверждение в случае заполнения только обязательных полей")
    @Severity(SeverityLevel.MINOR) // BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL
    @Owner("Наталья Силаева")
    @Link(name = "Задача №2", url = "https://tracker.yandex.ru/AUTOTEST-002")
    void inputReqParameters(){
        SelenideLogger.addListener("allure", new AllureSelenide());
        registrationPage.openPage();
        registrationPage.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setGender(gender)
                .setPhone(phone)
                .setDateOfBird(birthDay, birthMonth, birthYear)
                .setHobbies(hobby1)
                .clickSubmit();

        registrationPage.verifyResultModalAppears()
                .verifyResult("Student Name", firstName + " " + lastName)
                .verifyResult("Student Email", email)
                .verifyResult("Gender", gender)
                .verifyResult("Mobile", phone)
                .verifyResult("Date of Birth", birthDay + " " + birthMonth + "," + birthYear)
                .verifyResult("Hobbies", hobby1);
    }
    @Test
    void successTest1(){
        assertTrue(true);
    }
    @Test
    void successTest2(){
        assertTrue(true);
    }
    @Test
    void successTest3(){
        assertTrue(true);
    }

}
