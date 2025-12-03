package com.parabank.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.parabank.base.BaseTest;
import com.parabank.pages.LoginPage;
import com.parabank.pages.AccountOverviewPage;

public class TestWithPOM extends BaseTest {

    @Test
    public void testValidLogin() {

        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page not visible");

        loginPage.login("john", "demo");

        AccountOverviewPage accountPage = new AccountOverviewPage(driver);

        Assert.assertTrue(accountPage.isPageLoaded(), "Account page not loaded!");

        System.out.println("Login successful");
        System.out.println("Account: " + accountPage.getAccountNumber());
        System.out.println("Balance: " + accountPage.getBalance());
    }

    @Test
    public void testInvalidLogin() {

        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("wrong", "wrong");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error is NOT shown");

        System.out.println("Error Message: " + loginPage.getErrorMessage());
    }
}
