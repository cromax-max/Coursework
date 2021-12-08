package ru.netology.app;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.netology.helper.CardDto;

import static io.restassured.RestAssured.given;

public class Request {

    private static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setBasePath("/api/v1")
            .setAccept(ContentType.ANY)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static Response postRequest(String path, CardDto data) {
        return given()
                .spec(REQ_SPEC)
                .body(data)
                .when().post(path)
                .then().log().all()
                .extract().response();
    }
}
