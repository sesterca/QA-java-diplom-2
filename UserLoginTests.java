package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
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
    private String accessToken;

    @Before
    public void setUp(){
        user = createRandomUser();
        userClient = new UserClient();
        userClient.create(user);
        userCredentials = userCredentials.from(user);
    }

    @After
    public void tearDown(){
        userClient.delete(accessToken);}

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void userCanLoginWithValidParameters(){
        ValidatableResponse response = userClient.login(userCredentials);
        int statusCode = response.extract().statusCode();
        accessToken = response.extract().path("accessToken");
        boolean success = response.extract().path("success");
        assertEquals(200, statusCode);
        assertEquals(true, success);
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void userCanNotLoginWithInvalidParametersTest(){
        ValidatableResponse response = userClient.login(userCredentials.from(createRandomUser()));
        int statusCode = response.extract().statusCode();
        accessToken = response.extract().path("accessToken");
        String message = response.extract().path("message");
        assertEquals(401, statusCode);
        assertEquals("email or password are incorrect", message);
    }
}
