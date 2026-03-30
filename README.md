# evertec-agibank-api-test-automation-restassured-java

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![RestAssured](https://img.shields.io/badge/RestAssured-5.4.0-green)
![JUnit5](https://img.shields.io/badge/JUnit-5.10.2-orange)
![Allure](https://img.shields.io/badge/Allure-2.25.0-yellow)

API test automation project for Evertec Agibank using RestAssured, JUnit 5, and Allure Report.

## Stack

- **Java 17**
- **RestAssured 5.4.0** — HTTP client for API testing
- **JUnit Jupiter 5.10.2** — Test framework
- **Allure 2.25.0** — Test reporting
- **Jackson Databind 2.16.1** — JSON serialization/deserialization
- **Lombok 1.18.30** — Boilerplate reduction

## Project Structure

```
src/test/java/com/evertec/agibank/dogapi/
├── base/       # Base test classes and setup
├── config/     # Configuration (base URLs, environment)
├── model/      # POJOs / DTOs
├── service/    # API service/client layer
├── tests/      # Test classes
└── utils/      # Utility helpers

src/test/resources/
├── schemas/    # JSON Schema files for contract validation
├── testdata/   # Test data files
└── allure.properties
```

## Running Tests

```bash
# Run all tests
mvn test

# Generate Allure report
mvn allure:report

# Open Allure report
mvn allure:serve
```
