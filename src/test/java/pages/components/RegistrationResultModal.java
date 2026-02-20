package pages.components;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationResultModal {
    public void verifyModalAppears(){
        $(".modal-dialog").shouldBe(appear);
    }
    public void verifyResult(String key, String value){
        $(".table-responsive").$(byText(key))
                .parent().shouldHave(text(value));
    }
}
