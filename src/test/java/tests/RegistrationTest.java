package tests;

import base.BaseTest;
import base.TestHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterPage;
import utils.TestDataGenerator;
import utils.User;

public class RegistrationTest extends BaseTest {

    //A new user can register with valid details
     @Test
     public void validRegistrationTest(){
     User user = TestDataGenerator.createUser();

         RegisterPage registerPage = new RegisterPage(driver);
         registerPage.openRegisterForm();
         registerPage.register(user);

         Assert.assertTrue(registerPage.isRegistrationSuccessful(),
                 "Registration failed for user: " + user.getUsername());
     }


    //Registration is rejected when the passwords do not match
    @Test
    public void passwordMistmatchTest(){
         User user = TestDataGenerator.createUser();

         RegisterPage registerPage = new RegisterPage(driver);
         registerPage.openRegisterForm();
         registerPage.register(user, "Hellooo999");

         String error = registerPage.getFieldError("repeatedPassword");
         Assert.assertTrue(error.toLowerCase().contains("did not match"),
        "Expected a password mismatch error but got: " + error + "'");
    }

    //Registration is rejected when the username already exists
    @Test
    public void duplicateUsernameTest() {
        User user = TestHelper.registerNewUser(driver);

        // Log out, then try to register the same username again
        new LoginPage(driver).logout();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegisterForm();
        registerPage.register(user);

        String error = registerPage.getFieldError("customer.username");
        Assert.assertTrue(error.toLowerCase().contains("already exists"),
                "Expected a duplicate username error but got: '" + error + "'");


}
}