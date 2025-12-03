package com.parabank.stepdefinitions;

import com.parabank.pages.AccountOverviewPage;
import com.parabank.utilities.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AccountSteps {
    
    WebDriver driver;
    AccountOverviewPage accountOverviewPage;
    
    @Given("I am logged in to Parabank")
    public void i_am_logged_in_to_parabank() {
        driver = DriverManager.getDriver();
        
        // First go to login page
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        
        // Check if already logged in
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("overview") || currentUrl.contains("account")) {
            System.out.println("✅ Already logged in");
            accountOverviewPage = new AccountOverviewPage(driver);
        } else {
            // Login with default credentials
            LoginSteps loginSteps = new LoginSteps();
            loginSteps.i_am_on_the_parabank_login_page();
            loginSteps.i_enter_username_and_password("john", "demo");
            loginSteps.i_click_the_login_button();
            loginSteps.i_should_see_account_overview_page();
            
            accountOverviewPage = new AccountOverviewPage(driver);
            System.out.println("✅ Successfully logged in to Parabank");
        }
    }
    
    @When("I navigate to accounts page")
    public void i_navigate_to_accounts_page() {
        accountOverviewPage.navigateToAccountsOverview();
        System.out.println("✅ Navigated to accounts page");
    }
    
    @Then("I should see my account balance")
    public void i_should_see_my_account_balance() {
        String balance = accountOverviewPage.getBalance();
        System.out.println("💰 Account Balance: " + balance);
        
        // Check if balance is valid
        boolean hasBalance = !balance.equals("Balance not found") && 
                            !balance.isEmpty() && 
                            !balance.contains("not found");
        
        Assert.assertTrue(hasBalance, "Expected to see account balance but found: " + balance);
        System.out.println("✅ Balance verification successful");
    }
    
    @When("I click on account number")
    public void i_click_on_account_number() {
        accountOverviewPage.clickFirstAccount();
        System.out.println("✅ Clicked on account number");
    }
    
    @Then("I should see account transaction history")
    public void i_should_see_account_transaction_history() {
        String pageTitle = driver.getTitle();
        String currentUrl = driver.getCurrentUrl();
        
        System.out.println("📄 Page Title: " + pageTitle);
        System.out.println("🔗 Current URL: " + currentUrl);
        
        boolean hasTransactionHistory = pageTitle.contains("Account") || 
                                       currentUrl.contains("activity") ||
                                       driver.getPageSource().contains("Transaction");
        
        Assert.assertTrue(hasTransactionHistory, "Expected to see transaction history");
        System.out.println("✅ Transaction history displayed");
    }
    
    @When("I click logout button")
    public void i_click_logout_button() {
        accountOverviewPage.clickLogout();
        System.out.println("✅ Clicked logout button");
    }
    
    @Then("I should be redirected to login page")
    public void i_should_be_redirected_to_login_page() {
        boolean isLoggedOut = accountOverviewPage.isLogoutSuccessful();
        
        if (isLoggedOut) {
            System.out.println("✅ Successfully logged out");
            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle();
            
            boolean isLoginPage = currentUrl.contains("index.htm") || 
                                 pageTitle.contains("Login") || 
                                 pageTitle.contains("Welcome");
            
            Assert.assertTrue(isLoginPage, "Should be on login page after logout");
            System.out.println("✅ Redirected to login page");
        } else {
            Assert.fail("Logout was not successful");
        }
    }
}