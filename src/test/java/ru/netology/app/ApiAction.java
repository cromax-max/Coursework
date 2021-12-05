package ru.netology.app;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.netology.helper.PaymentCardDto;

import static io.restassured.RestAssured.given;

public class ApiAction {

    private static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setBasePath("/api/v1")
            .setAccept(ContentType.ANY)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static ValidatableResponse paymentByCard(PaymentCardDto data) {
        return postRequest("/pay", data)
                .then().log().all();
    }

    public static ValidatableResponse creditRequestByCard(PaymentCardDto data) {
        return postRequest("/credit", data)
                .then().log().all();
    }

    private static Response postRequest(String path, PaymentCardDto data) {
        return given()
                .spec(REQ_SPEC)
                .body(data)
                .when().post(path);
    }
}
