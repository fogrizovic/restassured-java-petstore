package com.petstore.tests;

import com.petstore.utils.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoreTests extends BaseTest {

    private static int orderId;

    @Test(description = "Should get store inventory")
    public void shouldGetInventory() {
        given()
        .when()
            .get("/store/inventory")
        .then()
            .statusCode(200)
            .body("available", notNullValue());
    }

    @Test(description = "Should place a new order")
    public void shouldPlaceOrder() {
        String body = """
                {
                  "petId": 1,
                  "quantity": 1,
                  "status": "placed",
                  "complete": true
                }
                """;

        orderId = given()
            .body(body)
        .when()
            .post("/store/order")
        .then()
            .statusCode(200)
            .body("status", equalTo("placed"))
            .body("complete", equalTo(true))
            .extract().path("id");
    }

    @Test(description = "Should get order by ID", dependsOnMethods = "shouldPlaceOrder")
    public void shouldGetOrderById() {
        given()
        .when()
            .get("/store/order/" + orderId)
        .then()
            .statusCode(200)
            .body("id", equalTo(orderId));
    }

    @Test(description = "Should delete order", dependsOnMethods = "shouldGetOrderById")
    public void shouldDeleteOrder() {
        given()
        .when()
            .delete("/store/order/" + orderId)
        .then()
            .statusCode(200);
    }
}
