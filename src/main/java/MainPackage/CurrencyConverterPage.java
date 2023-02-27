package MainPackage;
import com.github.javafaker.Faker;
import dev.failsafe.internal.util.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.asynchttpclient.util.Assertions;
import org.checkerframework.checker.units.qual.K;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.locks.Condition;

public class CurrencyConverterPage {
    private WebDriver driver;

    public CurrencyConverterPage(WebDriver driver) {
        this.driver = driver;
    }

    private By InputAmount = By.cssSelector("input[class='amount-input__NumberInput-sc-1gq6pic-1 eIuRdk']");
    private By InputLabel = By.cssSelector("label[for='amount']");
    private By FromCurrencyBox = By.cssSelector("input[id='midmarketFromCurrency']");
    private By ToCurrencyBox = By.cssSelector("input[id='midmarketToCurrency']");
    private By ChangeCurrency = By.cssSelector("div[class='currency-converter___StyledDiv-zieln1-0 dYwyct']");
    private By SelectedSourceCurrency = By.xpath(String.format("//span[@class='dark-text' and contains(text(),'%s')]", sourceCurrency()));
    private By SelectedTargetCurrency = By.xpath(String.format("//span[@class='dark-text' and contains(text(),'%s')]", targetCurrency()));


    private By ConvertButton = By.cssSelector("button[style='grid-area:buttons']");
    private By SignUpButton = By.cssSelector("button[aria-describedby='0.5425981265080513']");
    private By RegisterButton = By.cssSelector("button[aria-describedby='0.3821385393257273']");
    private By Popup = By.cssSelector("button[id='element-0JgZU8']");
    private By AcceptCookiesButton = By.cssSelector("button[class='button__BaseButton-sc-1qpsalo-0 haqezJ']");
    private By ConvertedTarget = By.xpath("//p[@class='result__BigRate-sc-1bsijpp-1 iGrAod']");
    private By ConvertedSource = By.cssSelector("p[class='result__ConvertedText-sc-1bsijpp-0 gwvOOF']");
    private By SingleUnitTarget = By.cssSelector("div.unit-rates___StyledDiv-sc-1dk593y-0.dEqdnx > p:nth-of-type(2)");
    private By SingleUnitSource = By.cssSelector("div.unit-rates___StyledDiv-sc-1dk593y-0.dEqdnx > p:nth-of-type(1)");
    private By ErrorMessage = By.cssSelector("div[class='currency-converter__ErrorText-zieln1-2 dkXbBF']");

    private By result1 = By.cssSelector("p.result__BigRate-sc-1bsijpp-1.iGrAod:not(:has(span.faded-digit");


    public String number() {
        Faker faker = new Faker();
        long randomNumber = faker.number().numberBetween(0, 999);
        System.out.println(randomNumber);
        return String.valueOf(randomNumber);
    }

    public String decimalNumber() {
        Faker faker = new Faker();
        long randomNumber = faker.number().numberBetween(0, 56);
        String decimal = number() + "." + randomNumber;
        System.out.println(decimal);
        return String.valueOf(decimal);

    }

    public String sourceCurrency() {
        String currency = "EUR";
        return String.valueOf(currency);

    }

    public String targetCurrency() {
        String currency = "USD";
        return String.valueOf(currency);

    }

    //User should be able to specify numeric amount, source and target currency, and obtain conversion amount.
    public void SkippingCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(AcceptCookiesButton));
        driver.findElement(AcceptCookiesButton).click();

    }


    public void SelectSourceCurrency() {
        WebElement fromCurrencyBox = driver.findElement(FromCurrencyBox);
//Now we click the box and type the source currency.
        fromCurrencyBox.click();
        fromCurrencyBox.sendKeys(sourceCurrency());
        fromCurrencyBox.sendKeys(Keys.ENTER);
    }

    public void AssertSourceCurrencySelected () {
        //And here we assert if the system received our text and set the right currency. We assert it through text.
        String actualCurrency = driver.findElement(SelectedSourceCurrency).getText();
        String expectedText = sourceCurrency() + " " + "–";

        boolean textEqual = actualCurrency.equals(expectedText);
        if (textEqual) {
            System.out.println("Acceptance Criteria 1: Success, Currency is selected");
        } else {
            System.out.println("Acceptance Criteria 1: Fail, currency is not selected");
        }

    }


    public void SelectTargetCurrency() {
        WebElement toCurrencyBox = driver.findElement(ToCurrencyBox);
        toCurrencyBox.click();
        toCurrencyBox.sendKeys(targetCurrency());
        toCurrencyBox.sendKeys(Keys.ENTER);
    }
    public void AssertTargetCurrencySelected () {
        String actualCurrency = driver.findElement(SelectedTargetCurrency).getText();

        String expectedText = targetCurrency() + " " + "–";
        System.out.println(expectedText);

        boolean textEqual = actualCurrency.equals(expectedText);
        if (textEqual) {
            System.out.println("Acceptance Criteria 1: Success,target Currency is Selected");
        } else {
            System.out.println("Acceptance Criteria 1: Fail, target Currency isn't Selected");
        }

    }

    public String typedAmount() {
        String enteredAmount = driver.findElement(InputAmount).getAttribute("value");
        return String.valueOf(enteredAmount);
    }
    public String amount() {
        String[] parts = typedAmount().split("\\.");
        String numberBeforeDot = parts[0];
        return String.valueOf(numberBeforeDot);
    }

    public void typeWholeInteger() {

        String expectedNumber = number();
        driver.findElement(InputAmount).sendKeys(expectedNumber);

        //Assert if integer was entered.
        boolean assertTyped = typedAmount().equals(expectedNumber + ".00");
        if (assertTyped) {
            System.out.println("Acceptance Criteria 1,2: Bingo!!! User succesfully typed the amount");
        } else {
            System.out.println("Acceptance Criteria 1,2: Fail, amount is not typed");

        }

    }

    public void typeDecimalNumericAmount() {
        String expectedNumber = decimalNumber();
        driver.findElement(InputAmount).sendKeys(expectedNumber);


        //And now we do assert if the amount we entered is equal to the actual number
        String attribute = driver.findElement(InputAmount).getAttribute("value");

        boolean assertTyped = expectedNumber.equals(attribute);
        if (assertTyped) {
            System.out.println("Acceptance Criteria 2: Bingo!!! User succesfully typed the amount");
        } else {
            System.out.println("Acceptance Criteria 2: Fail, amount is not asserted");
        }
    }

    public void changeCurrencies() {
        driver.findElement(ChangeCurrency).click();
    }

    //Acceptance Criteria 2:
    public void ConvertIt() {
        driver.findElement(ConvertButton).click();

    }

    public void ClosePopUp() throws Exception {

        Thread.sleep(5000); //We are using Thread.sleep method just because we cannot
        // locate the element and interact with it as it is located inside of #shadow-root (closed).
        // So we skip it using SendKeys.

        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
    }

    public void ConversionResults() {
        //display the full conversion amount for the value specified as well as the conversion rate of
        // a single unit for both currencies.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(SingleUnitTarget));
        //verifying if the elements with text of result are visible and displayed
        boolean fullConversionAmount = driver.findElement(result1).isDisplayed();
        boolean sourceResultIsVisible = driver.findElement(ConvertedSource).isDisplayed();
        boolean targetResultIsVisible = driver.findElement(ConvertedTarget).isDisplayed();

        if (fullConversionAmount) {
            System.out.println("Acceptance Criteria 3: Success, full conversion amount is visible");
        }else{
            System.out.println("Acceptance Criteria 3: Fail, full conversion amount isn't visible");
        }

        if (sourceResultIsVisible) {
            System.out.println("Acceptance Criteria 3: Success, source currency is visible");
        } else {
            System.out.println("Acceptance Criteria 3: Fail, source currency is not visible");
        }
        if (targetResultIsVisible) {
            System.out.println("Acceptance Criteria 3: Success, target currency is visible");
        } else {
            System.out.println("Acceptance Criteria 3: Fail, target currency is not visible");
        }

    }
    public String singleUnitTarget() {
        String singleUnitTarget1 = driver.findElement(SingleUnitTarget).getText();
        return String.valueOf(singleUnitTarget1);
    }
    public String singleUnitSource() {
        String singleUnitSource1 = driver.findElement(SingleUnitSource).getText();
        return String.valueOf(singleUnitSource1);
    }
    ////To make some calculations to assert if whether conversion works correct according to the rate
//// we need to extract the number from the text with letters. We use method of substring and split for that reason.
//        //We have 2 of single unit currencies, so we need to extract number from both
    public String extractedSourceNumber() {
        int extractNumber1 = singleUnitSource().indexOf('=');
        String numberAfterEquals = singleUnitSource().substring(extractNumber1 + 1).trim();
        String[] parts = numberAfterEquals.split("\\s+");
        String extractedNumberOfSourceCurrency = parts[0];
        System.out.println("Source single unit number is " + extractedNumberOfSourceCurrency);
        return String.valueOf(extractedNumberOfSourceCurrency);
    }
    public String extractedTargetNumber() {
        int extractNumber2 = singleUnitTarget().indexOf('=');
        String numberAfterEquals1 = singleUnitTarget().substring(extractNumber2 + 1).trim();
        String[] parts1 = numberAfterEquals1.split("\\s+");
        String extractedNumberOfTargetCurrency = parts1[0];
        System.out.println("Target single unit number is " + extractedNumberOfTargetCurrency);
        return String.valueOf(extractedNumberOfTargetCurrency);
        //We extracted the number from Text and now the next step is to multiply received amount by single unit value
        //to make sure that the calculation/conversion is automated and correct.

    }
    public String conversionNumber() {
        By result1 = By.cssSelector("p.result__BigRate-sc-1bsijpp-1.iGrAod:not(:has(span.faded-digit");
        String assertions = driver.findElement(result1).getText();

        int equalsIndex2 = assertions.indexOf('=');
        String numberAfterEquals2 = assertions.substring(equalsIndex2 + 1).trim();
        String[] parts2 = numberAfterEquals2.split("\\s+");
        String number3 = parts2[0];
        return String.valueOf(number3);
    }
    public void AssertConversion() {


        //we get number with getAttribute here:
        String inputValue = driver.findElement(InputAmount).getAttribute("value");
        //converting String into double
        double num1 = Double.parseDouble(inputValue);
        double num2 = Double.parseDouble(extractedSourceNumber());
        //and finally multiplying and getting a result:
        double result = num1 * num2;
        String resultString = Double.toString(result);
//Now we cut the big number and leave only points and hundredths. and excluding thousandths to facilitate
        //assertion process.
        int dotIndex = resultString.indexOf(".");
        String integerPart = resultString.substring(0, dotIndex);
        String decimalPart = resultString.substring(dotIndex + 1, Math.min(dotIndex + 3, resultString.length()));
        String expectedNumber = integerPart + "." + decimalPart;


        //Let's get to conversion result target number we do the same steps with this number: extract, cut.


        int dotIndex2 = conversionNumber().indexOf(".");
        String integerPart2 = conversionNumber().substring(0, dotIndex2);
        String decimalPart2 = conversionNumber().substring(dotIndex2 + 1, Math.min(dotIndex2 + 3, conversionNumber().length()));
        String actualNumber = integerPart2 + "." + decimalPart2;

        //Now through boolean we compare the number we get from multiplying single source currency unit
        // by amount we entered at the beginning. And process it through if-else

        System.out.println("Edited screen number is " + actualNumber);
        boolean resultIsCorrect = actualNumber.equals(expectedNumber);


        if (resultIsCorrect) {
            System.out.println("Acceptance Criteria 4: Success! Conversion is mathematically correct");
        } else {
            System.out.println("Acceptance Criteria 4: Fail, conversion is not correct");
        }
    }

    public void NegativeNumbersConversion() throws Exception {
        //Remove previous value and set the negativeNumber
        String negativeNumber = "-" + number();
        WebElement locator = driver.findElement(InputAmount);
        locator.click();
//As long as other types of wait didn't work out then we try the last chance Thread.sleep. I'm trying not to use it
        //but sometimes it's still necessary.
        Thread.sleep(4000);

        for (int i = 0; i < 3; i++) {
            locator.sendKeys(Keys.BACK_SPACE);
        }
        locator.sendKeys(negativeNumber);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(locator));

        String negativeInput = locator.getAttribute("value");
        System.out.println("negativeInput is " + negativeInput);
        wait.until(ExpectedConditions.elementToBeClickable(ErrorMessage));
        String errorMessage = driver.findElement(ErrorMessage).getText();
        System.out.println("Error Message is " + errorMessage);

        String resultText = driver.findElement(result1).getText();
        if (resultText.contains("-")) {
            System.out.println("Acceptance Criteria 6: Conversion has been processed despite negativeNumber");
        } else {
            System.out.println("Acceptance Criteria 6: Fail, Conversion hasn't been processed despite negativeNumber");
        }

    }

    public void NonNumericValueCheck() throws Exception {

        driver.findElement(FromCurrencyBox).click();
        WebElement locator = driver.findElement(InputAmount);
        locator.click();
        Thread.sleep(4000);

        try {
            for (int i = 0; i < 10; i++) {
                locator.sendKeys(Keys.BACK_SPACE);
            }
        } catch (Exception e) {

        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        locator.sendKeys("sda");
        Thread.sleep(3000);
        String nonNumeric = locator.getAttribute("value");
        System.out.println("negativeInput is " + nonNumeric);
        wait.until(ExpectedConditions.elementToBeClickable(ErrorMessage));
        String errorMessage = driver.findElement(ErrorMessage).getText();
        System.out.println("Error Message is " + errorMessage);

        wait.until(ExpectedConditions.elementToBeClickable(result1));
        String resultText = driver.findElement(result1).getText();
        boolean resultTextContains = resultText.contains("0.00");
        boolean errorMessageEquals = errorMessage.equals("Please enter a valid amount");

        if (resultTextContains) {
            System.out.println("Acceptance Criteria 7: Success, conversion hasn't been process");

        } else {
            System.out.println("Acceptance Criteria 7: Fail, conversion has been process");
        }
        if (errorMessageEquals) {
            System.out.println("Acceptance Criteria 7: Success System display error message");
        }else{
            System.out.println("Acceptance Criteria 7: Fail - System doesn't display error message");
        }
    }

    //User should be able to swap source and target currencies by clicking the ‘Invert Currencies’ button.
// Once the button is clicked on, the conversion is made.
    public void AfterSwapConversion() {
        String beforeSwapNumber = conversionNumber();
        System.out.println("before Swap number " + beforeSwapNumber);
        changeCurrencies();
        String afterSwapNumber = conversionNumber();
        System.out.println("after Swap number " + afterSwapNumber);
        AssertConversion();
        boolean beforeAndAfterEqual = beforeSwapNumber.equals(afterSwapNumber);
        if (beforeAndAfterEqual) {
            System.out.println("Acceptance Criteria 8: Fail of conversion after Swap the currency");
        }else{
            System.out.println("Acceptance Criteria 8: Success the conversion is made after swap.");
        }
    }

    public void URIDataReflection() {
        //Whenever user performs a conversion (or reverses it), the page URI should be updated to reflect the amount,
        // source and target currency for the conversion.
        String beforeUrl = driver.getCurrentUrl();
        System.out.println(beforeUrl);
        changeCurrencies();
        String afterUrl = driver.getCurrentUrl();
        System.out.println(afterUrl);


        String beforeUrlExpected = String.format("https://www.xe.com/currencyconverter/convert/?Amount=" + amount() + "&From=" + targetCurrency() + "&To=" + sourceCurrency() + "");
        String afterUrlExpected = String.format("https://www.xe.com/currencyconverter/convert/?Amount=" + amount() + "&From=" + sourceCurrency() + "&To=" + targetCurrency() + "");
        System.out.println(beforeUrlExpected);
        boolean urlHasChanged = beforeUrl.equals(afterUrl);

        boolean reflectsAmount = beforeUrlExpected.contains("Amount="+ amount() +"");
        boolean containsCurrency = beforeUrlExpected.contains(""+targetCurrency()+"") || beforeUrlExpected.contains(""+sourceCurrency()+"");

        if (urlHasChanged) {
            System.out.println("Criteria acceptance 9: URL hasn't been changed after currencies swap");
        } else {
            System.out.println("Criteria acceptance 9: URL has been changed after currencies swap");
        }
        if (reflectsAmount) {
            System.out.println("Criteria Acceptance 9: URL is reflecting the value entered");
        }else{
            System.out.println("Fail, URL isn't reflecting the value entered to Convert");
        }
        if (containsCurrency) {
            System.out.println("Criteria Acceptance 9: URL is reflecting the source and target currencies entered");
        }else{
            System.out.println("Fail, URL isn't reflecting the currency entered");
        }
    }
    public void UsersAccessToConversionPageThroughString() {
        // If I took it right I need to make sure that user can write a code to URL using some parameters
        //and through driver.get take it to the result?
        String number = number();
        String accessToConversion = String.format("https://www.xe.com/currencyconverter/convert/?Amount=" + number + "&From=" + sourceCurrency() + "&To=" + targetCurrency() + "");
        driver.get(accessToConversion);
        boolean amountEquals = amount().equals(number);

        if (amountEquals) {
            System.out.println("Success, we got the access to Conversion page through using String parameters");
        }else{
            System.out.println("Fail, we haven't gotten the access to Conversion page through using String parameters");
        }
    }
}


//The source and target currency dropdowns should list the most popular currencies at the top of the dropdown,
// and then list all other currencies in alphabetical order.
