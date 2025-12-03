package com.parabank.stepdefinitions;

import com.parabank.pages.TransferFundsPage;
import com.parabank.utilities.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class TransferSteps {
    
    WebDriver driver;
    TransferFundsPage transferFundsPage;
    
    @Given("I have at least two accounts")
    public void i_have_at_least_two_accounts() {
        driver = DriverManager.getDriver();
        // Just a placeholder - in real scenario check account count
        System.out.println("Assuming user has multiple accounts");
    }
    
    @When("I navigate to Transfer Funds page")
    public void i_navigate_to_transfer_funds_page() {
        driver.get("https://parabank.parasoft.com/parabank/transfer.htm");
        transferFundsPage = new TransferFundsPage(driver);
    }
    
    @When("I select from account {string}")
    public void i_select_from_account(String fromAccount) {
        Select fromDropdown = new Select(driver.findElement(By.id("fromAccountId")));
        fromDropdown.selectByVisibleText(fromAccount);
    }
    
    @When("I select to account {string}")
    public void i_select_to_account(String toAccount) {
        Select toDropdown = new Select(driver.findElement(By.id("toAccountId")));
        toDropdown.selectByVisibleText(toAccount);
    }
    
    @When("I click Transfer button")
    public void i_click_transfer_button() {
        driver.findElement(By.xpath("//input[@value='Transfer']")).click();
    }
    
    @Then("I should see transfer success message")
    public void i_should_see_transfer_success_message() {
        String pageSource = driver.getPageSource();
        boolean hasSuccess = pageSource.contains("Transfer Complete") || 
                           pageSource.contains("successfully");
        Assert.assertTrue(hasSuccess, "Transfer success message not found");
    }
    
    @Then("I should see amount {string} transferred")
    public void i_should_see_amount_transferred(String amount) {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(amount), 
            "Amount " + amount + " not found in transfer confirmation");
    }
    
    @Then("I should see insufficient funds error")
    public void i_should_see_insufficient_funds_error() {
        String pageSource = driver.getPageSource().toLowerCase();
        boolean hasError = pageSource.contains("insufficient") || 
                          pageSource.contains("error");
        Assert.assertTrue(hasError, "Insufficient funds error not found");
    }
}