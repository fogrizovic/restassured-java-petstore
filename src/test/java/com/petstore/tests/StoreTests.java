package com.petstore.tests;

import com.petstore.utils.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class StoreTests extends BaseTest {

    private static long orderId;

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
            .extract().jsonPath().getLong("id");
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

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        if (orderId != 0) {
            given()
            .when()
                .delete("/store/order/" + orderId);
        }
    }
}
