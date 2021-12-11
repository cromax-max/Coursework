package ru.netology.app.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static java.time.Duration.ofSeconds;

public class HomePage {

    @Getter
    private Notification notification;

    public HomePage() {
        open("http://localhost:8080/");
        this.notification = page(Notification.class);
    }

    @FindBy(xpath = "//button[.='Купить']")
    private SelenideElement payButton;
    @FindBy(xpath = "//button[.='Купить в кредит']")
    private SelenideElement creditButton;

    public PayForm payment() {
        payButton.click();
        return page(PayForm.class);
    }

    public PayForm creditRequest() {
        creditButton.click();
        return page(PayForm.class);
    }

    public void checkOkNotification() {
        notification.getOkNotification()
                .shouldBe(visible, ofSeconds(10))
                .shouldHave(exactText("Успешно Операция одобрена Банком."));
        notification.getErrorNotification()
                .shouldBe(hidden);
    }

    public void checkErrorNotification() {
        notification.getErrorNotification()
                .shouldBe(visible, ofSeconds(10))
                .shouldHave(exactText("Ошибка Ошибка! Банк отказал в проведении операции."));
        notification.getOkNotification().shouldBe(hidden);
    }
}
