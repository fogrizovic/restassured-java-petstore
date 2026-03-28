package com.petstore.tests;

import com.petstore.utils.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetTests extends BaseTest {

    private static final long PET_ID = 99991L;

    @Test(description = "Should create a new pet")
    public void shouldCreatePet() {
        String body = """
                {
                  "id": %d,
                  "name": "Buddy",
                  "status": "available"
                }
                """.formatted(PET_ID);

        given()
            .body(body)
        .when()
            .post("/pet")
        .then()
            .statusCode(200)
            .body("id", equalTo((int) PET_ID))
            .body("name", equalTo("Buddy"))
            .body("status", equalTo("available"));
    }

    @Test(description = "Should get pet by ID", dependsOnMethods = "shouldCreatePet")
    public void shouldGetPetById() {
        given()
        .when()
            .get("/pet/" + PET_ID)
        .then()
            .statusCode(200)
            .body("id", equalTo((int) PET_ID))
            .body("name", equalTo("Buddy"));
    }

    @Test(description = "Should update pet status", dependsOnMethods = "shouldGetPetById")
    public void shouldUpdatePet() {
        String body = """
                {
                  "id": %d,
                  "name": "Buddy",
                  "status": "sold"
                }
                """.formatted(PET_ID);

        given()
            .body(body)
        .when()
            .put("/pet")
        .then()
            .statusCode(200)
            .body("status", equalTo("sold"));
    }

    @Test(description = "Should find pets by status")
    public void shouldFindPetsByStatus() {
        given()
            .queryParam("status", "available")
        .when()
            .get("/pet/findByStatus")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test(description = "Should delete pet", dependsOnMethods = "shouldUpdatePet")
    public void shouldDeletePet() {
        given()
        .when()
            .delete("/pet/" + PET_ID)
        .then()
            .statusCode(200);
    }

    @Test(description = "Should return 404 for non-existing pet")
    public void shouldReturn404ForNonExistingPet() {
        given()
        .when()
            .get("/pet/000000999")
        .then()
            .statusCode(404);
    }
}
