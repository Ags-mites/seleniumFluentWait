package com.sofka;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.function.Function;
import com.sofka.utils.CustomConditions;

public class QuotePage {
    private final WebDriver driver;

    // Localizador del elemento que muestra el estado de la cotización
    private final By quoteStatusLocator = By.id("quote-status");

    public QuotePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Espera a que el botón de generación vuelva a estar habilitado.
     */
    public void waitForButtonToBeEnabled() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofMillis(200))
            .ignoring(NoSuchElementException.class);

        wait.until(d -> {
            WebElement btn = d.findElement(By.id("generate-quote-btn"));
            return btn.isEnabled(); 
        });
    }

    /**
     * Espera a que aparezca un mensaje de estado intermedio específico.
     * @param expectedMessage El texto parcial que se espera ver (ej: "Conectando...")
     */
    public void waitForIntermediateState(String expectedMessage) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(5))
            .pollingEvery(Duration.ofMillis(50))
             .ignoring(NoSuchElementException.class);

        wait.until(d -> d.getPageSource().contains(expectedMessage));
    }

    public void waitForStateToDisappear(String message) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
             .withTimeout(Duration.ofSeconds(10))
             .pollingEvery(Duration.ofMillis(200));

        wait.until(CustomConditions.textDisappears(message));
    }

    public void waitForQuoteToComplete() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofMillis(300))
            .ignoring(NoSuchElementException.class)
            .ignoring(StaleElementReferenceException.class);

        wait.until(driver -> {
            WebElement status = driver.findElement(quoteStatusLocator);
            return status.getText().contains("Cotización Total:");
        });
    }
}
