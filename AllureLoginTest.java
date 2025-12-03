package com.parabank.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.parabank.pages.LoginPage;
import com.parabank.pages.AccountOverviewPage;
import com.parabank.utilities.DriverManager;

public class AllureLoginTest {
    
    WebDriver driver;
    
    @BeforeMethod
    public void setup() {
        System.out.println("Setting up Allure test...");
        driver = DriverManager.getDriver();
    }
    
    @Test
    public void testValidLogin() {
        System.out.println("Running Allure valid login test...");
        
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        LoginPage loginPage = new LoginPage(driver);
        
        // Verify login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        
        // Login
        loginPage.login("john", "demo");
        
        // Wait
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verify success
        AccountOverviewPage accountPage = new AccountOverviewPage(driver);
        Assert.assertTrue(accountPage.isPageLoaded(), "Should be logged in");
        
        System.out.println("Allure test passed!");
    }
    
    @Test
    public void testInvalidLogin() {
        System.out.println("Running Allure invalid login test...");
        
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login("wrong", "wrong");
        
        // Wait
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Should show error");
        System.out.println("Error: " + loginPage.getErrorMessage());
    }
    
    @AfterMethod
    public void tearDown() {
        System.out.println("Cleaning up Allure test...");
        DriverManager.quitDriver();
    }
}