import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.Test;
import java.util.Random;
public class ApiAuthRegisterTest extends BaseApiTest {

    //1. Создание пользователя
    //1.1 Проверка создания уникального пользователя
    @Test
    @DisplayName("Check status code of /api/auth/register with unique user")
    @Description("Verifying the creation of a unique user")
    public void checkUniqueUserCreation(){ //проверяем статус код при создании курьера
        Random random = new Random();
        String email = "something" + random.nextInt(10000000) + "@test.com";
        User user = new User( email, "12345", "name");
        user.successfulLoginRegistUser(user,"/api/auth/register");
    }

    //1.2 Проверка создания пользователя, который уже зарегистрирован
    @Test
    @DisplayName("Check status code of /api/auth/register with existing user")
    @Description("Checking the creation of a user who is already registered")
    public void checkExistUserCreation(){ //проверяем статус код при создании курьера
        User user = new User( "plep.test003@yandex.ru", "12345", "Polina");
        user.respondPostBodyCheck(user, "/api/auth/register", 403, false, "User already exists");
    }

    //1.2 Проверка создания  пользователя при не заполненом одном из обязательных полей.
    @Test
    @DisplayName("Check status code of /api/auth/register without one of the required fields")
    @Description("Checking the creation of a user without one of the required fields.")
    public void checkUnsuccessfulUserCreation(){ //проверяем статус код при создании курьера
        User user = new User( "12345", "test");
        user.respondPostBodyCheck(user, "/api/auth/register", 403, false, "Email, password and name are required fields");
    }
}
