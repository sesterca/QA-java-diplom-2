package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<String> burger;

    @Step("Создание бургера из доступных ингредиентов")
    public List<String> makeBurger() {

        //получение базы данных доступных ингредиентов
        OrderClient orderClient = new OrderClient();
        ValidatableResponse ingredients = orderClient.getIngredients();

        //создание списка доступных булочек
        List<String> buns = ingredients.extract().jsonPath().getList("data.findAll{it.type =='bun'}._id");
        //создание списка доступных начинок
        List<String> fillings = ingredients.extract().jsonPath().getList("data.findAll{it.type =='main'}._id");
        //создание списка доступных соусов
        List<String> sauces = ingredients.extract().jsonPath().getList("data.findAll{it.type =='sauce'}._id");

        //сборка бургера из булочки, начинки и соуса
        burger = new ArrayList<>();
        burger.add(buns.get(RandomUtils.nextInt(0, buns.size()-1)));
        burger.add(fillings.get(RandomUtils.nextInt(0, fillings.size()-1)));
        burger.add(sauces.get(RandomUtils.nextInt(0, sauces.size()-1)));
        return burger;
    }

    public String ingredientNotExist(){
        return "61c0c5a71d1f82001bdaaa" + RandomStringUtils.randomAlphanumeric(2);}

    @Step("Создание бургера из несуществующих ингредиентов")
    public List<String> makeBurgerOfNonExistent() {
        burger = new ArrayList<>();
        burger.add(ingredientNotExist());
        burger.add(ingredientNotExist());
        return burger;
    }
}
