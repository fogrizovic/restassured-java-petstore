package com.petstore.tests;

import com.petstore.utils.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UserTests extends BaseTest {

    private static final String USERNAME = "testuser_restassured";

    @Test(description = "Should create a new user")
    public void shouldCreateUser() {
        String body = """
                {
                  "username": "%s",
                  "firstName": "Test",
                  "lastName": "User",
                  "email": "test@example.com",
                  "password": "password123",
                  "phone": "1234567890",
                  "userStatus": 1
                }
                """.formatted(USERNAME);

        given()
            .body(body)
        .when()
            .post("/user")
        .then()
            .statusCode(200);
    }

    @Test(description = "Should get user by username", dependsOnMethods = "shouldCreateUser")
    public void shouldGetUserByUsername() {
        given()
        .when()
            .get("/user/" + USERNAME)
        .then()
            .statusCode(200)
            .body("username", equalTo(USERNAME));
    }

    @Test(description = "Should update user", dependsOnMethods = "shouldGetUserByUsername")
    public void shouldUpdateUser() {
        String body = """
                {
                  "username": "%s",
                  "firstName": "Updated",
                  "lastName": "User",
                  "email": "updated@example.com",
                  "password": "password123",
                  "phone": "0987654321",
                  "userStatus": 1
                }
                """.formatted(USERNAME);

        given()
            .body(body)
        .when()
            .put("/user/" + USERNAME)
        .then()
            .statusCode(200);
    }

    @Test(description = "Should login user")
    public void shouldLoginUser() {
        given()
            .queryParam("username", USERNAME)
            .queryParam("password", "password123")
        .when()
            .get("/user/login")
        .then()
            .statusCode(200)
            .body(notNullValue());
    }

    @Test(description = "Should logout user", dependsOnMethods = "shouldLoginUser")
    public void shouldLogoutUser() {
        given()
        .when()
            .get("/user/logout")
        .then()
            .statusCode(200);
    }

    @Test(description = "Should delete user", dependsOnMethods = "shouldUpdateUser")
    public void shouldDeleteUser() {
        given()
        .when()
            .delete("/user/" + USERNAME)
        .then()
            .statusCode(200);
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        given()
        .when()
            .delete("/user/" + USERNAME);
    }
}
