package com.sofka.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {

    public static ExpectedCondition<Boolean> textDisappears(By locator, String expectedText) {
        return driver -> {
            try {
                WebElement element = driver.findElement(locator);
                return !element.getText().contains(expectedText);
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return true;
            }
        };
    }
}
