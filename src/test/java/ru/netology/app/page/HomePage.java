package ru.netology.app.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class HomePage {

    @Getter private Notification notification;

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
}
