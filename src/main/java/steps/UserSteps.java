package steps;

import builders.UserBuilder;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserSteps {

    public static final String PATH_CREATE_USER = "/api/auth/register";
    public static final String PATH_DELETE_USER = "/api/auth/user";
    public static final String PATH_LOGIN_USER = "/api/auth/login";

    @Step("create user")
    public static Response createUser(UserBuilder userBuilder) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userBuilder)
                .when()
                .post(PATH_CREATE_USER)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("login user")
    public static Response loginUser(UserBuilder userBuilder) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userBuilder)
                .when()
                .post(PATH_LOGIN_USER)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("delete user")
    public static Response deleteUser(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(PATH_DELETE_USER);
    }

}
