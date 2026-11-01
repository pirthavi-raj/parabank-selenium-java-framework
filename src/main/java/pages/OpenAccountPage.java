package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import javax.naming.OperationNotSupportedException;


public class OpenAccountPage extends BasePage {

    private final By openAccountLink = By.linkText("Open New Account");
    private final By accountTypeDropdown = By.id("type");
    private final By fromAccountDrop = By.id("fromAccountId");
    private final By openAccountButton =By.cssSelector("input[value='Open New Account']");
    private final By resultHeading = By.cssSelector("#openAccountResult h1.title");
    private final By newAccountId = By.id("newAccountId");

    public OpenAccountPage(WebDriver driver){
        super(driver);
    }

    public OpenAccountPage open(){
        click(openAccountLink);
        waitForVisible(accountTypeDropdown);

        // The "from account" list loads after the page, so wait until it has options
        wait.until(d -> new Select(d.findElement(fromAccountDrop)).getOptions().size() > 0);
        return this;
    }

    //check accountype like is it "Checking" or "Saving"
     public void openAccount(String accountType){
         new Select(waitForVisible(accountTypeDropdown)).selectByVisibleText(accountType);
         click(openAccountButton);
     }

     public boolean isAccountOpened() {
         try {
             return wait.until(ExpectedConditions.textToBePresentInElementLocated(resultHeading, "Account Opened"));
         } catch (TimeoutException e) {
             return false;
         }
     }
        public String getNewAccountNumber(){
            return getText(newAccountId);
         }
     }



