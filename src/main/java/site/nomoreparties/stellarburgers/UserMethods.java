package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserMethods {

    @Step("Генерация данных пользователя (рандомные данные)")
    public static User createRandomUser(){
        User user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru");
        user.setPassword(RandomStringUtils.random(6,true,true));
        user.setName(RandomStringUtils.randomAlphabetic(5));
        return user;}

    @Step("Генерация данных пользователя (заданные данные)")
    public static User createUser(String email, String password, String name){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        return user;}

    public static User setNewEmail(){return new User().setEmail(RandomStringUtils.randomAlphabetic(5) + "@yandex.ru");}

    public static User setNewPassword(){return new User().setPassword(RandomStringUtils.random(6,true,true));}

    public static User setNewName(){return new User().setName(RandomStringUtils.randomAlphabetic(5));}
}
