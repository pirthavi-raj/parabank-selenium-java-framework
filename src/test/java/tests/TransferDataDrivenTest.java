package tests;

import base.BaseTest;
import base.TestHelper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.TransferFundsPage;

public class TransferDataDrivenTest extends BaseTest {

    @DataProvider(name = "invalidAmounts")
    public Object[][] invalidAmounts() {
        return new Object[][]{
                // amount, expected part of the error message
                {"", "internal error"},
                {"abc", "internal error"}
        };
    }

    @Test(dataProvider = "invalidAmounts", description = "Invalid transfer amounts are rejected")
    public void invalidAmountTest(String amount, String expectedError) {
        TestHelper.registerNewUser(driver);
        String fromAccount = TestHelper.getFirstAccount(driver);
        String toAccount = TestHelper.openNewAccount(driver, "SAVINGS");

        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.open();
        transferPage.transfer(amount, fromAccount, toAccount);

        Assert.assertTrue(transferPage.isErrorDisplayed(),
                "Expected an error for amount '" + amount + "' but none appeared");

        String error = transferPage.getErrorMessage();
        Assert.assertTrue(error.toLowerCase().contains(expectedError.toLowerCase()),
                "For amount '" + amount + "' expected an error containing '"
                        + expectedError + "' but got: '" + error + "'");
    }
}