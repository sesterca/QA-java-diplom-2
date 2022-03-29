package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserMakeOrderTests {

    private User user;
    private Order order;
    private UserClient userClient;
    private OrderClient orderClient;
    private UserCredentials userCredentials;
    private String accessToken;

    @Before
    public void setUp(){
        order = new Order();
        userClient = new UserClient();
        orderClient = new OrderClient();
        //создание пользователя
        user = UserMethods.createRandomUser();
        ValidatableResponse creation = userClient.create(user);}

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void userAuthorizedMakeOrderTest(){
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        accessToken = login.extract().path("accessToken");
        List<String> burger = order.makeBurger();
        Map<String, List<String>> burgerOrder = new HashMap<>();
        burgerOrder.put("ingredients", burger);
        ValidatableResponse order = orderClient.makeOrder(accessToken, burgerOrder);
        int statusCode = order.extract().statusCode();
        int orderNumber = order.extract().path("order.number");
        assertEquals("Burger order failed", 200, statusCode);
        Assert.assertNotNull("No order has been created", orderNumber);}

    @Test
    @DisplayName("Cоздание заказа без авторизации")
    public void userNonAuthorizedMakeOrderTest(){
        List<String> burger = order.makeBurger();
        Map<String, List<String>> burgerOrder = new HashMap<>();
        burgerOrder.put("ingredients", burger);
        ValidatableResponse order = orderClient.makeOrder("", burgerOrder);
        int statusCode = order.extract().statusCode();
        int message = order.extract().path("message");
        assertEquals("Unauthorized", 401, statusCode);
        assertEquals("User need to be authorised to make order", "You should be authorised", message);}

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void userCanNotMakeEmptyBurgerTest(){
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        accessToken = login.extract().path("accessToken");
        Map<String, List<String>> burgerOrder = new HashMap<>();
        ValidatableResponse order = orderClient.makeOrder(accessToken, burgerOrder);
        int statusCode = order.extract().statusCode();
        String message = order.extract().path("message");
        assertEquals("Bad Request", 400, statusCode);
        assertEquals("Burger is empty. Choose ingredients", "Ingredient ids must be provided", message);}

    @Test
    @DisplayName("Создание с неверным хешем ингредиентов")
    public void burgerCanNotBeMadeOfNonExistentIngredientsTest(){
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        accessToken = login.extract().path("accessToken");
        List<String> burger = order.makeBurgerOfNonExistent();
        Map<String, List<String>> burgerOrder = new HashMap<>();
        burgerOrder.put("ingredients", burger);
        ValidatableResponse order = orderClient.makeOrder(accessToken, burgerOrder);
        int statusCode = order.extract().statusCode();
        assertEquals("Ingredients exist", 500, statusCode);}
}
