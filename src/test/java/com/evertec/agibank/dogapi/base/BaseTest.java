package com.evertec.agibank.dogapi.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static RequestSpecification spec;

    @BeforeAll
    static void setupSpec() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://dog.ceo/api")
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }
}
