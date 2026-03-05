import builders.UserBuilder;
import data.TestData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import static data.TestData.EMAIL;
import static data.TestData.PASSWORD;
import static java.net.HttpURLConnection.HTTP_OK;
import static steps.UserSteps.deleteUser;
import static steps.UserSteps.loginUser;

public class BaseAPITest {

    @Before
    public void setUp() {
        RestAssured.baseURI = TestData.BASE_URI;
    }

    @After
    public void tearDown() {
        cleanupTestData();
    }

    private void cleanupTestData() {
        try {
            Response loginResponse = loginUser(new UserBuilder.Builder()
                    .withEmail(EMAIL)
                    .withPassword(PASSWORD)
                    .build()
            );

            if (loginResponse.getStatusCode() == HTTP_OK) {
                String accessToken = loginResponse.jsonPath().getString("accessToken");
                if (accessToken != null) {
                    deleteUser(accessToken);
                }
            }
        } catch (Exception e) {
        }
    }
}
