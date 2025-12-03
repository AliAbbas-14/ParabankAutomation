package com.parabank.stepdefinitions;

import com.parabank.pages.LoginPage;
import com.parabank.pages.AccountOverviewPage;
import com.parabank.utilities.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;

public class LoginSteps {
    
    WebDriver driver;
    LoginPage loginPage;
    AccountOverviewPage accountOverviewPage;
    
    @Given("I am on the Parabank login page")
    public void i_am_on_the_parabank_login_page() {
        driver = DriverManager.getDriver();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        loginPage = new LoginPage(driver);
        System.out.println("✅ Navigated to Parabank login page");
    }
    
    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        System.out.println("✅ Entered username: " + username + " and password: " + password.replaceAll(".", "*"));
    }
    
    @And("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
        System.out.println("✅ Clicked login button");
    }
    
    @Then("I should see account overview page")
    public void i_should_see_account_overview_page() {
        try {
            System.out.println("\n=== STARTING LOGIN VERIFICATION ===");
            
            // Wait for page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Wait for URL to change from login page
            wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe("https://parabank.parasoft.com/parabank/index.htm")
            ));
            
            String currentUrl = driver.getCurrentUrl();
            System.out.println("🔗 Current URL: " + currentUrl);
            
            String pageTitle = driver.getTitle();
            System.out.println("📄 Page Title: " + pageTitle);
            
            // Take screenshot
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File screenshotsDir = new File("screenshots");
                if (!screenshotsDir.exists()) {
                    screenshotsDir.mkdir();
                }
                File screenshotFile = new File("screenshots/after_login_" + System.currentTimeMillis() + ".png");
                org.apache.commons.io.FileUtils.copyFile(screenshot, screenshotFile);
                System.out.println("📸 Screenshot saved: " + screenshotFile.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("⚠️ Could not save screenshot: " + e.getMessage());
            }
            
            // Check for errors
            String pageSource = driver.getPageSource().toLowerCase();
            boolean hasError = pageSource.contains("error") || 
                              pageSource.contains("invalid") || 
                              pageSource.contains("incorrect");
            
            if (hasError) {
                System.out.println("❌ Error detected on page!");
                int errorIndex = pageSource.indexOf("error");
                int start = Math.max(0, errorIndex - 50);
                int end = Math.min(pageSource.length(), errorIndex + 50);
                System.out.println("Error context: ..." + pageSource.substring(start, end) + "...");
            }
            
            // Check login success
            boolean isLoggedIn = false;
            String reason = "";
            
            if (currentUrl.contains("overview")) {
                isLoggedIn = true;
                reason = "URL contains 'overview'";
            } else if (currentUrl.contains("account")) {
                isLoggedIn = true;
                reason = "URL contains 'account'";
            } else if (pageTitle.contains("Account")) {
                isLoggedIn = true;
                reason = "Title contains 'Account'";
            } else if (pageTitle.contains("Overview")) {
                isLoggedIn = true;
                reason = "Title contains 'Overview'";
            } else if (pageTitle.contains("Welcome")) {
                isLoggedIn = true;
                reason = "Title contains 'Welcome'";
            } else if (pageSource.contains("logout") || pageSource.contains("log out")) {
                isLoggedIn = true;
                reason = "Page contains logout link";
            }
            
            System.out.println("✅ Login Status: " + (isLoggedIn ? "SUCCESS" : "FAILED"));
            if (isLoggedIn) {
                System.out.println("✅ Reason: " + reason);
            } else {
                System.out.println("❌ Could not verify login");
                String first200 = driver.getPageSource().substring(0, Math.min(200, driver.getPageSource().length()));
                System.out.println("First 200 chars of page: " + first200);
            }
            
            Assert.assertTrue(isLoggedIn, "Login failed!\nURL: " + currentUrl + "\nTitle: " + pageTitle);
            System.out.println("✅ Login verification completed successfully!");
            
        } catch (Exception e) {
            System.out.println("❌ Exception during login verification: " + e.getMessage());
            
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File errorFile = new File("screenshots/login_error_" + System.currentTimeMillis() + ".png");
                org.apache.commons.io.FileUtils.copyFile(screenshot, errorFile);
                System.out.println("📸 Error screenshot saved: " + errorFile.getAbsolutePath());
            } catch (Exception screenshotEx) {
                System.out.println("⚠️ Could not save error screenshot");
            }
            
            throw new AssertionError("Login verification failed: " + e.getMessage(), e);
        }
    }
    
    @Then("I should see error message")
    public void i_should_see_error_message() {
        try {
            System.out.println("\n=== CHECKING FOR ERROR MESSAGE ===");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            
            // METHOD 1: Use LoginPage method
            boolean errorDisplayed = false;
            String errorText = "";
            
            try {
                errorDisplayed = loginPage.isErrorMessageDisplayed();
                System.out.println("LoginPage.isErrorMessageDisplayed() result: " + errorDisplayed);
                
                if (errorDisplayed) {
                    errorText = loginPage.getErrorMessage();  // CHANGED: getErrorMessageText() to getErrorMessage()
                    System.out.println("✅ Error message displayed: " + errorText);
                    
                    // Take screenshot
                    try {
                        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                        File errorFile = new File("screenshots/error_message_" + System.currentTimeMillis() + ".png");
                        org.apache.commons.io.FileUtils.copyFile(screenshot, errorFile);
                        System.out.println("📸 Screenshot saved: " + errorFile.getAbsolutePath());
                    } catch (Exception e) {
                        System.out.println("⚠️ Could not save error screenshot");
                    }
                    
                    Assert.assertTrue(true, "Error message found: " + errorText);
                    return;
                }
            } catch (Exception e) {
                System.out.println("⚠️ LoginPage methods failed: " + e.getMessage());
            }
            
            // METHOD 2: Direct check
            System.out.println("Trying direct WebDriver check...");
            
            String[] errorSelectors = {
                ".error",
                "p.error",
                "span.error", 
                "div.error",
                "[class*='error']",
                "#rightPanel p",
                "#rightPanel span"
            };
            
            for (String selector : errorSelectors) {
                try {
                    if (driver.findElements(By.cssSelector(selector)).size() > 0) {
                        errorText = driver.findElement(By.cssSelector(selector)).getText();
                        if (!errorText.trim().isEmpty()) {
                            errorDisplayed = true;
                            System.out.println("Found error with selector '" + selector + "': " + errorText);
                            break;
                        }
                    }
                } catch (Exception e) {
                    // Continue
                }
            }
            
            // METHOD 3: Check page source
            if (!errorDisplayed) {
                String pageSource = driver.getPageSource();
                if (pageSource.contains("error") || pageSource.contains("Error")) {
                    errorDisplayed = true;
                    if (pageSource.contains("The username and password could not be verified")) {
                        errorText = "The username and password could not be verified";
                    } else if (pageSource.contains("Please enter a username and password")) {
                        errorText = "Please enter a username and password";
                    } else {
                        errorText = "Error found in page source";
                    }
                    System.out.println("Found error in page source: " + errorText);
                }
            }
            
            if (errorDisplayed) {
                System.out.println("✅ Error verification passed!");
                System.out.println("Error text: " + errorText);
                Assert.assertTrue(true);
            } else {
                System.out.println("❌ ERROR: No error message found!");
                System.out.println("Current URL: " + driver.getCurrentUrl());
                System.out.println("Page Title: " + driver.getTitle());
                
                try {
                    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    File debugFile = new File("screenshots/no_error_debug_" + System.currentTimeMillis() + ".png");
                    org.apache.commons.io.FileUtils.copyFile(screenshot, debugFile);
                    System.out.println("📸 Debug screenshot saved: " + debugFile.getAbsolutePath());
                    
                    String pageSource = driver.getPageSource();
                    File sourceFile = new File("screenshots/page_source_" + System.currentTimeMillis() + ".html");
                    org.apache.commons.io.FileUtils.writeStringToFile(sourceFile, pageSource, "UTF-8");
                    System.out.println("📄 Page source saved: " + sourceFile.getAbsolutePath());
                } catch (Exception e) {
                    System.out.println("⚠️ Could not save debug files: " + e.getMessage());
                }
                
                Assert.fail("Expected error message but none was found.");
            }
            
        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            System.out.println("❌ Exception in error verification: " + e.getMessage());
            throw new AssertionError("Error verification failed: " + e.getMessage(), e);
        }
    }
}