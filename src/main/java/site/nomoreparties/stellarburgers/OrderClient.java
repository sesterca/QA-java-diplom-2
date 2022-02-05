package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredBase{

    //получение данных об ингредиентах
    public ValidatableResponse getIngredients(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(Endpoints.INGREDIENTS.getEndpoint())
                .then()
                .log().body();
    }

    //создание заказа
    public ValidatableResponse makeOrder(String accessToken, Map<String, List<String>> userOrder){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .and()
                .body(userOrder)
                .log().body()
                .when()
                .post(Endpoints.USER_ORDER.getEndpoint())
                .then()
                .log().body();
    }

    //

    //получение заказов пользователя
    public ValidatableResponse getOrders(String accessToken){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(Endpoints.USER_ORDER.getEndpoint())
                .then()
                .log().body();
    }
}
