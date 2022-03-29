package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static site.nomoreparties.stellarburgers.UserMethods.createRandomUser;

public class UserNonAuthorizedChangesTests {

    private UserClient userClient;
    private User user;
    private UserCredentials userCredentials;
    private String accessToken;

    @Before
    public void setUp(){
        user = createRandomUser();
        userClient = new UserClient();
        ValidatableResponse register = userClient.create(user);
        accessToken = register.extract().path("accessToken");
        userCredentials = userCredentials.from(user);}

    @After
    public void tearDown(){
        userClient.delete(accessToken);}

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void userCanNotChangeCredentialsWithoutLoginTest(){
        ValidatableResponse change = userClient.changeNotLogin(user);
        int statusCode = change.extract().statusCode();
        String message = change.extract().path("message");
        assertEquals(401, statusCode);
        assertEquals("You should be authorised", message);
    }
}

