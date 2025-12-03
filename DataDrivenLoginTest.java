package com.parabank.tests;

import com.parabank.pages.LoginPage;
import com.parabank.pages.AccountOverviewPage;
import com.parabank.utilities.DriverManager;
import com.parabank.utilities.ExcelDataReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataDrivenLoginTest {
    
    WebDriver driver;
    LoginPage loginPage;
    
    @BeforeMethod
    public void setup() {
        System.out.println("\n🔧 Setting up Data-Driven test...");
        driver = DriverManager.getDriver();
    }
    
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        System.out.println("📂 Fetching test data...");
        
        // Try to get data from Excel
        Object[][] excelData = ExcelDataReader.getLoginTestData();
        
        // If Excel data is valid, use it
        if (excelData != null && excelData.length > 0 && excelData[0] != null && excelData[0][0] != null) {
            System.out.println("✅ Loaded " + excelData.length + " test cases from Excel");
            return excelData;
        } else {
            System.out.println("⚠️ Excel data not available. Using hardcoded test data.");
            
            // Return hardcoded test data
            return new Object[][] {
                // Format: {username, password, expectedResult, description}
                {"john", "demo", "SUCCESS", "Valid login with correct credentials"},
                {"wrong", "wrong", "ERROR", "Invalid login with wrong credentials"},
                {"", "demo", "ERROR", "Login with empty username"},
                {"john", "", "ERROR", "Login with empty password"},
                {"admin", "admin123", "SUCCESS", "Admin user login"}
            };
        }
    }
    
    @Test(dataProvider = "loginData")
    public void testLoginWithExcelData(String username, String password, String expectedResult, String description) {
        System.out.println("\n📊 Running Test: " + description);
        System.out.println("👤 Username: " + username);
        System.out.println("🔒 Password: " + (password != null ? password.replaceAll(".", "*") : "null"));
        System.out.println("🎯 Expected: " + expectedResult);
        
        try {
            // Navigate to login page
            driver.get("https://parabank.parasoft.com/parabank/index.htm");
            loginPage = new LoginPage(driver);
            
            // Enter credentials
            if (username != null && !username.isEmpty()) {
                loginPage.enterUsername(username);
            }
            
            if (password != null && !password.isEmpty()) {
                loginPage.enterPassword(password);
            }
            
            loginPage.clickLoginButton();
            
            // Wait for result
            Thread.sleep(2000);
            
            // Take screenshot
            takeScreenshot("datadriven_" + description.replace(" ", "_"));
            
            // Verify result based on expected outcome
            if (expectedResult.equalsIgnoreCase("SUCCESS")) {
                verifySuccessfulLogin(description);
            } else if (expectedResult.equalsIgnoreCase("ERROR")) {
                verifyFailedLogin(description);
            } else {
                System.out.println("⚠️ Unknown expected result: " + expectedResult);
                Assert.fail("Unknown expected result: " + expectedResult);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Test FAILED with exception: " + e.getMessage());
            takeScreenshot("datadriven_error_" + description.replace(" ", "_"));
            Assert.fail("Test failed for: " + description + " - Error: " + e.getMessage());
        }
    }
    
    private void verifySuccessfulLogin(String description) {
        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();
        
        boolean isLoggedIn = currentUrl.contains("overview") || 
                            currentUrl.contains("account") ||
                            pageTitle.contains("Account") || 
                            pageTitle.contains("Overview");
        
        if (isLoggedIn) {
            System.out.println("✅ Test PASSED: Successfully logged in!");
            System.out.println("   URL: " + currentUrl);
            System.out.println("   Title: " + pageTitle);
        } else {
            System.out.println("❌ Test FAILED: Expected successful login but got:");
            System.out.println("   URL: " + currentUrl);
            System.out.println("   Title: " + pageTitle);
            Assert.fail("Expected successful login for: " + description);
        }
    }
    
    private void verifyFailedLogin(String description) {
        boolean hasError = loginPage.isErrorMessageDisplayed();
        
        if (hasError) {
            String errorMessage = loginPage.getErrorMessage();
            System.out.println("✅ Test PASSED: Got expected error - " + errorMessage);
        } else {
            // Check if we're still on login page
            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle();
            
            boolean stillOnLoginPage = currentUrl.contains("index.htm") || 
                                      pageTitle.contains("Welcome") ||
                                      loginPage.isLoginPageDisplayed();
            
            if (stillOnLoginPage) {
                System.out.println("✅ Test PASSED: Login failed as expected (still on login page)");
                System.out.println("   URL: " + currentUrl);
                System.out.println("   Title: " + pageTitle);
            } else {
                System.out.println("❌ Test FAILED: Expected error but got unexpected page");
                System.out.println("   URL: " + currentUrl);
                System.out.println("   Title: " + pageTitle);
                Assert.fail("Expected error but didn't get it for: " + description);
            }
        }
    }
    
    private void takeScreenshot(String fileName) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotsDir = new File("screenshots");
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdir();
            }
            
            // Take screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "screenshots/" + fileName + "_" + System.currentTimeMillis() + ".png";
            Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
            
            System.out.println("📸 Screenshot saved: " + screenshotPath);
        } catch (Exception e) {
            System.out.println("⚠️ Could not save screenshot: " + e.getMessage());
        }
    }
    
    @AfterMethod
    public void tearDown() {
        System.out.println("🧹 Cleaning up Data-Driven test...");
        DriverManager.quitDriver();
    }
}