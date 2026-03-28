package com.petstore.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static final String BASE_URL = System.getProperty("base.url", "https://petstore.swagger.io/v2");

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    protected RequestSpecification given() {
        return io.restassured.RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
