package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginDataDrivenTest extends BaseTest {

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins(){
        return new Object[][]{
                //username, password, expected error
                {"no_such_user_xyz", "Test@1234", "could not be"},
                {"", "Test@1234", "enter a username and password"},
                {"someuser", "", "enter a username and password"},
                {"", "", "enter a username and password"}
        };
    }

    @Test(dataProvider = "invalidLogins", description = "Invalid login data")
    public void invalidLoginTest(String username, String password, String expectedError){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains(expectedError.toLowerCase()),
                 "For username '" + username + "' and password '" + password
                         + "' expected an error containing '" + expectedError + "' but got: '" + error + "'");


        
    }
}
