package com.sofka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class QuoteGenerationTest {
    private WebDriver driver;
    private QuotePage quotePage;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:5173/");
        quotePage = new QuotePage(driver);
    }

    @Test
    void testQuoteGenerationCompletes() {
        driver.findElement(By.id("generate-quote-btn")).click();
        
        quotePage.waitForIntermediateState("Conectando al servidor...");
        quotePage.waitForIntermediateState("Calculando volumen...");
        
        quotePage.waitForStateToDisappear("Conectando al servidor...");

        quotePage.waitForQuoteToComplete();
        assertTrue(true, "La cotización se completó correctamente usando FluentWait.");
        
        quotePage.waitForButtonToBeEnabled();
        assertTrue(driver.findElement(By.id("generate-quote-btn")).isEnabled(), "El botón debería estar habilitado tras finalizar.");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
