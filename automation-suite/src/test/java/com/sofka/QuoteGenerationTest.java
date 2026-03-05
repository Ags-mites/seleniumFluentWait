package com.sofka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class QuoteGenerationTest {
    private WebDriver driver;
    private QuotePage quotePage;
    private String currentTestName;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        quotePage = new QuotePage(driver);
    }

    @Test
    void testQuoteGenerationPassing() {
        currentTestName = "Test Escenario Exitoso (Passing)";
        try {
            Allure.step("Abrir escenario passing", () -> {
                driver.get("http://localhost:5173/passing");
                attachScreenshot("01_Inicial");
            });

            Allure.step("Click en Generar Cotización", () -> {
                quotePage.clickGenerateButton();
                attachScreenshot("02_BotonClickeado");
            });

            Allure.step("Esperar estados intermedios", () -> {
                quotePage.waitForIntermediateState("Conectando al servidor...");
                attachScreenshot("03_EstadoConectando");
                quotePage.waitForIntermediateState("Calculando volumen...");
                attachScreenshot("04_EstadoCalculando");
                quotePage.waitForStateToDisappear("Conectando al servidor...");
            });

            Allure.step("Esperar cotización final", () -> {
                quotePage.waitForQuoteToCompleteRobust();
                attachScreenshot("05_CotizacionCompletada");
            });

            String quoteText = quotePage.getQuoteStatusText();
            assertTrue(quoteText.contains("Cotización Total:"), 
                "La cotización debe contener el texto 'Cotización Total:'");

            quotePage.waitForButtonToBeEnabled();
            attachScreenshot("06_BotonHabilitado");

            System.out.println("✓ Test PASÓ: " + currentTestName);
        } catch (Exception e) {
            attachScreenshot("ERROR_Fallo");
            Allure.addAttachment("error", e.getClass().getSimpleName() + ": " + e.getMessage());
            System.err.println("✗ Test FALLÓ: " + currentTestName);
            System.err.println("  Error: " + e.getMessage());
            fail(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @Test
    void testQuoteGenerationIncomplete() {
        currentTestName = "Test Escenario Incompleto (Incomplete)";
        try {
            Allure.step("Abrir escenario incomplete", () -> {
                driver.get("http://localhost:5173/incomplete");
                attachScreenshot("01_Inicial");
            });

            Allure.step("Click en Generar Cotización", () -> {
                quotePage.clickGenerateButton();
                attachScreenshot("02_BotonClickeado");
            });

            Allure.step("Esperar estado intermedio", () -> {
                quotePage.waitForIntermediateState("Conectando al servidor...");
                attachScreenshot("03_ProcesoIniciado");
                quotePage.waitForStateToDisappear("Conectando al servidor...");
                attachScreenshot("04_ProcesoFinalizado");
            });

            Allure.step("Intentar esperar quote-status (se espera fallo)", () -> {
                quotePage.waitForQuoteToComplete();
                attachScreenshot("05_ElementoEncontrado");
            });

            String quoteText = quotePage.getQuoteStatusText();
            assertTrue(quoteText.contains("Cotización Total:"), 
                "La cotización debe contener el texto 'Cotización Total:'");

            System.out.println("✓ Test PASÓ: " + currentTestName);
        } catch (Exception e) {
            attachScreenshot("ERROR_ElementoNoEncontrado");
            Allure.addAttachment("error esperado", e.getClass().getSimpleName() + ": " + e.getMessage());
            System.err.println("✗ Test FALLÓ (ESPERADO): " + currentTestName);
            System.err.println("  Error: " + e.getMessage());
            throw e;
        }
    }

    private void attachScreenshot(String name) {
        if (driver == null) {
            return;
        }
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
