import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Test;
import java.util.Random;

public class ApiAuthLoginTest extends BaseApiTest {
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
        user.successfulLoginRegistUser(user,"/api/auth/login");
    }

    //2.2 Проверка логина с неверным логином и паролем.
    @Test
    @DisplayName("Check response body of /api/auth/login with invalid login and password")
    @Description("Verifying the response body of login with invalid login and password")
    public void checkInvalidLogin(){
        User invalidUser = new User("invalid3@yandex.ru", "wrongpassword", "test");
        invalidUser.respondPostBodyCheck(invalidUser, "/api/auth/login",401, false,"email or password are incorrect");
    }
}