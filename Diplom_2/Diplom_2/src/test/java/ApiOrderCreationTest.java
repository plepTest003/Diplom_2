
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiOrderCreationTest extends BaseApiTest {
    //4.Создание заказа
    //4.1. Создание заказа с авторизацией:
    //4.3. Создание заказа с ингредиентами:
    @Test
    @DisplayName("Check status code of /api/orders with auth and ingredients")
    @Description("Verifying the creation of order with auth and ingredients")
    public void checkOrderCreationWithAuthAndIngredients(){
        // залогиниваемся
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@test.com";
        User user = new User( email, "12345", "name");
        creatUniqueUser(user);
        accessToken = user.successfulLoginRegistUser(user,"/api/auth/login");
        // создаем заказ
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .and()
                .body("{\"ingredients\":[\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6c\"]}")
                .when()
                .post("/api/orders")
                .then().statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .extract().response();
        int orderNumber = response.path("order.number");
        System.out.println("Order created successfully. Order number: " + orderNumber);
    }

    //4.2. Создание заказа без авторизации:
    @Test
    @DisplayName("Check status code of /api/orders without auth")
    @Description("Verifying the creation of order without auth")
    public void checkOrderCreationWithoutAuth(){
        given()
                .header("Content-type", "application/json")
                .and()
                .body("{\"ingredients\":[\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6c\"]}")
                .when()
                .post("/api/orders")
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    //4.4. Создание заказа без ингредиентов
    //4.5 с неверным хешем ингредиентов
    @Test
    @DisplayName("Check status code of /api/orders without Ingredients")
    @Description("Verifying the creation of order without Ingredients")
    public void checkOrderCreationWithoutIngredients() {
        given()
                .header("Content-type", "application/json")
                .and()
                .body("{\"ingredients\":[]}")
                .when()
                .post("/api/orders")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }
}
