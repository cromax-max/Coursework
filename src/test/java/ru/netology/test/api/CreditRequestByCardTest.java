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
import static ru.netology.helper.DbHelper.DbTable.CREDIT_REQUEST_ENTITY;
import static ru.netology.helper.DbHelper.DbTable.ORDER_ENTITY;
import static ru.netology.helper.DbHelper.*;

public class CreditRequestByCardTest {
    int orderEntityRowCountBefore;
    int creditRequestEntityRowCountBefore;
    CardDto dataCard;

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

        var response = postRequest("/credit", dataCard);
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
        dataCard = createDeclinedCard();

        var response = postRequest("/credit", dataCard);
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
        dataCard = createInvalidCardWithoutNumber();

        var response = postRequest("/credit", dataCard);

        assertThat(response.statusCode(), is(400));
        assertThat(response.jsonPath()
                .get("error"), is("Missing card number"));
        assertThat(getRowCount(ORDER_ENTITY.getTitle()), equalTo(orderEntityRowCountBefore));
        assertThat(getRowCount(CREDIT_REQUEST_ENTITY.getTitle()), equalTo(creditRequestEntityRowCountBefore));
    }

    @AfterAll
    static void resetSetup() {
        closeConnection();
    }
}
