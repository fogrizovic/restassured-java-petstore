# restassured-java-petstore

API test suite for the [Swagger Petstore](https://petstore.swagger.io/v2) built with REST Assured and TestNG.

## Stack

- Java
- REST Assured
- TestNG
- Allure
- GitHub Actions

## Run tests

```bash
mvn test
```

## Reports

Allure results are generated to `target/allure-results`. To serve the report locally:

```bash
mvn allure:serve
```

On `main`, tests run via GitHub Actions and the report is published to GitHub Pages.
