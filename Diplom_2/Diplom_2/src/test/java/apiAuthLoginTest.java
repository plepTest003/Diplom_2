import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Test;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class apiAuthLoginTest extends baseApiTest {
    //2.Логин пользователя
    //2.1 Проверка логина под существующим пользователем
    @Test
    @DisplayName("Check status code of /api/auth/login with existing user")
    @Description("Verifying the login of an existing user")
    public void checkExistingUserLogin(){
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@test.com";
        User user = new User( email, "12345", "name");
        creatUniqueUser(user);
        accessToken = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/login")
                .then().statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .extract().path("accessToken");// забираем accessToken из ответа
        deleteCreatedUser();
    }

    //2.2 Проверка логина с неверным логином и паролем.
    @Test
    @DisplayName("Check response body of /api/auth/login with invalid login and password")
    @Description("Verifying the response body of login with invalid login and password")
    public void checkInvalidLogin(){
        User invalidUser = new User("invalid3@yandex.ru", "wrongpassword", "test");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(invalidUser)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }
}