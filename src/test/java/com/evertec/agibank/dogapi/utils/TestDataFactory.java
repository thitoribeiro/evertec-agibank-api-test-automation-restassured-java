package com.evertec.agibank.dogapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class TestDataFactory {

    private static final BreedsData breedsData;

    static {
        try {
            InputStream stream = TestDataFactory.class.getClassLoader()
                    .getResourceAsStream("testdata/breeds.json");
            if (stream == null) {
                throw new RuntimeException("Arquivo breeds.json não encontrado em testdata/");
            }
            breedsData = new ObjectMapper().readValue(stream, BreedsData.class);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar breeds.json", e);
        }
    }

    private TestDataFactory() {}

    public static Stream<String> validBreeds() {
        return breedsData.validBreeds.stream();
    }

    public static Stream<String> invalidBreeds() {
        return breedsData.invalidBreeds.stream();
    }

    public static Stream<String> breedsWithSubBreeds() {
        return breedsData.breedsWithSubBreeds.stream();
    }

    public static String randomValidBreed() {
        List<String> breeds = breedsData.validBreeds;
        Collections.shuffle(breeds);
        return breeds.get(0);
    }

    private static class BreedsData {
        public List<String> validBreeds;
        public List<String> invalidBreeds;
        public List<String> breedsWithSubBreeds;
    }
}
