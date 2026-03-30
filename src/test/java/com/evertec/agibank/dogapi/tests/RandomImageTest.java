package com.evertec.agibank.dogapi.tests;

import com.evertec.agibank.dogapi.base.BaseTest;
import com.evertec.agibank.dogapi.service.DogApiService;
import com.evertec.agibank.dogapi.utils.SchemaValidator;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Random Image")
@Story("Imagem aleatória de qualquer raça")
class RandomImageTest extends BaseTest {

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
        dogApiService.getRandomImage()
                .then()
                .statusCode(200);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("deve validar schema JSON da resposta")
    void shouldMatchJsonSchema() {
        dogApiService.getRandomImage()
                .then()
                .body(SchemaValidator.matchesSchema("random-image-schema.json"));
    }

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("campo status deve ser success")
    void shouldReturnStatusSuccess() {
        dogApiService.getRandomImage()
                .then()
                .body("status", equalTo("success"));
    }

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("campo message deve ser uma URL não nula e não vazia")
    void shouldReturnNonEmptyImageUrl() {
        dogApiService.getRandomImage()
                .then()
                .body("message", notNullValue())
                .body("message", not(emptyString()));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("URL deve pertencer ao domínio images.dog.ceo")
    void urlShouldBelongToCorrectDomain() {
        dogApiService.getRandomImage()
                .then()
                .body("message", containsString("images.dog.ceo"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("URL deve ter extensão de imagem válida (.jpg .jpeg .png .gif)")
    void urlShouldHaveValidImageExtension() {
        String url = dogApiService.getRandomImage()
                .then()
                .extract()
                .path("message");
        assertTrue(
                url.matches(".*\\.(jpg|jpeg|png|gif)$"),
                "URL sem extensão de imagem válida: " + url
        );
    }

    @RepeatedTest(3)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("duas chamadas consecutivas devem retornar URLs diferentes")
    @Description("Valida aleatoriedade real — a API não deve retornar a mesma URL em chamadas seguidas")
    void consecutiveCallsShouldReturnDifferentUrls() {
        String url1 = dogApiService.getRandomImage()
                .then()
                .extract()
                .path("message");
        String url2 = dogApiService.getRandomImage()
                .then()
                .extract()
                .path("message");
        assertNotEquals(url1, url2,
                "A API retornou a mesma URL em duas chamadas consecutivas: " + url1);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("tempo de resposta deve ser inferior a 3000ms")
    void shouldRespondInLessThan3Seconds() {
        dogApiService.getRandomImage()
                .then()
                .time(lessThan(3000L));
    }
}
