package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredBase{

    //запрос на создание пользователя
    public static ValidatableResponse create(User user){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .log().body()
                .when()
                .post(Endpoints.USER_REGISTER.getEndpoint())
                .then()
                .log().body();
    }

    //запрос на авторизацию пользователя
    public ValidatableResponse login(UserCredentials userCredentials){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(userCredentials)
                .log().body()
                .when()
                .post(Endpoints.USER_LOGIN.getEndpoint())
                .then()
                .log().body();
    }

    //запрос на получение данных пользователя
    public static ValidatableResponse getInfo(String accessToken){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(Endpoints.USER_CHANGE.getEndpoint())
                .then()
                .log().body();
    }

    //запрос на изменение данных авторизованного пользователя
    public ValidatableResponse change(String accessToken, User user){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .and()
                .body(user)
                .log().body()
                .when()
                .patch(Endpoints.USER_CHANGE.getEndpoint())
                .then()
                .log().body();
    }

    //запрос на изменение данных пользователя без авторизации
    public ValidatableResponse changeNotLogin(User user){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .log().body()
                .when()
                .patch(Endpoints.USER_CHANGE.getEndpoint())
                .then()
                .log().body();
    }

    //запрос на выход из личного кабинета пользователя
    public ValidatableResponse logout(User user){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .log().body()
                .when()
                .post(Endpoints.USER_LOGOUT.getEndpoint())
                .then()
                .log().body();
    }

    //запрос на удаление пользователя
    public ValidatableResponse delete(String accessToken){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(Endpoints.USER_REGISTER.getEndpoint())
                .then()
                .log().body();
    }
}
