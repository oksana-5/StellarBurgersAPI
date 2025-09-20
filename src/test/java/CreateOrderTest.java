import builders.OrderBuilder;
import builders.UserBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Arrays;

import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.createOrderWithAuthorization;
import static steps.OrderSteps.createOrderWithoutAuthorization;
import static steps.UserSteps.createUser;

public class CreateOrderTest extends BaseAPITest {

    @Test
    @DisplayName("create order with ingredients test")
    @Description("valid order creation returns 200 ok and owner information")
    public void createOrderWithIngredientsTest() {
        Response createResponse = createUser(new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .build()
        );

        String accessToken = createResponse.jsonPath().getString("accessToken");

        OrderBuilder orderBuilder = new OrderBuilder.Builder()
                .withIngredients(INGREDIENTS)
                .build();

        createOrderWithAuthorization(orderBuilder, accessToken)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.owner", notNullValue());
    }

    @Test
    @DisplayName("create order without authorization test")
    @Description("valid order creation returns 200 ok")
    public void createOrderWithoutAuthorizationTest() {
        OrderBuilder orderBuilder = new OrderBuilder.Builder()
                .withIngredients(INGREDIENTS)
                .build();

        createOrderWithoutAuthorization(orderBuilder)
                .then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("create order without ingredients test")
    @Description("invalid order creation returns 400 bad request")
    public void createOrderWithoutIngredientsTest() {
        Response createResponse = createUser(new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .build()
        );

        String accessToken = createResponse.jsonPath().getString("accessToken");

        OrderBuilder orderBuilder = new OrderBuilder.Builder()
                .withIngredients(null)
                .build();

        createOrderWithAuthorization(orderBuilder, accessToken)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("create order with invalid ingredients test")
    @Description("invalid order creation returns 500 internal error")
    public void createOrderWithInvalidIngredientsTest() {
        Response createResponse = createUser(new UserBuilder.Builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .build()
        );

        String accessToken = createResponse.jsonPath().getString("accessToken");

        OrderBuilder orderBuilder = new OrderBuilder.Builder()
                .withIngredients(Arrays.asList("invalid", "ingredients"))
                .build();

        createOrderWithAuthorization(orderBuilder, accessToken)
                .then()
                .statusCode(HTTP_INTERNAL_ERROR);
    }


}
