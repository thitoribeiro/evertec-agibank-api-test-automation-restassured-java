package com.evertec.agibank.dogapi.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matcher;

import java.io.InputStream;

public class SchemaValidator {

    private SchemaValidator() {}

    public static Matcher matchesSchema(String schemaFileName) {
        InputStream stream = SchemaValidator.class.getClassLoader()
                .getResourceAsStream("schemas/" + schemaFileName);
        if (stream == null) {
            throw new IllegalArgumentException("Schema não encontrado: schemas/" + schemaFileName);
        }
        return JsonSchemaValidator.matchesJsonSchema(stream);
    }
}
