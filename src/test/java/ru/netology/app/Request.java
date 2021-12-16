package ru.netology.app;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.netology.helper.Card;

import static io.restassured.RestAssured.given;

public class Request {

    private static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(System.getProperty("baseUrl").isEmpty() ? RestAssured.baseURI : System.getProperty("baseUrl"))
//            .setPort(RestAssured.port)
            .setBasePath("/api/v1")
            .setAccept(ContentType.ANY)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static Response post(String path, Card data) {
        return given()
                .spec(REQ_SPEC)
                .body(data, ObjectMapperType.JACKSON_2)
                .when().post(path)
                .then().log().all()
                .extract().response();
    }
}
