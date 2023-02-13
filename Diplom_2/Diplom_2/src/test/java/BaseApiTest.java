import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import static io.restassured.RestAssured.given;

public class BaseApiTest {
    public static String accessToken;
    @Before
    public void setUp() {
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site/";
    }

    @Step("Creat Unique User")
    public void creatUniqueUser(User user){
        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(user)
                .when()
                .post("/api/auth/register")
                .then().statusCode(200);
    }
    @AfterEach
    public void deleteCreatedUser(){
        if (accessToken != null) { // проверяем, что accessToken не равен null
            given()
                    .header("Authorization", accessToken)
                    .header("Content-type", "application/json")
                    .delete("/api/auth/user")
                    .then()
                    .statusCode(202);
        }
    }
}
