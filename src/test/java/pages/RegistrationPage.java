package pages;

import pages.components.CalendarComponent;
import pages.components.RegistrationResultModal;

import java.io.File;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {

    CalendarComponent calendarComponent = new CalendarComponent();
    RegistrationResultModal registrationResultModal = new RegistrationResultModal();
    public RegistrationPage openPage(){
        open("https://demoqa.com/automation-practice-form");
        return this;
    }

    public RegistrationPage setFirstName(String firstName){
        $("#firstName").setValue(firstName);
        return this;
    }
    public RegistrationPage setLastName(String lastName){
        $("#lastName").setValue(lastName);
        return this;
    }
    public RegistrationPage setEmail(String email){
        $("#userEmail").setValue(email);
        return this;
    }
    public RegistrationPage setGender(String gender){
        $("#genterWrapper").$(byText(gender)).click(); // radioButton
        return this;
    }
    public RegistrationPage setPhone(String phone){
        $("#userNumber").setValue(phone);
        return this;
    }
    public RegistrationPage setDateOfBird(String day, String month, String year){
        // Указываем значение в react data-picker
        $("#dateOfBirthInput").click();
        calendarComponent.setDate(day, month, year);
        return this;
    }
    public RegistrationPage setSubject(String subject){
        // Элемент react-select / autocomplete.
        // Список значений появляется после ввода текста + нужно подтверждение, напр. Enter
        $("#subjectsInput").setValue(subject).pressEnter();
        return this;
    }
    public RegistrationPage setHobbies(String hobbies){
        // checkbox
        $("#hobbiesWrapper").$(byText(hobbies)).click();
        return this;
    }
    public RegistrationPage uploadFile(File file){
        $("#uploadPicture").uploadFile(file);
        return this;
    }
    public RegistrationPage setAddress(String address){
        $("#currentAddress").setValue(address);
        return this;
    }
    public RegistrationPage setCity(String city){
        // два react-select. Зависимые выпадающие списки.(City) зависит от (State)
        $("#react-select-3-input").setValue(city).pressEnter();
        return this;
    }
    public RegistrationPage setState(String state){
        $("#react-select-4-input").setValue(state).pressEnter();
        return this;
    }
    public void clickSubmit(){
        $("#submit").click();
    }

    public RegistrationPage verifyResultModalAppears(){
        registrationResultModal.verifyModalAppears();
        return this;
    }
    public RegistrationPage verifyResult(String key, String value){
        registrationResultModal.verifyResult(key, value);
        return this;
    }


}
