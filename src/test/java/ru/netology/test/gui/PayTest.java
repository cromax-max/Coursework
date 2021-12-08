package ru.netology.test.gui;

import org.junit.jupiter.api.*;
import ru.netology.app.page.HomePage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.helper.DataHelper.*;

public class PayTest {

    HomePage page;

    @BeforeEach
    void openPage() {
        page = page(HomePage.class);
    }

    @Test
    void shouldPaymentByApprovedCard() {
        page
                .payment()
                .completeForm(createApprovedCard());
        page.notification.checkOkNotification();
    }

    @Test
    void shouldPaymentByDeclinedCard() {
        page
                .payment()
                .completeForm(createDeclinedCard());
        page.notification.checkErrorNotification();
    }

    @Test
    void shouldPaymentByInvalidCard() {
        page
                .payment()
                .completeForm(createInvalidCard());
        page.notification.checkErrorNotification();
    }

    @Test
    void shouldCreditRequestByApprovedCard() {
        page
                .creditRequest()
                .completeForm(createApprovedCard());
        page.notification.checkOkNotification();
    }

    @Test
    void shouldCreditRequestByDeclinedCard() {
        page
                .creditRequest()
                .completeForm(createDeclinedCard());
        page.notification.checkErrorNotification();
    }

    @Test
    void shouldCreditRequestByInvalidCard() {
        page
                .creditRequest()
                .completeForm(createInvalidCard());
        page.notification.checkErrorNotification();
    }
}
