import builders.UserBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.UserSteps.createUser;
import static steps.UserSteps.loginUser;
import static java.net.HttpURLConnection.HTTP_OK;

public class LoginUserTest extends BaseAPITest {

    @Before
    public void createUserSuccess() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .build();

        createUser(userBuilder);
    }

    @Test
    @DisplayName("login user success test")
    @Description("valid credentials login returns 200 ok")
    public void loginUserSuccessTest() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .build();

        loginUser(userBuilder)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user", notNullValue())
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("login user with wrong email test")
    @Description("invalid credentials login returns 401 unauthorized")
    public void loginUserWithWrongEmailTest() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withEmail(EMAIL + "test")
                .withPassword(PASSWORD)
                .build();

        loginUser(userBuilder)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("login user with wrong password test")
    @Description("invalid credentials login returns 401 unauthorized")
    public void loginUserWithWrongPasswordTest() {
        UserBuilder userBuilder = new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD + "test")
                .build();

        loginUser(userBuilder)
                .then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
