package ru.netology.app.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static ru.netology.app.page.Notification.ERROR;
import static ru.netology.app.page.Notification.OK;

public class HomePage {

    public HomePage() {
        open(System.getProperty("baseUrl")); //Default (Selenide)value: http://localhost:8080
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

    public void check(Notification notification) {
        switch (notification.ordinal()) {
            case (0):
                notification.isVisible().shouldHave(exactText("Успешно Операция одобрена Банком."));
                ERROR.isHidden();
                break;
            case (1):
                notification.isVisible().shouldHave(exactText("Ошибка Ошибка! Банк отказал в проведении операции."));
                OK.isHidden();
                break;
        }
    }
}
