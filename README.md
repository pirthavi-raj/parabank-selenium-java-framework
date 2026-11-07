ParaBank Selenium Java Framework

Hello everyone, 
I built this project to practice Selenium with Java and to show how I structure a test automation 
framework from scratch. I tests ParaBank, a demo banking website made for test practice, registering a user, logging in, 
opening accounts, transferring money, paying bills and so on.

**What I used**
Java 17+
Selenium WebDriver 4.25 (Selenium Manager downloads the browser driver, so there is no manual driver setup)
TestNG 7.10 for tests, assertions and data providers
Maven to manage dependencies and run the tests
Page Object Model to keep locators out of the tests
CSV files and TestNG @DataProvider for data-driven testing

**What the framework can do**
Every page has its own class (Page Object Model), so if a locator changes I fix it in one place.
Every test creates its own new user, so no test depends on old data or on another test.
It supports Chrome, Firefox and Edge, with headless mode.
I use explicit waits only, with no Thread.sleep().
Test data comes from @DataProvider methods and from CSV files.
The setup step tries to register a user twice, because the public demo site sometimes fails once.

**Project structure**
parabank-selenium-java-framework/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/java/
    │   ├── base/        BasePage, DriverFactory
    │   ├── pages/       the page objects
    │   └── utils/       config, CSV reader, test data
    └── test/
        ├── java/
        │   ├── base/    BaseTest, TestHelper
        │   └── tests/   all test classes
        └── resources/
            ├── config.properties
            ├── testng.xml
            └── data/    CSV files
            
**What each class does**
Base
BasePage: the parent of all my page classes. It holds the driver and an explicit wait, and has click, type, getText and 
          similar methods that wait for the element first.
DriverFactory: creates the browser (Chrome, Firefox or Edge, headless or not) in one place, so no test creates a browser 
          itself.
          
Pages
RegisterPage: the registration form. It fills the form from a User object, submits it, and reads the success heading or a 
             field error.
LoginPage: logs in and out, reads the login error, and checks if the user is logged in.
AccountsOverviewPage: reads the accounts table: account numbers, whether an account exists, and an account's balance as a 
                      number.
OpenAccountPage: opens a Checking or Savings account and returns the new account number.
TransferFundsPage: transfers money between two accounts and reads the success or error message.
BillPayPage: fills in the payee details from a Payee object, sends the payment, and reads validation errors.
FindTransactionPage: searches an account's transactions and checks whether the results contain a given text.
UpdateContactInfoPage: changes the address details and reads the saved values back.
utils (main)
ConfigReader: reads config.properties, and a value given on the command line (like -Dbrowser=firefox) wins over the file.
CsvReader: reads a CSV file and gives the rows to a @DataProvider.
User: holds all registration details in one object.
Payee: holds all bill pay form values in one object.
TestDataGenerator: makes a unique username, a random SSN, a full valid user and a valid payee.
base (test)
BaseTest: the parent of all my tests. It opens a fresh browser before each test and always closes it afterwards.
TestHelper: shared setup steps: register a new user (with one retry), get the first account number, open a new account.
tests
RegistrationTest: valid registration, password mismatch, duplicate username.
LoginLogoutTest: valid login, wrong password, empty fields, logout.
OpenAccountTest: opens a Checking and a Savings account with a @DataProvider and checks the overview.
TransferFundsTest: a valid transfer, and a before and after balance check.
BillPayTest: a valid payment and an empty form.
FindTransactionTest: makes a transfer, then finds it in the history by amount.
UpdateContactInfoTest: updates contact info and checks that it was saved.
LoginDataDrivenTest: one login test, many invalid inputs.
TransferDataDrivenTest: one transfer test, many invalid amounts.
RegistrationDataTest: one registration test that runs for every row of registration_validation.csv.
BillPayDataTest: one bill pay test that runs for every row of billpay_validation.csv.

**Resources**
config.properties: base URL, browser, headless and wait time.
testng.xml: the list of test classes that Maven runs.
registration_validation.csv and billpay_validation.csv: the test data for the data-driven tests.

**What we need**
JDK 17 or newer
Maven 3.8 or newer
Git
Google Chrome (Firefox or Edge also work)
An internet connection, because the tests use the public ParaBank site

..................................................................................
**How to run it**
Clone the project and check that it builds:

bash
git clone https://github.com/pirthavi-raj/parabank-selenium-java-framework.git
cd parabank-selenium-java-framework
mvn clean test-compile

....................................................................................

**Problems I ran into (and what I learned)**
Locators are case sensitive. customer.firstname and customer.firstName are different, and the wrong one made every 
registration test time out. Now I copy ids straight from DevTools.
Some pages load their dropdowns late. The account dropdowns on ParaBank fill in after the page loads, so I added explicit 
waits until they have options.
The demo site is not always stable. It can be slow, fail now and then, and reset its data. So every test makes its own user, 
the wait time is configurable, and registration in the setup retries once.
Special characters break registration. An address with ü made ParaBank return an HTTP 400 error, so my test data uses plain
English letters.
A test can fail because the page is in a different state than I expected. so I learned to trust the Maven command as the 
reference.

......................................................................................

**Things I found in ParaBank**

While writing the data-driven tests I noticed some behavior a real bank should not have:

- Transfers of 0 and negative amounts are accepted without any error.
- Empty or text amounts show a generic "internal error" and not a clear validation message.
- The login error differs: an unknown username says "could not be found", and a known username with a wrong password says
  "could not be verified".

......................................................................................
If you have feedback or ideas to improve this project, I'd be happy to hear them.
