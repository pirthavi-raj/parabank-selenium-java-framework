package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class AccountsOverviewPage extends BasePage {

    private final By overviewLink = By.linkText("Accounts Overview");
    private final By accountTable = By.id("accountTable");
    private final By accountLinks = By.cssSelector("#accountTable tbody tr td:first-child a");

    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
    }

    public AccountsOverviewPage open() {
        click(overviewLink);
        waitForVisible(accountTable);
        return this;
    }

    public boolean isOverviewDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountTable));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Reads the account numbers shown right now, without waiting
    private List<String> readAccountNumbers() {
        List<String> numbers = new ArrayList<>();
        for (WebElement link : driver.findElements(accountLinks)) {
            numbers.add(link.getText().trim());
        }
        return numbers;
    }

    // All account numbers shown in the table, in order
    public List<String> getAccountNumbers() {
        waitForVisible(accountTable);
        wait.until(ExpectedConditions.presenceOfElementLocated(accountLinks));
        return readAccountNumbers();
    }

    // Waits until the given account shows up in the table
    public boolean hasAccount(String accountNumber) {
        waitForVisible(accountTable);
        try {
            wait.ignoring(StaleElementReferenceException.class)
                    .until(d -> readAccountNumbers().contains(accountNumber));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Reads the balance of one account, for example "$100.00" becomes 100.0
    public double getBalance(String accountNumber) {
        By balanceCell = By.xpath("//table[@id='accountTable']//tr[td/a[normalize-space()='"
                + accountNumber + "']]/td[2]");
        String text = getText(balanceCell);
        return Double.parseDouble(text.replaceAll("[^0-9.\\-]", ""));
    }

    // The first account in the table, handy as a source account for transfers
    public String getFirstAccountNumber() {
        List<String> numbers = getAccountNumbers();
        if (numbers.isEmpty()) {
            throw new IllegalStateException("No accounts found in the overview table");
        }
        return numbers.get(0);
    }
}