package tests;

import base.BaseTest;
import base.TestHelper;
import org.checkerframework.checker.index.qual.PolyUpperBound;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BillPayPage;
import utils.Payee;
import utils.TestDataGenerator;

public class BillPayTest extends BaseTest {

    //bill can be paid with valid payee details
    @Test
    public void validBillPaymentTest(){
        TestHelper.registerNewUser(driver);
        String fromAccount = TestHelper.getFirstAccount(driver);

        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.open();
        billPayPage.payBill(TestDataGenerator.createPayee("20"), fromAccount);

        Assert.assertTrue(billPayPage.isPaymentSuccessful(), "Bill payment from account " + fromAccount + " failed");
    }

    //empty bill pay form shows validation errors
    @Test
    public void emptyFromShowsErrorsTest(){
        TestHelper.registerNewUser(driver);
        String fromAccount = TestHelper.getFirstAccount(driver);

        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.open();
        Payee emptyPayee = new Payee("", "", "", "", "", "", "", "","");
        billPayPage.payBill(emptyPayee, fromAccount);

        Assert.assertTrue(billPayPage.hasValidationErrors(),
                "Expected validation errors for an empty form, but none appeared");
    }
    }












