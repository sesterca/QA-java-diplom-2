package site.nomoreparties.stellarburgers;

public enum Endpoints {

    //регистрация пользователя
        USER_REGISTER("/auth/register"),
    //авторизация пользователя
        USER_LOGIN("/auth/login"),
    //изменение данных пользователя и удаление пользователя
        USER_CHANGE("/auth/user"),
    //выход из личного кабинета пользователя
        USER_LOGOUT("/auth/logout "),
    //создание заказа и получение данных о заказе
        USER_ORDER("/orders"),
    //получение данных об ингредиентах
        INGREDIENTS("/ingredients");

    private String endpoint;
    Endpoints(String endpoint) {this.endpoint = endpoint;}

    public String getEndpoint(){return endpoint;}
}
