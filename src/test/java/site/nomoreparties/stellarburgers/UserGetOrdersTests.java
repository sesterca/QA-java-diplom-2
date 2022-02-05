package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UserGetOrdersTests {

    private User user;
    private Order order;
    private UserMethods userMethods;
    private UserClient userClient;
    private OrderClient orderClient;
    private String accessToken;

    @Before
    public void setUp(){
        orderClient = new OrderClient();
        order = new Order();}

    //получение заказов конкретного пользователя авторизованного
    @Test
    public void getAllOrdersAuthorizedUserTest(){
        //создание пользователя
        userClient = new UserClient();
        user = UserMethods.createRandomUser();
        ValidatableResponse creation = userClient.create(user);
        //авторизация
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        accessToken = login.extract().path("accessToken");
        //создание заказа
        List<String> burger = order.makeBurger();
        Map<String, List<String>> burgerOrder = new HashMap<>();
        burgerOrder.put("ingredients", burger);
        //отправка запроса к эндпоинту для оформление заказа
        ValidatableResponse order = orderClient.makeOrder(accessToken, burgerOrder);
        ValidatableResponse userOrders = orderClient.getOrders(accessToken);
        int statusCode = userOrders.extract().statusCode();
        int orderNumber = order.extract().path("order.number");
        assertEquals("User has 0 orders", 200, statusCode);
        Assert.assertNotNull("No order have made", orderNumber);
    }

    //получение заказов конкретного пользователя неавторизованного
    @Test
    public void getAllOrdersNonAuthorizedUser() {
        userClient = new UserClient();
        user = UserMethods.createRandomUser();
        ValidatableResponse creation = userClient.create(user);
        ValidatableResponse userOrders = orderClient.getOrders("");
        int statusCode = userOrders.extract().statusCode();
        String message = userOrders.extract().path("message");
        assertEquals("User need to login", 401, statusCode);
        assertEquals("Only authorized user can get orders", "You should be authorised", message);
    }
}
