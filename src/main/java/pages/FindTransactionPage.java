package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class FindTransactionPage extends BasePage {

    private final By findTransactionsLink = By.linkText("Find Transactions");
    private final By accountDropdown = By.id("accountId");
    private final By transactionIdField = By.id("criteria.transactionId");
    private final By dateField = By.id("criteria.onDate");
    private final By fromDateField = By.id("criteria.fromDate");
    private final By toDateField = By.id("criteria.toDate");
    private final By amountField = By.id("criteria.amount");
    private final By findByIdButton = By.id("findById");
    private final By findByDateButton = By.id("findByDate");
    private final By findByDateRangeButton = By.id("findByDateRange");
    private final By findByAmountButton = By.id("findByAmount");
    private final By resultTable = By.id("transactionTable");
    private final By resultRows = By.cssSelector("#transactionTable tbody tr");
    private final By errorMessage = By.cssSelector("#rightPanel .error");

    public FindTransactionPage(WebDriver driver) {
        super(driver);
    }

    public FindTransactionPage open() {
        click(findTransactionsLink);
        waitForVisible(accountDropdown);
        wait.until(d -> new Select(d.findElement(accountDropdown)).getOptions().size() > 0);
        return this;
    }

    public FindTransactionPage selectAccount(String accountNumber) {
        new Select(waitForVisible(accountDropdown)).selectByVisibleText(accountNumber);
        return this;
    }

    public void findByAmount(String amount){
        type(amountField, amount);
        click(findByAmountButton);
    }

    public void findByTransactionId(String transactionId) {
        type(transactionIdField, transactionId);
        click(findByIdButton);
    }

    // Date format
    public void findByDate(String date) {
        type(dateField, date);
        click(findByDateButton);
    }

    public void findByDateRange(String fromDate, String toDate) {
        type(fromDateField, fromDate);
        type(toDateField, toDate);
        click(findByDateRangeButton);
    }

    public boolean isResultTableDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultTable));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public int getResultCount() {
        if (!isResultTableDisplayed()) {
            return 0;
        }
        return driver.findElements(resultRows).size();
    }

    // Text of every row, so a test can check that an amount or description is present
    public List<String> getResultRowTexts() {
        List<String> rows = new ArrayList<>();
        if (isResultTableDisplayed()) {
            for (WebElement row : driver.findElements(resultRows)) {
                rows.add(row.getText().trim());
            }
        }
        return rows;
    }

    public boolean resultsContain(String text) {
        return getResultRowTexts().stream().anyMatch(row -> row.contains(text));
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

}

