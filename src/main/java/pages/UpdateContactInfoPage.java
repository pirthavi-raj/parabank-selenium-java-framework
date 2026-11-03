package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UpdateContactInfoPage extends BasePage {

    private final By updateLink = By.linkText("Update Contact Info");
    private final By address = By.id("customer.address.street");
    private final By city = By.id("customer.address.city");
    private final By state = By.id("customer.address.state");
    private final By zipCode = By.id("customer.address.zipCode");
    private final By phone = By.id("customer.phoneNumber");
    private final By updateButton = By.cssSelector("input[value='Update Profile']");
    private final By resultHeading = By.cssSelector("#updateProfileResult h1.title");

    public UpdateContactInfoPage(WebDriver driver){
        super(driver);
    }

    public UpdateContactInfoPage open(){
        click(updateLink);
        waitForVisible(address);
        return this;
    }

    public void updateContactInfo(String newAddress, String newCity, String newState, String newZip, String newPhone){
        type(address, newAddress);
        type(city, newCity);
        type(state, newState);
        type(zipCode, newZip);
        type(phone, newPhone);
        click(updateButton);
    }

    public boolean isProfileUpdated() {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(resultHeading, "Profile Update"));
        } catch (TimeoutException e) {
            return false;
        }
    }
        public String getAddress(){
            return waitForVisible(address).getAttribute("value");
        }
     public String getCity(){
         return waitForVisible(city).getAttribute("value");

    }

    

}
