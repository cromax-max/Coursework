package ru.netology.test.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.app.ApiAction;
import ru.netology.helper.PaymentCardDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.netology.helper.DataHelper.*;
import static ru.netology.helper.DbHelper.DbTable.ORDER_ENTITY;
import static ru.netology.helper.DbHelper.DbTable.PAYMENT_ENTITY;
import static ru.netology.helper.DbHelper.*;

public class PaymentByCardTest {
    int orderEntityRowCountBefore;
    int paymentEntityRowCountBefore;
    PaymentCardDto dataCard;

    @BeforeAll
    static void setup() {
        createConnection();
    }

    @BeforeEach
    void getRowCountFromBd() {
        orderEntityRowCountBefore = getRowCount(ORDER_ENTITY.getTitle());
        paymentEntityRowCountBefore = getRowCount(PAYMENT_ENTITY.getTitle());
    }

    @Test
    void shouldPaymentByApprovedCard() {
        dataCard = createApprovedCard();

        ApiAction.paymentByCard(dataCard)
                .assertThat().statusCode(200)
                .and()
                .body("status", equalTo("APPROVED"));
        var sqlResult = getLastRow(PAYMENT_ENTITY.getTitle());

        assertThat(getRowCount(ORDER_ENTITY.getTitle()), greaterThan(orderEntityRowCountBefore));
        assertThat(getRowCount(PAYMENT_ENTITY.getTitle()), greaterThan(paymentEntityRowCountBefore));
        assertThat(sqlResult.get("status"), equalTo("APPROVED"));
        assertThat(sqlResult.get("amount"), equalTo("45000"));
        assertFalse(sqlResult.containsValue(dataCard.getHolder()));
    }

    @Test
    void shouldPaymentByDeclinedCard() {
        dataCard = createDeclinedCard();

        ApiAction.paymentByCard(dataCard)
                .assertThat().statusCode(200)
                .and()
                .body("status", equalTo("DECLINED"));
        var sqlResult = getLastRow(PAYMENT_ENTITY.getTitle());

        assertThat(getRowCount(ORDER_ENTITY.getTitle()), greaterThan(orderEntityRowCountBefore));
        assertThat(getRowCount(PAYMENT_ENTITY.getTitle()), greaterThan(paymentEntityRowCountBefore));
        assertThat(sqlResult.get("status"), equalTo("DECLINED"));
        assertFalse(sqlResult.containsValue(dataCard.getHolder()));
    }

    @Test
    void shouldPaymentByInvalidCard() {
        dataCard = createInvalidCard();

        ApiAction.paymentByCard(dataCard)
                .assertThat().statusCode(200)
                .and()
                .body("message", equalTo("Invalid card number"));

        assertThat(getRowCount(ORDER_ENTITY.getTitle()), equalTo(orderEntityRowCountBefore));
        assertThat(getRowCount(PAYMENT_ENTITY.getTitle()), equalTo(paymentEntityRowCountBefore));
    }

    @AfterAll
    static void resetSetup() {
        closeConnection();
    }
}