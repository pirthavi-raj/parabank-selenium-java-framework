package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.User;

public class RegisterPage extends BasePage {

    private final By registerLink = By.linkText("Register");
    private final By firstName = By.id("customer.firstname");
    private final By lastName = By.id("customer.lastname");
    private final By address = By.id("customer.address.street");
    private final By city = By.id("customer.address.state");
    private final By state = By.id("customer.address.state");
    private final By zipCode = By.id("customer.address.zipCode");
    private final By phone =By.id("customer.phoneNumber");
    private final By ssn = By.id("customer.ssn");
    private final By username = By.id("customer.username");
    private final By password = By.id("customer.password");
    private final By confirmPassword = By.id("repeatedPassword");
    private final By registerButton = By.cssSelector("input[value='Register]");
    private final By heading = By.cssSelector("#rightPanel h1.title");

    public RegisterPage(WebDriver driver){
        super(driver);
    }

    public RegisterPage openRegisterForm(){
        click(registerLink);
        waitForVisible(firstName);
        return this;
    }

    public RegisterPage fillForm(User user, String confirmPasswordValue){
        type(firstName, user.getFirstName());
        type(lastName, user.getLastName());
        type(address, user.getAddress());
        type(city, user.getCity());
        type(state, user.getState());
        type(zipCode, user.getZipCode());
        type(phone, user.getPhone());
        type(ssn, user.getSsn());
        type(username, user.getUsername());
        type(password, user.getPassword());
        type(confirmPassword, confirmPasswordValue);
        return this;
    }

    public void submit(){
        click(registerButton);
    }

    // Normal registration: confirm password matches the password
    public void register(User user){
        fillForm(user, user.getPassword());
        submit();
    }

    // Used for the password mismatch test
    public void register(User user, String confirmPasswordValue){
        fillForm(user, confirmPasswordValue);
        submit();
    }

    public boolean isRegistrationSuccessful() {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(heading, "Welcome"));
        } catch (TimeoutException e) {
            return false;
        }
    }
        public String getHeadingText(){
        return getText(heading);
    }

    public String getFieldError(String fieldId){
        By error =By.id(fieldId + ".errors");
        return isDisplay(error) ? getText(error): "";
    }

}
