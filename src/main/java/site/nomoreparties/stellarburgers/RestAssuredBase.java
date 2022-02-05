package site.nomoreparties.stellarburgers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAssuredBase {

    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site/api")
                .addHeader("Content-type", "application/json")
                .setContentType(ContentType.JSON)
                .build();
    }
}