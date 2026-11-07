package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.AccountsOverviewPage;
import pages.OpenAccountPage;
import pages.RegisterPage;
import utils.ConfigReader;
import utils.TestDataGenerator;
import utils.User;

public class TestHelper {

    // Registers a brand new user. ParaBank logs the user in automatically afterwards.
    // The demo site sometimes fails once, so we try twice, each time with a fresh user.
    public static User registerNewUser(WebDriver driver) {
        RegisterPage registerPage = new RegisterPage(driver);

        for (int attempt = 1; attempt <= 2; attempt++) {
            if (attempt > 1) {
                driver.get(ConfigReader.get("base.url"));
            }
            User user = TestDataGenerator.createUser();
            registerPage.openRegisterForm();
            registerPage.register(user);

            if (registerPage.isRegistrationSuccessful()) {
                return user;
            }
            System.out.println("Registration attempt " + attempt + " failed. URL: "
                    + driver.getCurrentUrl() + " | PAGE: " + pageStart(driver));
        }
        Assert.fail("Setup failed: could not register a user after 2 attempts. Page: " + pageStart(driver));
        return null;
    }

    // The account number every new user starts with
    public static String getFirstAccount(WebDriver driver) {
        return new AccountsOverviewPage(driver).open().getFirstAccountNumber();
    }

    // Opens a CHECKING or SAVINGS account and returns its number
    public static String openNewAccount(WebDriver driver, String accountType) {
        OpenAccountPage openAccountPage = new OpenAccountPage(driver);
        openAccountPage.open();
        openAccountPage.openAccount(accountType);

        Assert.assertTrue(openAccountPage.isAccountOpened(),
                "Setup failed: could not open a " + accountType + " account");
        return openAccountPage.getNewAccountNumber();
    }

    private static String pageStart(WebDriver driver) {
        String body = driver.findElement(By.tagName("body")).getText();
        return body.substring(0, Math.min(300, body.length())).replace("\n", " ");
    }
}