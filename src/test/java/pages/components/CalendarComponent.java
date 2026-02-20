package pages.components;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CalendarComponent {
    public void setDate(String day, String month, String year){
        $(".react-datepicker__year-select").selectOption(year);
        $(".react-datepicker__month-select").selectOption(month);
        // либо $(".react-datepicker__month-select").selectOptionByValue("7");
        $$(".react-datepicker__day--0" + day)
                .filter(not(cssClass("react-datepicker__day--outside-month"))) //фильтрую предыдущий месяц, т.к подхватывается 31 число от предыдущего месяца
                .first()
                .click();
    }
}

