import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.DisplayName;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class apiAuthUserTest extends baseApiTest {
    //3.Изменение данных пользователя
    //3.1 Проверяется изменение пользовательских данных с авторизацией
    @Test
    @DisplayName("Check status code of /api/auth/user with authorized user")
    @Description("Verifying the update of user data with authorization")
    public void checkAuthorizedUserUpdate(){
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
                .then()
                .extract().path("accessToken");// забираем accessToken из ответа
        User updatedUser = new User( email, "12345", "NewName");
        given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken) // добавляем в header авторизационный токен
                .and()
                .body(updatedUser)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("user.name", equalTo("NewName"));
        deleteCreatedUser();
    }

    //3.1 Проверяется изменение пользовательских данных без авторизации
    @Test
    @DisplayName("Check status code of /api/auth/user without authorization")
    @Description("Verifying the update of user data without authorization")
    public void checkUnauthorizedUserUpdate(){
        User updatedUser = new User("plep.test003@yandex.ru", "12345", "NewName");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(updatedUser)
                .when()
                .patch("/api/auth/user")
                .then()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }
}
