import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class baseApiTest {
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
    @Step("Delete Unique User")
    public void deleteCreatedUser(){
        given()
                .header("Authorization", accessToken) // добавляем в header авторизационный токен
                .header("Content-type", "application/json")
                .and()
                //.body(user)
                //.when()
                .delete("/api/auth/user")
                .then().statusCode(202);
    }
}

