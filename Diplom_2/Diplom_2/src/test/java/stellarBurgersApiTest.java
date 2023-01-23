import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class stellarBurgersApiTest {
    @Before
    public void setUp() {
        RestAssured.baseURI= "https://stellarburgers.nomoreparties.site/";
    }

    //1. Создание курьера
    @Test
    @DisplayName("Check status code of /api/auth/register")
    @Description("Verifying the creation of a unique user")
    public void checkUniqueUserCreation(){ //проверяем статус код при создании курьера
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@yandex.ru";
        User user = new User( email, "12345", "name");
        given()
                .header("Content-type", "application/json") // заполни header
                .and()
                .body(user)
                .when()
                .post("/api/auth/register")
                .then().statusCode(200);
    }
}
