package steps;

import builders.OrderBuilder;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    public static final String PATH_CREATE_ORDER = "/api/orders";

    @Step("create order with authorization")
    public static Response createOrderWithAuthorization(OrderBuilder orderBuilder, String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(orderBuilder)
                .when()
                .post(PATH_CREATE_ORDER)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("create order without authorization")
    public static Response createOrderWithoutAuthorization(OrderBuilder orderBuilder) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderBuilder)
                .when()
                .post(PATH_CREATE_ORDER)
                .then()
                .log().all()
                .extract().response();
    }
}
