package ru.netology.test.gui;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.app.page.HomePage;
import ru.netology.app.page.Notification;
import ru.netology.helper.Card;

import static com.codeborne.selenide.Selenide.page;


public class PayTest {

    private HomePage page;

    @BeforeEach
    void openPage() {
        page = page(HomePage.class);
    }

    @Test
    void shouldPaymentByApprovedCard() {
        page
                .payment()
                .completeForm(Card.APPROVED);
        page
                .check(Notification.OK);
    }

    @Test
    void shouldPaymentByDeclinedCard() {
        page
                .payment()
                .completeForm(Card.DECLINED);
        page
                .check(Notification.ERROR);
    }

    @Test
    void shouldPaymentByInvalidCard() {
        page
                .payment()
                .completeForm(Card.INVALID);
        page
                .check(Notification.ERROR);
    }

    @Test
    void shouldCreditRequestByApprovedCard() {
        page
                .creditRequest()
                .completeForm(Card.APPROVED);
        page
                .check(Notification.OK);
    }

    @Test
    void shouldCreditRequestByDeclinedCard() {
        page
                .creditRequest()
                .completeForm(Card.DECLINED);
        page
                .check(Notification.ERROR);
    }

    @Test
    void shouldCreditRequestByInvalidCard() {
        page
                .creditRequest()
                .completeForm(Card.INVALID);
        page
                .check(Notification.ERROR);
    }

    @AfterAll
    static void close() {
        Selenide.closeWebDriver();
    }
}
