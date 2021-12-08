package ru.netology.test.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.helper.CardDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.netology.app.Request.postRequest;
import static ru.netology.helper.DataHelper.*;
import static ru.netology.helper.DbHelper.DbTable.ORDER_ENTITY;
import static ru.netology.helper.DbHelper.DbTable.PAYMENT_ENTITY;
import static ru.netology.helper.DbHelper.*;

public class PaymentByCardTest {
    int orderEntityRowCountBefore;
    int paymentEntityRowCountBefore;
    CardDto dataCard;

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

        var response = postRequest("/pay", dataCard);
        var sqlResult = getLastRow(PAYMENT_ENTITY.getTitle());

        assertThat(response.statusCode(), is(200));
        assertThat(response.jsonPath()
                .get("status"), is("APPROVED"));
        assertThat(getRowCount(ORDER_ENTITY.getTitle()), greaterThan(orderEntityRowCountBefore));
        assertThat(getRowCount(PAYMENT_ENTITY.getTitle()), greaterThan(paymentEntityRowCountBefore));
        assertThat(sqlResult, hasEntry("status", "APPROVED"));
        assertThat(sqlResult, hasEntry("amount", "45000"));
        assertThat(sqlResult, not(hasValue(dataCard.getNumber())));
    }

    @Test
    void shouldPaymentByDeclinedCard() {
        dataCard = createDeclinedCard();

        var response = postRequest("/pay", dataCard);
        var sqlResult = getLastRow(PAYMENT_ENTITY.getTitle());

        assertThat(response.statusCode(), is(200));
        assertThat(response.jsonPath()
                .get("status"), is("DECLINED"));
        assertThat(getRowCount(ORDER_ENTITY.getTitle()), greaterThan(orderEntityRowCountBefore));
        assertThat(getRowCount(PAYMENT_ENTITY.getTitle()), greaterThan(paymentEntityRowCountBefore));
        assertThat(sqlResult, hasEntry("status", "DECLINED"));
        assertThat(sqlResult, not(hasValue(dataCard.getNumber())));
    }

    @Test
    void shouldPaymentByInvalidCard() {
        dataCard = createInvalidCardWithoutNumber();

        var response = postRequest("/pay", dataCard);

        assertThat(response.statusCode(), is(400));
        assertThat(response.jsonPath()
                .get("error"), is("Missing card number"));
        assertThat(getRowCount(ORDER_ENTITY.getTitle()), equalTo(orderEntityRowCountBefore));
        assertThat(getRowCount(PAYMENT_ENTITY.getTitle()), equalTo(paymentEntityRowCountBefore));
    }

    @AfterAll
    static void resetSetup() {
        closeConnection();
    }
}