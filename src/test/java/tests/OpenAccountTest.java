package tests;

import base.BaseTest;
import base.TestHelper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.OpenAccountPage;

public class OpenAccountTest extends BaseTest {

    @DataProvider(name = "accountTypes")
    public Object[][] accountTypes() {
        return new Object[][]{
                {"CHECKING"},
                {"SAVINGS"}
        };
    }

    @Test(dataProvider = "accountTypes", description = "A new account of each type can be opened")
    public void openAccountTest(String accountType) {
        TestHelper.registerNewUser(driver);

        OpenAccountPage openAccountPage = new OpenAccountPage(driver);
        openAccountPage.open();
        openAccountPage.openAccount(accountType);
        Assert.assertTrue(openAccountPage.isAccountOpened(),
                "Opening a " + accountType + " account failed");

        String newAccount = openAccountPage.getNewAccountNumber();

        AccountsOverviewPage overview = new AccountsOverviewPage(driver).open();
        Assert.assertTrue(overview.hasAccount(newAccount),
                "New " + accountType + " account " + newAccount
                        + " is missing from the overview. Accounts found: " + overview.getAccountNumbers());
    }
}
