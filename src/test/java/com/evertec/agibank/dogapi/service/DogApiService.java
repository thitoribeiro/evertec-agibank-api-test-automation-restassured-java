package com.evertec.agibank.dogapi.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DogApiService {

    private final RequestSpecification spec;

    public DogApiService(RequestSpecification spec) {
        this.spec = spec;
    }

    @Step("Buscar lista completa de raças — GET /breeds/list/all")
    public Response getAllBreeds() {
        return given().spec(spec).when().get("/breeds/list/all").then().extract().response();
    }

    @Step("Buscar imagens da raça '{breed}' — GET /breed/{breed}/images")
    public Response getBreedImages(String breed) {
        return given().spec(spec).when().get("/breed/{breed}/images", breed).then().extract().response();
    }

    @Step("Buscar imagem aleatória — GET /breeds/image/random")
    public Response getRandomImage() {
        return given().spec(spec).when().get("/breeds/image/random").then().extract().response();
    }
}
