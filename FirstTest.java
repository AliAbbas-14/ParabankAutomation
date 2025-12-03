package com.parabank.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class FirstTest {
    
    @Test
    public void testParabankLogin() {
        // Step 1: ChromeDriver setup - AAP KA PATH
    	// Try forward slashes:
    	System.setProperty("webdriver.chrome.driver", "E:\\SQE project\\Selenium\\chromedriver.exe");        System.setProperty("webdriver.chrome.driver", "E:\\SQE project\\Selenium\\chromedriver.exe");
        // Note: Double backslash use karna important hai
        
        WebDriver driver = new ChromeDriver();
        
        try {
            // Step 2: Parabank website open karein
            driver.get("https://parabank.parasoft.com/parabank/index.htm");
            System.out.println(" Website opened successfully!");
            System.out.println("Page title: " + driver.getTitle());
            
            // Step 3: Login fields find karein
            WebElement username = driver.findElement(By.name("username"));
            WebElement password = driver.findElement(By.name("password"));
            WebElement loginBtn = driver.findElement(By.xpath("//input[@value='Log In']"));
            
            System.out.println(" Login fields found!");
            
            // Step 4: Login credentials enter karein
            username.sendKeys("john");
            password.sendKeys("demo");
            System.out.println(" Credentials entered!");
            
            // Step 5: Login button click karein
            loginBtn.click();
            System.out.println(" Login button clicked!");
            
            // Step 6: Wait for page to load
            Thread.sleep(3000); // 3 seconds wait
            
            // Step 7: Check if login successful
            String title = driver.getTitle();
            String currentUrl = driver.getCurrentUrl();
            
            System.out.println("\n=== RESULTS ===");
            System.out.println("Current Page Title: " + title);
            System.out.println("Current URL: " + currentUrl);
            
            if(title.contains("Accounts Overview")) {
                System.out.println(" SUCCESS: Login successful!");
                
                // Extra: Account number show karein
                try {
                    WebElement accountNumber = driver.findElement(By.xpath("//td[@id='accountId']"));
                    System.out.println("Account Number: " + accountNumber.getText());
                } catch (Exception e) {
                    System.out.println("Account number not found, but login successful!");
                }
                
            } else if(currentUrl.contains("index.htm")) {
                System.out.println(" FAILED: Still on login page!");
                
                // Check for error message
                try {
                    WebElement error = driver.findElement(By.className("error"));
                    System.out.println("Error Message: " + error.getText());
                } catch (Exception e) {
                    System.out.println("No error message found");
                }
                
            } else {
                System.out.println(" UNKNOWN: On some other page");
            }
            
        } catch (Exception e) {
            System.out.println("ERROR occurred!");
            System.out.println("Error details: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Step 8: Close browser
            driver.quit();
            System.out.println("\n Browser closed.");
            System.out.println(" TEST COMPLETED!");
        }
    }
}