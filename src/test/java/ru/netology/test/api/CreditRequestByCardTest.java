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
import static ru.netology.helper.DbHelper.DbTable.CREDIT_REQUEST_ENTITY;
import static ru.netology.helper.DbHelper.DbTable.ORDER_ENTITY;
import static ru.netology.helper.DbHelper.*;

public class CreditRequestByCardTest {
    int orderEntityRowCountBefore;
    int creditRequestEntityRowCountBefore;
    PaymentCardDto dataCard;

    @BeforeAll
    static void setup() {
        createConnection();
    }

    @BeforeEach
    void getRowCountFromBd() {
        orderEntityRowCountBefore = getRowCount(ORDER_ENTITY.getTitle());
        creditRequestEntityRowCountBefore = getRowCount(CREDIT_REQUEST_ENTITY.getTitle());
    }

    @Test
    void shouldCreditRequestByApprovedCard() {
        dataCard = createApprovedCard();

        ApiAction.creditRequestByCard(dataCard)
                .assertThat().statusCode(200)
                .and()
                .body("status", equalTo("APPROVED"));
        var sqlResult = getLastRow(CREDIT_REQUEST_ENTITY.getTitle());

        assertThat(getRowCount(ORDER_ENTITY.getTitle()), greaterThan(orderEntityRowCountBefore));
        assertThat(getRowCount(CREDIT_REQUEST_ENTITY.getTitle()), greaterThan(creditRequestEntityRowCountBefore));
        assertThat(sqlResult.get("status"), equalTo("APPROVED"));
        assertFalse(sqlResult.containsValue(dataCard.getHolder()));
    }

    @Test
    void shouldCreditRequestByDeclinedCard() {
        dataCard = createDeclinedCard();

        ApiAction.creditRequestByCard(dataCard)
                .assertThat().statusCode(200)
                .and()
                .body("status", equalTo("DECLINED"));

        var sqlResult = getLastRow(CREDIT_REQUEST_ENTITY.getTitle());
        assertThat(getRowCount(ORDER_ENTITY.getTitle()), greaterThan(orderEntityRowCountBefore));
        assertThat(getRowCount(CREDIT_REQUEST_ENTITY.getTitle()), greaterThan(creditRequestEntityRowCountBefore));
        assertThat(sqlResult.get("status"), equalTo("DECLINED"));
        assertFalse(sqlResult.containsValue(dataCard.getHolder()));
    }

    @Test
    void shouldCreditRequestByInvalidCard() {
        dataCard = createInvalidCard();

        ApiAction.creditRequestByCard(dataCard)
                .assertThat().statusCode(200)
                .and()
                .body("message", equalTo("Invalid card number"));

        assertThat(getRowCount(ORDER_ENTITY.getTitle()), equalTo(orderEntityRowCountBefore));
        assertThat(getRowCount(CREDIT_REQUEST_ENTITY.getTitle()), equalTo(creditRequestEntityRowCountBefore));
    }

    @AfterAll
    static void resetSetup() {
        closeConnection();
    }
}
