package com.sofka;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.function.Function;
import com.sofka.utils.CustomConditions;

public class QuotePage {
    private final WebDriver driver;

    private final By quoteStatusLocator = By.id("quote-status");
    private final By generateButtonLocator = By.id("generate-quote-btn");

    public QuotePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickGenerateButton() {
        driver.findElement(generateButtonLocator).click();
    }

    public void waitForButtonToBeEnabled() {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofMillis(200))
            .ignoring(NoSuchElementException.class);

        wait.until(d -> {
            WebElement btn = d.findElement(generateButtonLocator);
            return btn.isEnabled(); 
        });
    }

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
            .withTimeout(Duration.ofSeconds(15))
            .pollingEvery(Duration.ofMillis(200))
            .ignoring(NoSuchElementException.class);

        wait.until(driver -> {
            WebElement status = driver.findElement(quoteStatusLocator);
            return status.getText().contains("Cotización Total:");
        });
    }

    public void waitForQuoteToCompleteRobust() {
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

    public boolean isQuoteStatusPresent() {
        try {
            driver.findElement(quoteStatusLocator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getQuoteStatusText() {
        try {
            return driver.findElement(quoteStatusLocator).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
