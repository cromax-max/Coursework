package ru.netology.app.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.*;
import static java.time.Duration.ofSeconds;

public class Notification {

    @FindBy(css = ".notification_status_ok")
    private SelenideElement okNotification;

    @FindBy(css = ".notification_status_error")
    private SelenideElement errorNotification;

    public void checkOkNotification() {
        okNotification
                .shouldBe(visible, ofSeconds(10))
                .shouldHave(exactText("Успешно Операция одобрена Банком."));
        errorNotification
                .shouldBe(hidden);
    }

    public void checkErrorNotification() {
        errorNotification
                .shouldBe(visible, ofSeconds(10))
                .shouldHave(exactText("Ошибка Ошибка! Банк отказал в проведении операции."));
        okNotification.shouldBe(hidden);
    }
}
