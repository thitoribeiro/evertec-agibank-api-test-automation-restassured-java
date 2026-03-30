package com.evertec.agibank.dogapi.utils;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class AllureTestListener implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext ctx, Throwable cause) {
        Allure.addAttachment(
            "Detalhes da falha",
            "text/plain",
            "Teste: " + ctx.getDisplayName() + "\nErro: " + cause.getMessage()
        );
    }

    @Override
    public void testSuccessful(ExtensionContext ctx) {
        Allure.step("Teste concluído com sucesso: " + ctx.getDisplayName());
    }
}
