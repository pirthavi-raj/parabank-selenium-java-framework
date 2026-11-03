package base;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.AccountsOverviewPage;
import pages.OpenAccountPage;
import pages.RegisterPage;
import utils.TestDataGenerator;
import utils.User;

public class TestHelper {

    public static User registerNewUser(WebDriver driver){
        User user = TestDataGenerator.createUser();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegisterForm();
        registerPage.register(user);

        Assert.assertTrue(registerPage.isRegistrationSuccessful(),
                "Setup failed: could not register user " + user.getUsername());

        return user;
    }

    // The account number every new user starts with
      public static String getFirstAccount(WebDriver driver){
        return new AccountsOverviewPage(driver).open().getFirstAccountNumber();
      }


    // Opens a CHECKING or SAVINGS account and returns its number
      public static String openNewAccount(WebDriver driver, String accountTye){
          OpenAccountPage openAccountPage = new OpenAccountPage(driver);
          openAccountPage.open();
          openAccountPage.openAccount(accountTye);

          Assert.assertTrue(openAccountPage.isAccountOpened(), "Setup failed: could not open a " + " account");
          return openAccountPage.getNewAccountNumber();
      }









}
