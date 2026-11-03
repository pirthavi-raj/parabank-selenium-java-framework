package tests;

import base.BaseTest;
import base.TestHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.User;

public class LoginLogoutTest extends BaseTest {

    //A registered user can log in with valid credentials

    @Test
    public void validLoginTest(){
        User user = TestHelper.registerNewUser(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.logout();
        loginPage.login(user.getUsername(), user.getPassword());

        Assert.assertTrue(loginPage.isLoggedIn(), "Login failed for user: " + user.getUsername());

    }

    //Login is rejected when the password is wrong
    @Test
    public void invalidPasswordTest(){
        User user = TestHelper.registerNewUser(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.logout();
        loginPage.login(user.getUsername(), "WrongPassword@1");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("could not be verified"), "Expected a login error but got: '" + error + "'");
    }

    //Login is rejected when both fields are empty
    @Test
    public void emptyFieldsTest(){
      //User user = TestHelper.registerNewUser(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains("enter a username and password"),
                "Expected an empty-field error but got: " + error + "'");
     }

     //Logging out returns the user to the login page
    @Test
    public void logoutTest(){
     TestHelper.registerNewUser(driver);

     LoginPage loginPage = new LoginPage(driver);
     loginPage.logout();

     Assert.assertTrue(loginPage.isLoginFormDisplayed(),
             "Login form should be visible after logout");
     }

}
