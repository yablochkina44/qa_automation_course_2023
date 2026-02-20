package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

import java.io.File;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class StudentRegistrationFormTest {
    RegistrationPage registrationPage = new RegistrationPage();
    File file = new File("src/test/resources/test_picture.png");
    String firstName = "Наталья";
    String lastName = "Силаева";
    String email = "regis@gmail.com";
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
    String address = "Нижний Новгород, Ул. Максима Горького";
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
    void CorrectValueInAllInput(){
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
                .uploadFile(file)
                .setAddress(address)
                .setCity(city)
                .setState(state)
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

}
