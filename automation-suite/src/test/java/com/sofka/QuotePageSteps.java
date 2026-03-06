package com.sofka;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.Assert.assertTrue;

public class QuotePageSteps {

    QuotePage quotePage;
    public WebDriver driver;

    @Step("Abrir escenario con UI completa (PASSING)")
    public void abrirEscenarioPassing() {
        driver.get("http://localhost:5173/passing");
        takeScreenshot("01_passing_scenario");
    }

    @Step("Abrir escenario con UI incompleta (INCOMPLETE)")
    public void abrirEscenarioIncomplete() {
        driver.get("http://localhost:5173/incomplete");
        takeScreenshot("02_incomplete_scenario");
    }

    @Step("Hacer clic en botón 'Generar Cotización'")
    public void clicEnGenerarCotizacion() {
        quotePage.clickGenerateButton();
        takeScreenshot("03_after_click");
    }

    @Step("Esperar estados intermedios: 'Conectando al servidor', 'Calculando volumen', 'Aplicando descuentos'")
    public void esperarEstadosIntermedios() {
        quotePage.waitForIntermediateState("Conectando al servidor...");
        quotePage.waitForIntermediateState("Calculando volumen...");
        quotePage.waitForStateToDisappear("Conectando al servidor...");
        takeScreenshot("04_intermediate_states");
    }

    @Step("Esperar cotización final (robusta)")
    public void esperarCotizacionFinal() {
        quotePage.waitForQuoteToCompleteRobust();
        takeScreenshot("05_quote_complete");
    }

    @Step("Validar que la cotización está completa y contiene 'Cotización Total:'")
    public void validarCotizacionCompleta() {
        String quoteText = quotePage.getQuoteStatusText();
        assertTrue("La cotización debe contener el texto 'Cotización Total:'", 
            quoteText.contains("Cotización Total:"));
        takeScreenshot("06_quote_validated");
    }

    @Step("Esperar que el botón 'Generar Cotización' esté habilitado")
    public void esperarBotonHabilitado() {
        quotePage.waitForButtonToBeEnabled();
        takeScreenshot("07_button_enabled");
    }

    private void takeScreenshot(String name) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File outputDir = new File("build/reports/serenity/screenshots");
            outputDir.mkdirs();
            File outputFile = new File(outputDir, name + ".png");
            Files.copy(screenshot.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + outputFile.getAbsolutePath());
            Serenity.takeScreenshot();
        } catch (Exception e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
    }
}
