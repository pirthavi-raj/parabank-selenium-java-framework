package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utils.Payee;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BillPayPage extends BasePage {

    private final By billPayLink = By.linkText("Bill Pay");
    private final By payeeName = By.name("payee.name");
    private final By address = By.name("payee.address.street");
    private final By city = By.name("payee.address.city");
    private final By state = By.name("payee.address.state");
    private final By zipCode = By.name("payee.address.zipCode");
    private final By phone = By.name("payee.phoneNumber");
    private final By accountNumber = By.name("payee.accountNumber");
    private final By verifyAccount = By.name("verifyAccount");
    private final By amount = By.name("amount");
    private final By fromAccountDropdown = By.name("fromAccountId");
    private final By sendPaymentButton = By.cssSelector("input[value='Send Payment']");
    private final By resultHeading = By.cssSelector("#billpayResult h1.title");
    private final By errorMessages = By.cssSelector("#rightPanel span.error");

    public BillPayPage(WebDriver driver){
        super(driver);
    }

    public BillPayPage open(){
        click(billPayLink);
        waitForVisible(payeeName);
        wait.until(d -> new Select(d.findElement(fromAccountDropdown)).getOptions().size() > 0);
        return this;
    }

    // Empty strings from a CSV simply leave a field blank
    public void payBill(Payee payee, String fromAccount){
        type(payeeName, payee.getName());
        type(address, payee.getAddress());
        type(city, payee.getCity());
        type(state, payee.getState());
        type(zipCode, payee.getZipCode());
        type(phone, payee.getPhone());
        type(accountNumber, payee.getAccountNumber());
        type(verifyAccount, payee.getVerifyAccountNumber());
        type(amount, payee.getAmount());

        new Select(waitForVisible(fromAccountDropdown)).selectByVisibleText(fromAccount);
        click(sendPaymentButton);
    }

    public boolean isPaymentSuccessful() {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(resultHeading, "Bill Payment Complete"));
        } catch (TimeoutException e) {
            return false;
        }
    }
    public List<String> getErrorMessages() {
        List<String> messages = new ArrayList<>();
        for (WebElement element : driver.findElements(errorMessages)) {
            if (element.isDisplayed() && !element.getText().trim().isEmpty()) {
                messages.add(element.getText().trim());
            }
        }
        return messages;
    }

    // True if any visible error message contains the given text
    public boolean hasError(String expectedText){
        return getErrorMessages().stream().anyMatch(message -> message.toLowerCase().contains(expectedText.toLowerCase()));
    }
    }


