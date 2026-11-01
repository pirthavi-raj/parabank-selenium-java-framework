package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class TransferFundPage extends BasePage {

    private final By transferLink = By.linkText("Transfer Funds");
    private final By amountField = By.id("amount");
    private final By fromAccountDrop = By.id("fromAccountId");
    private final By toAccountDrop = By.id("toAccountId");
    private final By transferButton = By.cssSelector("input[value='Transfer']");
    private final By resultHead = By.cssSelector("#showResult h1.title");
    private final By resultMessage = By.cssSelector("#showResult p");
    private final By errorHead = By.cssSelector("#showError h1.title");
    private final By errorMessage = By.cssSelector("#showError p.error");

    public TransferFundPage(WebDriver driver) {
        super(driver);
    }


    public TransferFundPage open() {
        click(transferLink);
        waitForVisible(amountField);
        wait.until(d -> new Select(d.findElement(fromAccountDrop)).getOptions().size() > 0);
        wait.until(d -> new Select(d.findElement(toAccountDrop)).getOptions().size() > 0);
        return this;

    }

    //if there is amount in string then test can send "", "abc", "-5"
    public void transfer(String amount, String fromAccount, String toAccount) {
        type(amountField, amount);
        new Select(waitForVisible(fromAccountDrop)).selectByVisibleText(fromAccount);
        new Select(waitForVisible(toAccountDrop)).selectByVisibleText(toAccount);
        click(transferButton);
    }

     public boolean isTransferSuccessful() {
         try {
             return wait.until(ExpectedConditions.textToBePresentInElementLocated(resultHead, "Transfer Complete"));
         } catch (TimeoutException e) {
             return false;
         }
     }
         public String getSuccessMessage() {
             return getText(resultMessage);
         }

         public boolean isErrorDisplayed(){
         try{
             wait.until(ExpectedConditions.visibilityOfElementLocated(errorHead));
             return true;
         }catch (TimeoutException e){
             return false;
         }
         }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
     }

