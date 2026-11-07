package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.CSVReader;
import utils.TestDataGenerator;
import utils.User;

public class RegistrationDataTest extends BaseTest {

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        return CSVReader.read("data/registration_validation.csv");
    }
    @Test(dataProvider = "registrationData", description = "Registration shows the right error for missing or invalid fields")
    public void registrationValidationTest(String description, String firstName, String lastName,
                                           String address, String city, String state, String zipCode,
                                           String ssn, String username, String password,
                                           String confirmPassword, String errorFieldId,
                                           String expectedError) {

        // AUTO means: generate a unique username
        String finalUsername = username.equals("AUTO") ? TestDataGenerator.uniqueUsername() : username;

        User user = new User(firstName, lastName, address, city, state, zipCode,
                "0123456789", ssn, finalUsername, password);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegisterForm();
        registerPage.register(user, confirmPassword);

        String error = registerPage.getFieldError(errorFieldId);
        Assert.assertTrue(error.toLowerCase().contains(expectedError.toLowerCase()),
                "[" + description + "] expected an error containing '" + expectedError
                        + "' on field '" + errorFieldId + "' but got: '" + error + "'");
    }

}
