package com.parabank.stepdefinitions;

import com.parabank.pages.BillPayPage;
import com.parabank.utilities.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class BillPaySteps {
    
    WebDriver driver;
    BillPayPage billPayPage;
    
    @When("I navigate to Bill Pay page")
    public void i_navigate_to_bill_pay_page() {
        driver = DriverManager.getDriver();
        billPayPage = new BillPayPage(driver);
        billPayPage.navigateToBillPay();
    }
    
    @When("I enter payee details:")
    public void i_enter_payee_details(io.cucumber.datatable.DataTable dataTable) {
        // FIX: Changed 'var' to explicit type declaration
        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = dataList.get(0);
        
        billPayPage.enterPayeeName(data.get("Payee Name"));
        billPayPage.enterAddress(data.get("Address"));
        billPayPage.enterCity(data.get("City"));
        billPayPage.enterState(data.get("State"));
        billPayPage.enterZipCode(data.get("Zip Code"));
        billPayPage.enterPhone(data.get("Phone"));
    }
    
    @When("I enter account number {string}")
    public void i_enter_account_number(String accountNumber) {
        billPayPage.enterAccountNumber(accountNumber);
        billPayPage.verifyAccountNumber(accountNumber);
    }
    
    @When("I enter amount {string}")
    public void i_enter_amount(String amount) {
        billPayPage.enterAmount(amount);
    }
    
    @When("I click Send Payment")
    public void i_click_send_payment() {
        billPayPage.clickSendPayment();
    }
    
    @Then("I should see payment confirmation")
    public void i_should_see_payment_confirmation() {
        Assert.assertTrue(billPayPage.isPaymentSuccessful(), 
            "Payment was not successful");
    }
    
    @Then("I should see payee name {string}")
    public void i_should_see_payee_name(String expectedName) {
        String actualName = billPayPage.getPayeeNameConfirmation();
        Assert.assertEquals(actualName, expectedName, 
            "Payee name doesn't match");
    }
    
    @When("I pay bill to {string} with account {string} amount {string}")
    public void i_pay_bill_to_with_account_amount(String payee, String account, String amount) {
        billPayPage.payBill(payee, account, amount);
    }
    
    @Then("payment should be {string}")
    public void payment_should_be(String expectedResult) {
        if (expectedResult.equals("successful")) {
            Assert.assertTrue(billPayPage.isPaymentSuccessful());
        } else {
            Assert.assertTrue(billPayPage.isErrorMessageDisplayed());
        }
    }
    
    @When("I click Send Payment without entering details")
    public void i_click_send_payment_without_entering_details() {
        billPayPage.clickSendPayment();
    }
    
    @Then("I should see validation errors")
    public void i_should_see_validation_errors() {
        Assert.assertTrue(billPayPage.isErrorMessageDisplayed());
    }
}