package com.sofka.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {

    /**
     * Espera a que un texto específico desaparezca del DOM o deje de ser visible.
     * Útil para validar que un estado de carga ha terminado.
     */
    public static ExpectedCondition<Boolean> textDisappears(String partialText) {
        return driver -> {
            try {
                // Buscamos si el texto está presente en el código fuente de la página
                return !driver.getPageSource().contains(partialText);
            } catch (StaleElementReferenceException e) {
                // Si el DOM cambia justo ahora, asumimos que el texto podría haber desaparecido
                return true; 
            }
        };
    }

    public static ExpectedCondition<WebElement> elementHasText(By locator, String exactText) {
        return driver -> {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed() && element.getText().equals(exactText)) {
                    return element;
                }
                return null; 
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                return null;
            }
        };
    }
}
