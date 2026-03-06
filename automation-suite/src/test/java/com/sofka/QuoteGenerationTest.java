package com.sofka;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityRunner.class)
public class QuoteGenerationTest {

    private QuotePageSteps quotePageSteps;

    @Managed
    public WebDriver driver;

    @Before
    public void setUp() {
        quotePageSteps = new QuotePageSteps();
        quotePageSteps.driver = driver;
        quotePageSteps.quotePage = new QuotePage();
        quotePageSteps.quotePage.setDriver(driver);
    }

    @Test
    public void testQuoteGenerationPassing() {
        quotePageSteps.abrirEscenarioPassing();
        quotePageSteps.clicEnGenerarCotizacion();
        quotePageSteps.esperarEstadosIntermedios();
        quotePageSteps.esperarCotizacionFinal();
        quotePageSteps.validarCotizacionCompleta();
        quotePageSteps.esperarBotonHabilitado();
    }

    @Test
    public void testQuoteGenerationIncompleteFail() {
        quotePageSteps.abrirEscenarioIncomplete();
        quotePageSteps.clicEnGenerarCotizacion();
        quotePageSteps.esperarEstadosIntermedios();
        quotePageSteps.esperarCotizacionFinal();
        quotePageSteps.validarCotizacionCompleta();
        quotePageSteps.esperarBotonHabilitado();
    }
}
