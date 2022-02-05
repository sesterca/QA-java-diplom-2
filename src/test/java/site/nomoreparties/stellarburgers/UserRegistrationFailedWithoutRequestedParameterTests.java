package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static site.nomoreparties.stellarburgers.UserMethods.createUser;

@RunWith(Parameterized.class)
public class UserRegistrationFailedWithoutRequestedParameterTests {

    //создать пользователя и не заполнить одно из обязательных полей
    private UserClient userClient;
    private User user;

    private final String email;
    private final String password;
    private final String name;

    public UserRegistrationFailedWithoutRequestedParameterTests(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getUserParameters(){
        return new Object[][]{
                {null, "1654sa", "anna"},
                {"sdfklj@yandex.ru", null, "anna"},
                {"sdfklj@yandex.ru", "1654sa", null}
        };
    }

    @Before
    public void setUp(){userClient = new UserClient();}

    @Test
    public void UserCreationFailedWithoutRequestedParameterTest(){
        user = createUser(email, password, name);
        ValidatableResponse response = UserClient.create(user);
        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(403, statusCode);
        assertEquals("Email, password and name are required fields", message);
    }
}
