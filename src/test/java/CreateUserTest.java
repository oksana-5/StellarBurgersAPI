import builders.UserBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.UserSteps.createUser;

public class CreateUserTest extends BaseAPITest {

    @Test
    @DisplayName("create user success test")
    @Description("valid user creation returns 200 ok and tokens")
    public void createUserSuccessTest() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .build();

        createUser(userBuilder)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user", notNullValue())
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

    }

    @Test
    @DisplayName("create existing user test")
    @Description("invalid user creation returns 403 forbidden")
    public void createExistingUserTest() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .build();

        createUser(userBuilder);
        createUser(userBuilder)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("create user without email test")
    @Description("invalid user creation returns 403 forbidden")
    public void createUserWithoutEmailTest() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withPassword(PASSWORD)
                .withName(NAME)
                .build();

        createUser(userBuilder)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("create user without password test")
    @Description("invalid user creation returns 403 forbidden")
    public void createUserWithoutPasswordTest() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withName(NAME)
                .build();

        createUser(userBuilder)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("create user without name test")
    @Description("invalid user creation returns 403 forbidden")
    public void createUserWithoutNameTest() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .build();

        createUser(userBuilder)
                .then()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

}
