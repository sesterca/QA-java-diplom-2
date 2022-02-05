package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static site.nomoreparties.stellarburgers.UserMethods.createRandomUser;

@RunWith(Parameterized.class)
public class UserAuthorizedChangesTests {

    private UserClient userClient;
    private User user;
    private final User changedUser;
    private UserMethods userMethods;
    private UserCredentials userCredentials;
    private String accessToken;

    public UserAuthorizedChangesTests(User changedUser){
        this.changedUser = changedUser;
    }

    @Parameterized.Parameters
    public static Object[][] changeParameter(){
        return new Object[][]{
                //изменение адреса электронной почты авторизованного пользователя
                {UserMethods.setNewEmail()},
                //изменение пароля авторизованного пользователя
                {UserMethods.setNewPassword()},
                //изменение имени авторизованного пользователя
                {UserMethods.setNewName()}
        };
    }

    @Before
    public void setUp(){
        user = createRandomUser();
        userClient = new UserClient();
        ValidatableResponse register = userClient.create(user);
        userCredentials = userCredentials.from(user);
        ValidatableResponse login = userClient.login(userCredentials);
        accessToken = login.extract().path("accessToken");
    }

    @Test
    public void userCanChangeEmailAfterLogin(){
        ValidatableResponse change = userClient.change(accessToken, changedUser);
        int statusCode = change.extract().statusCode();
        assertEquals(200, statusCode);
    }
}

