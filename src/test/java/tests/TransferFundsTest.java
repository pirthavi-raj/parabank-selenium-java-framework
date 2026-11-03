package tests;

import base.BaseTest;
import base.TestHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.TransferFundsPage;

import java.security.PublicKey;

public class TransferFundsTest extends BaseTest {

    @Test
    public void transferBetweenOwnAccountsTest(){
        TestHelper.registerNewUser(driver);
        String fromAccount = TestHelper.getFirstAccount(driver);
        String toAccount = TestHelper.openNewAccount(driver, "SAVINGS");

        TransferFundsPage tranfserPage = new TransferFundsPage(driver);
        tranfserPage.open();
        tranfserPage.transfer("80", fromAccount, toAccount);

        Assert.assertTrue(tranfserPage.isTransferSuccessful(),
                "Transfer from " + fromAccount + " to " + toAccount + " failed");
    }

    //Both balances change by the transferred amount
    @Test
    public void balanceUpdateAfterTransferTest(){
        TestHelper.registerNewUser(driver);
        String fromAccount = TestHelper.getFirstAccount(driver);
        String toAccount = TestHelper.openNewAccount(driver, "SAVINGS");
        double amount = 80.0;


    // Balances before the transfer
    AccountsOverviewPage overview = new AccountsOverviewPage(driver);
        overview.open();
    double fromBefore = overview.getBalance(fromAccount);
    double toBefore = overview.getBalance(toAccount);

    TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.open();
        transferPage.transfer("80", fromAccount, toAccount);
        Assert.assertTrue(transferPage.isTransferSuccessful(), "Transfer failed");


     // Balances after the transfer

      overview.open();
      double fromAfter = overview.getBalance(fromAccount);
      double toAfter = overview.getBalance(toAccount);
      Assert.assertEquals(fromAfter, fromBefore - amount, 0.01, "Sender balance is wrong");
      Assert.assertEquals(toAfter, toBefore + amount, 0.01, "Receiver balance is wrong");
}
}





