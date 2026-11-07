package tests;

import base.BaseTest;
import base.TestHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FindTransactionPage;
import pages.TransferFundsPage;

public class FindTransactionTest extends BaseTest {

    //A transfer can be found in the transaction history by amount
    @Test
    public void findTransactionByAmountTest(){
        TestHelper.registerNewUser(driver);
        String fromAccount = TestHelper.getFirstAccount(driver);
        String toAccount = TestHelper.openNewAccount(driver, "SAVINGS");

        // Create a transaction with an amount that is easy to search for
        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.open();
        transferPage.transfer("25", fromAccount, toAccount);
        Assert.assertTrue(transferPage.isTransferSuccessful(), "Setup failed: transfer did not work");

        FindTransactionPage findPage = new FindTransactionPage(driver);
        findPage.open();
        findPage.selectAccount(fromAccount);
        findPage.findByAmount("25");

        Assert.assertTrue(findPage.isResultTableDisplayed(), "No result table appeared");
        Assert.assertTrue(findPage.resultsContain("25.00"),
                "The 25.00 transfer is missing from the results: " + findPage.getResultRowTexts());
    }
}
