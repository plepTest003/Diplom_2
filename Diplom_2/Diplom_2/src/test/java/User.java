import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class User {
    private String email;
    private String password;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }
    User(String login, String password){
        this.email = login;
        this.password = password;
    }
    User(String login){
        this.email = login;
    }
    public String successfulLoginRegistUser(User user, String url) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(url)
                .then().statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .extract().path("accessToken");// забираем accessToken из ответа
    }
    public void respondPostBodyCheck(User user, String url, int statusCode, Boolean result, String bodyMessage) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(url)
                .then()
                .statusCode(statusCode)
                .and()
                .body("success", equalTo(result))
                .and()
                .body("message", equalTo(bodyMessage));
    }
}
