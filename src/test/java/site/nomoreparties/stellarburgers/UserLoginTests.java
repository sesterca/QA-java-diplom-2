package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static site.nomoreparties.stellarburgers.UserMethods.createRandomUser;

public class UserLoginTests {

    private User user;
    private UserClient userClient;
    private UserCredentials userCredentials;

    @Before
    public void setUp(){
        user = createRandomUser();
        userClient = new UserClient();
        userClient.create(user);
        userCredentials = userCredentials.from(user);
    }

    //логин под существующим пользователем
    @Test
    public void userCanLoginWithValidParameters(){
        ValidatableResponse response = userClient.login(userCredentials);
        int statusCode = response.extract().statusCode();
        boolean success = response.extract().path("success");
        assertEquals(200, statusCode);
        assertEquals(true, success);
    }

    //логин с неверным логином и паролем
    @Test
    public void userCanNotLoginWithInvalidParameters(){
        ValidatableResponse response = userClient.login(userCredentials.from(createRandomUser()));
        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        assertEquals(401, statusCode);
        assertEquals("email or password are incorrect", message);
    }
}
