package MyTests;
import MainPackage.CurrencyConverterPage;
import io.cucumber.java.en.*;

import io.cucumber.java.en_lol.BUT;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import io.cucumber.core.cli.Main;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CurrencyConverterTest {

    public static void main(String[] args) throws Exception {
        Main.run(new String[]{"--glue", "MyTests", "classpath:features/Project.feature"});
    }

    private WebDriver driver;
    public static CurrencyConverterPage currencyConverterPage;


    @Given("When I'm at Converter Page")
    public void setup() {

        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Java\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.xe.com/currencyconverter/");
        currencyConverterPage = new CurrencyConverterPage(driver);

    }
//

    @When("I skip cookies")
    public void skipCookies() {
        currencyConverterPage.SkippingCookies();
        //User should be able to specify source and target currency...
    }

    @And("Select a source currency and assert it is selected")
    public void SelectSourceCurrency() {
        currencyConverterPage.SelectSourceCurrency();
        currencyConverterPage.AssertSourceCurrencySelected();
    }

    @And("Select a target currency and assert it is selected")
    public void SelectTargetCurrency() {
        currencyConverterPage.SelectTargetCurrency();
        currencyConverterPage.AssertTargetCurrencySelected();
        currencyConverterPage.changeCurrencies();
    }

    @And("Type whole integer")
    public void typeWholeInteger() throws Exception {
        //Users should be able to specify whole integers.
        currencyConverterPage.typeWholeInteger();
        currencyConverterPage.ClosePopUp();
    }

    @And("Obtain result: conversion amount & conversion rate of a single unit for both currencies")
    public void ObtainResult() {
        //and obtain conversion amount.
        currencyConverterPage.ConvertIt();
        //Whenever system provides conversion results, it should display the full conversion amount for -
        // the value specified as well as the conversion rate of a single unit for both currencies.
        currencyConverterPage.ConversionResults();
    }

    @Then("Mathematical assertion for the conversion")
    //The conversion values presented for the amount specified (e.g. 10 USD = 8.90909 EUR) and the 1 unit values
    // should be mathematically correct. i.e. if 1 USD = 0.890909 EUR, then 10 USD should equate to 8.90909 EUR.
    public void AssertConversion() {
        currencyConverterPage.AssertConversion();
    }


    //User should be able to swap source and target currencies by clicking the ‘Invert Currencies’ button.

    // Once the button is clicked on, the conversion is made.
    @Then("I click swap currencies and conversion happens")
    public void PublicSwapConversion() {
        currencyConverterPage.AfterSwapConversion();

    }


@Then("URI should be updated to reflect the amount, source and target currency for the conversion.")
    public void URIDataReflection() {
    currencyConverterPage.URIDataReflection();
}


@Then("Entered Negative number and error message should be displayed but conversion happens anyway.")
    public void NegativeNumberConversion() throws Exception {
    currencyConverterPage.NegativeNumbersConversion();
}

@Then("user specify non-numeric values, the system should give an error.")
    public void NonNumericValueCheck() throws Exception {
    currencyConverterPage.NonNumericValueCheck();
}

@Given("Users is able to access a conversion page by specifying the right query string parameters")
    public void UserAccessToConversionPageThroughString() {
    currencyConverterPage.UsersAccessToConversionPageThroughString();
}
@Then("Decimal amount is typed and processed")
        public void typeDecimalNumericAmount() {
        currencyConverterPage.SkippingCookies();
    currencyConverterPage.typeDecimalNumericAmount();
}


}
        //IMPORTANT
        //Regarding the Acceptance Criteria #5:
        //The page doesn't provide free access to any element inside the dropdown ComboBox list,
        // so I couldn't sort or interact anyhow with any elements




