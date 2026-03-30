package com.evertec.agibank.dogapi.tests;

import com.evertec.agibank.dogapi.base.BaseTest;
import com.evertec.agibank.dogapi.service.DogApiService;
import com.evertec.agibank.dogapi.utils.SchemaValidator;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Breed Images")
class BreedImagesTest extends BaseTest {

    private static DogApiService dogApiService;

    @BeforeAll
    static void setup() {
        dogApiService = new DogApiService(spec);
    }

    @Nested
    @DisplayName("Happy path — breed válida")
    @Story("Imagens de raça existente")
    class ValidBreedTests {

        @Test
        @Tag("smoke")
        @Severity(SeverityLevel.BLOCKER)
        @DisplayName("deve retornar status 200 para breed válida (labrador)")
        void shouldReturn200ForValidBreed() {
            dogApiService.getBreedImages("labrador")
                    .then()
                    .statusCode(200);
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("deve validar schema JSON da resposta")
        void shouldMatchJsonSchema() {
            dogApiService.getBreedImages("labrador")
                    .then()
                    .body(SchemaValidator.matchesSchema("breed-images-schema.json"));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("campo message deve ser um array não vazio")
        void shouldReturnNonEmptyImageArray() {
            dogApiService.getBreedImages("labrador")
                    .then()
                    .body("message", not(empty()));
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("todas as URLs devem ter extensão de imagem válida")
        void allUrlsShouldHaveValidImageExtension() {
            List<String> urls = dogApiService.getBreedImages("labrador")
                    .jsonPath().getList("message");
            urls.forEach(url ->
                    assertTrue(
                            url.matches(".*\\.(jpg|jpeg|png|gif)$"),
                            "URL com extensão inválida encontrada: " + url
                    )
            );
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("todas as URLs devem conter o domínio images.dog.ceo")
        void allUrlsShouldContainCorrectDomain() {
            List<String> urls = dogApiService.getBreedImages("labrador")
                    .jsonPath().getList("message");
            urls.forEach(url ->
                    assertTrue(
                            url.contains("images.dog.ceo"),
                            "URL com domínio incorreto: " + url
                    )
            );
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("todas as URLs devem conter /breeds/ no path")
        void allUrlsShouldContainBreedsInPath() {
            List<String> urls = dogApiService.getBreedImages("labrador")
                    .jsonPath().getList("message");
            urls.forEach(url ->
                    assertTrue(
                            url.contains("/breeds/"),
                            "URL sem /breeds/ no path: " + url
                    )
            );
        }

        @ParameterizedTest
        @MethodSource("com.evertec.agibank.dogapi.utils.TestDataFactory#validBreeds")
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("deve retornar imagens para múltiplas breeds válidas")
        void shouldReturnImagesForMultipleValidBreeds(String breed) {
            dogApiService.getBreedImages(breed)
                    .then()
                    .statusCode(200)
                    .body("message", not(empty()));
        }
    }

    @Nested
    @DisplayName("Cenários negativos — breed inválida")
    @Story("Breed inexistente")
    class InvalidBreedTests {

        @ParameterizedTest
        @MethodSource("com.evertec.agibank.dogapi.utils.TestDataFactory#invalidBreeds")
        @Tag("smoke")
        @Severity(SeverityLevel.CRITICAL)
        @DisplayName("deve retornar 404 para breed inexistente")
        void shouldReturn404ForInvalidBreed(String breed) {
            dogApiService.getBreedImages(breed)
                    .then()
                    .statusCode(404);
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("body de erro não deve ter status success")
        void errorBodyShouldNotHaveSuccessStatus() {
            dogApiService.getBreedImages("invalidbreed")
                    .then()
                    .body("status", not(equalTo("success")));
        }
    }
}
