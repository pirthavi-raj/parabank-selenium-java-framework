package tests;

import base.BaseTest;
import base.TestHelper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BillPayPage;
import utils.CSVReader;
import utils.Payee;

public class BillPayDataTest extends BaseTest {

    @DataProvider(name = "billPayData")
    public Object[][] billPayData() {
        return CSVReader.read("data/billpay_validation.csv");
    }

    @Test(dataProvider = "billPayData", description = "Bill pay shows the right error for missing or invalid fields")
    public void billPayValidationTest(String description, String payeeName, String address,
                                      String city, String state, String zipCode, String phone,
                                      String accountNumber, String verifyAccount, String amount,
                                      String expectedError) {

        TestHelper.registerNewUser(driver);
        String fromAccount = TestHelper.getFirstAccount(driver);

        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.open();

        Payee payee = new Payee(payeeName, address, city, state, zipCode, phone,
                accountNumber, verifyAccount, amount);
        billPayPage.payBill(payee, fromAccount);

        Assert.assertTrue(billPayPage.hasValidationErrors(),
                "[" + description + "] expected validation errors but none appeared");
        Assert.assertTrue(billPayPage.hasError(expectedError),
                "[" + description + "] expected an error containing '" + expectedError
                        + "' but got: " + billPayPage.getErrorMessages());
    }
}