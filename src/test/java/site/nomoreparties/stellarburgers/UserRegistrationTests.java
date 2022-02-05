package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRegistrationTests {

    private UserClient userClient;
    private UserCredentials userCredentials;
    private String accessToken;

    @Before
    public void setUp(){userClient = new UserClient();}

    @Test
    @DisplayName("Создание уникального пользователя")
    public void userCanBeCreatedTest(){
        User user = UserMethods.createRandomUser();
        ValidatableResponse response = UserClient.create(user);
        userCredentials = userCredentials.from(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse userInfo = UserClient.getInfo(accessToken);
        int statusCode = response.extract().statusCode();
        String expectedEmail = userCredentials.getEmail();
        String userEmail = userInfo.extract().path("user.email");
        assertEquals(200, statusCode);
        assertEquals(expectedEmail.toLowerCase(), userEmail);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void userCanNotBeCreatedWithTheSameCredentialsTest(){
        User user = UserMethods.createRandomUser();
        ValidatableResponse uniqUser = UserClient.create(user);
        ValidatableResponse existedUser = UserClient.create(user);
        int statusCode = existedUser.extract().statusCode();
        String message = existedUser.extract().path("message");
        assertEquals(403, statusCode);
        assertEquals("User already exists", message);
    }
}
