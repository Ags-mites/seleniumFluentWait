package com.sofka;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

import static com.sofka.utils.CustomConditions.textDisappears;

public class QuotePage extends PageObject {

    private final By generateButtonLocator = By.id("generate-quote-btn");
    private final By quoteStatusLocator = By.id("quote-status");
    private final By statusMessageLocator = By.className("status-message");

    @Step("Hacer clic en botón generar")
    public void clickGenerateButton() {
        $(generateButtonLocator).click();
    }

    @Step("Esperar que el botón esté habilitado")
    public void waitForButtonToBeEnabled() {
        FluentWait<WebDriver> wait = createWait(10, 200);
        wait.until(ExpectedConditions.elementToBeClickable(generateButtonLocator));
    }

    @Step("Esperar mensaje intermedio: {0}")
    public void waitForIntermediateState(String expectedMessage) {
        FluentWait<WebDriver> wait = createWait(15, 100);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
            statusMessageLocator, expectedMessage));
    }

    @Step("Esperar a que desaparezca mensaje: {0}")
    public void waitForStateToDisappear(String message) {
        FluentWait<WebDriver> wait = createWait(10, 100);
        wait.until(textDisappears(statusMessageLocator, message));
    }

    @Step("Esperar cotización completa (robusto)")
    public void waitForQuoteToCompleteRobust() {
        FluentWait<WebDriver> wait = createWait(30, 300);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
            quoteStatusLocator, "Cotización Total:"));
    }

    @Step("Verificar que elemento de cotización está presente")
    public boolean isQuoteStatusPresent() {
        return $(quoteStatusLocator).isPresent();
    }

    @Step("Obtener texto de cotización")
    public String getQuoteStatusText() {
        WebElementFacade statusElement = $(quoteStatusLocator);
        FluentWait<WebDriver> wait = createWait(10, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(quoteStatusLocator));
        if (statusElement.isPresent()) {
            return statusElement.getText();
        }
        return null;
    }

    private FluentWait<WebDriver> createWait(long timeoutSeconds, long pollingMillis) {
        return new FluentWait<>(getDriver())
            .withTimeout(Duration.ofSeconds(timeoutSeconds))
            .pollingEvery(Duration.ofMillis(pollingMillis))
            .ignoring(NoSuchElementException.class)
            .ignoring(StaleElementReferenceException.class);
    }
}
