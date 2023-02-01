import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;


import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class apiAuthRegisterTest extends baseApiTest {

    //1. Создание пользователя
    //1.1 Проверка создания уникального пользователя
    @Test
    @DisplayName("Check status code of /api/auth/register with unique user")
    @Description("Verifying the creation of a unique user")
    public void checkUniqueUserCreation(){ //проверяем статус код при создании курьера
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@test.com";
        User user = new User( email, "12345", "name");
        accessToken = given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(user)
                .when()
                .post("/api/auth/register")
                .then().statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .extract().path("accessToken");// забираем accessToken из ответа
        deleteCreatedUser();
    }

    //1.2 Проверка создания пользователя, который уже зарегистрирован
    @Test
    @DisplayName("Check status code of /api/auth/register with existing user")
    @Description("Checking the creation of a user who is already registered")
    public void checkExistUserCreation(){ //проверяем статус код при создании курьера
        User user = new User( "plep.test003@yandex.ru", "12345", "Polina");
        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(user)
                .when()
                .post("/api/auth/register")
                .then().statusCode(403)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("User already exists"));
    }

    //1.2 Проверка создания  пользователя при не заполненом одном из обязательных полей.
    @Test
    @DisplayName("Check status code of /api/auth/register without one of the required fields")
    @Description("Checking the creation of a user without one of the required fields.")
    public void checkUnsuccessfulUserCreation(){ //проверяем статус код при создании курьера
        User user = new User( "12345", "test");
        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(user)
                .when()
                .post("/api/auth/register")
                .then().statusCode(403)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
