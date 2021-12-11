package ru.netology.app.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

@Getter
public class Notification {

    @FindBy(css = ".notification_status_ok")
    private SelenideElement okNotification;

    @FindBy(css = ".notification_status_error")
    private SelenideElement errorNotification;
}
