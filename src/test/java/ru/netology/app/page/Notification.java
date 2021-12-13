package ru.netology.app.page;

import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;

import java.time.Duration;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@AllArgsConstructor
public enum Notification {
    OK($(".notification_status_ok")),
    ERROR($(".notification_status_error"));

    private SelenideElement name;

    SelenideElement isVisible() {
        name.shouldBe(visible, Duration.ofSeconds(10));
        return name;
    }

    SelenideElement isHidden() {
        name.shouldBe(hidden);
        return name;
    }
}
