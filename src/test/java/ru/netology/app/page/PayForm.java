package ru.netology.app.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.netology.helper.Card;

public class PayForm {

    @FindBy(css = "[placeholder='0000 0000 0000 0000']")
    private SelenideElement number;
    @FindBy(css = "[placeholder='08']")
    private SelenideElement month;
    @FindBy(css = "[placeholder='22']")
    private SelenideElement year;
    @FindBy(css = "fieldset > div:nth-child(3) input")
    private SelenideElement holder;
    @FindBy(css = "[placeholder='999']")
    private SelenideElement cvc;
    @FindBy(xpath = "//button[contains(.,'Продолжить')]")
    private SelenideElement resumeButton;

    public void completeForm(Card card) {
        number.val(card.getNumber());
        month.val(card.getMonth());
        year.val(card.getYear());
        holder.val(card.getHolder());
        cvc.val(card.getCvc());
        resumeButton.click();
    }
    // public void enter...(String value){} *to validate fields*
}
