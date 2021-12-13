package ru.netology.test.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.app.Request;
import ru.netology.helper.Card;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.netology.helper.DbHelper.DbTable.CREDIT_REQUEST_ENTITY;
import static ru.netology.helper.DbHelper.DbTable.ORDER_ENTITY;
import static ru.netology.helper.DbHelper.*;

public class CreditRequestByCardTest {
    int orderEntityRowCountBefore;
    int creditRequestEntityRowCountBefore;
    Card dataCard;

    @BeforeAll
    static void setup() {
        createConnection();
    }

    @BeforeEach
    void getRowCountFromBd() {
        orderEntityRowCountBefore = getRowCount(ORDER_ENTITY.getTitle());
        creditRequestEntityRowCountBefore = getRowCount(CREDIT_REQUEST_ENTITY.getTitle());
    }

    @AfterAll
    static void resetSetup() {
        closeConnection();
    }

    @Test
    void shouldCreditRequestByApprovedCard() {
        dataCard = Card.APPROVED;

        var response = Request.post("/credit", dataCard);
        var sqlResult = getLastRow(CREDIT_REQUEST_ENTITY.getTitle());

        assertThat(response.statusCode(), is(200));
        assertThat(response.jsonPath()
                .get("status"), is("APPROVED"));
        assertThat(getRowCount(ORDER_ENTITY.getTitle()), greaterThan(orderEntityRowCountBefore));
        assertThat(getRowCount(CREDIT_REQUEST_ENTITY.getTitle()), greaterThan(creditRequestEntityRowCountBefore));
        assertThat(sqlResult, hasEntry("status", "APPROVED"));
        assertThat(sqlResult, not(hasValue(dataCard.getNumber())));
    }

    @Test
    void shouldCreditRequestByDeclinedCard() {
        dataCard = Card.DECLINED;

        var response = Request.post("/credit", dataCard);
        var sqlResult = getLastRow(CREDIT_REQUEST_ENTITY.getTitle());

        assertThat(response.statusCode(), is(200));
        assertThat(response.jsonPath()
                .get("status"), is("DECLINED"));
        assertThat(getRowCount(ORDER_ENTITY.getTitle()), greaterThan(orderEntityRowCountBefore));
        assertThat(getRowCount(CREDIT_REQUEST_ENTITY.getTitle()), greaterThan(creditRequestEntityRowCountBefore));
        assertThat(sqlResult, hasEntry("status", "DECLINED"));
        assertThat(sqlResult, not(hasValue(dataCard.getNumber())));
    }

    @Test
    void shouldCreditRequestByInvalidCard() {
        dataCard = Card.INVALID_WITHOUT_NUMBER;

        var response = Request.post("/credit", dataCard);

        assertThat(response.statusCode(), is(400));
        assertThat(response.jsonPath()
                .get("error"), is("Missing card number"));
        assertThat(getRowCount(ORDER_ENTITY.getTitle()), equalTo(orderEntityRowCountBefore));
        assertThat(getRowCount(CREDIT_REQUEST_ENTITY.getTitle()), equalTo(creditRequestEntityRowCountBefore));
    }
}
