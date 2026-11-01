package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private final By usernameLog = By.name("username");
    private final By passwordLog = By.name("password");
    private final By loginButton = By.cssSelector("input[value='Log In']");
    private final By logoutLink = By.linkText("Log Out");
    private final By errorMessage =By.cssSelector("#rightPanel p.error");


    public LoginPage(WebDriver driver){
        super(driver);
    }

    public void login(String username, String password){
        type(usernameLog, username);
        type(passwordLog, password);
        click(loginButton);
    }

    // True when the "Log Out" link appears after login
    public boolean isLoggedIn() {
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
            return true;
        } catch (TimeoutException e){
            return false;
        }
    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }

    // True when the login form is visible again, for example after logout
    public boolean isLoginFormDisplayed(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLog));
            return true;
        }catch(TimeoutException e){
            return false;
        }
    }
}
