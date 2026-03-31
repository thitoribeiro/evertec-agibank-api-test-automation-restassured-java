package com.evertec.agibank.dogapi.tests;

import com.evertec.agibank.dogapi.base.BaseTest;
import com.evertec.agibank.dogapi.model.BreedListResponse;
import com.evertec.agibank.dogapi.service.DogApiService;
import com.evertec.agibank.dogapi.utils.SchemaValidator;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("Breed List")
@Story("Listar todas as raças")
@DisplayName("Breed List — GET /breeds/list/all")
class BreedListTest extends BaseTest {

    private static DogApiService dogApiService;

    @BeforeAll
    static void setup() {
        dogApiService = new DogApiService(spec);
    }

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("deve retornar status HTTP 200")
    void shouldReturn200() {
        dogApiService.getAllBreeds()
                .then()
                .statusCode(200);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("deve retornar Content-Type application/json")
    void shouldReturnJsonContentType() {
        dogApiService.getAllBreeds()
                .then()
                .contentType(ContentType.JSON);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("deve validar o schema JSON da resposta")
    void shouldMatchJsonSchema() {
        dogApiService.getAllBreeds()
                .then()
                .body(SchemaValidator.matchesSchema("breed-list-schema.json"));
    }

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("campo status deve ser igual a success")
    void shouldReturnStatusSuccess() {
        dogApiService.getAllBreeds()
                .then()
                .body("status", equalTo("success"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("campo message deve ser um Map não nulo e não vazio")
    void shouldReturnNonEmptyMessageMap() {
        dogApiService.getAllBreeds()
                .then()
                .body("message", notNullValue())
                .body("message.size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("deve conter pelo menos 50 raças")
    void shouldContainAtLeast50Breeds() {
        Response response = dogApiService.getAllBreeds();
        BreedListResponse body = response.as(BreedListResponse.class);
        assertThat(body.getMessage().size(), greaterThanOrEqualTo(50));
    }

    @ParameterizedTest
    @MethodSource("com.evertec.agibank.dogapi.utils.TestDataFactory#breedsWithSubBreeds")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("raças com sub-raças devem ter lista não vazia")
    void breedWithSubBreedsShouldHaveNonEmptyList(String breed) {
        dogApiService.getAllBreeds()
                .then()
                .body("message." + breed + ".size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("tempo de resposta deve ser inferior a 3000ms")
    void shouldRespondInLessThan3Seconds() {
        dogApiService.getAllBreeds()
                .then()
                .time(lessThan(3000L));
    }
}
