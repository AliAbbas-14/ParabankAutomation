package com.parabank.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import com.parabank.utils.DemoSiteChecker;
import org.testng.annotations.BeforeMethod;
import com.parabank.config.ConfigManager;
import com.parabank.utilities.DriverManager;

public class BaseTest {
	protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.out.println("=== Browser Setup Start ===");
        
        // Get driver from DriverManager
        driver = DriverManager.getDriver();
        
        // Navigate to base URL
        driver.get(ConfigManager.getBaseUrl());
        
        System.out.println("✅ Navigated to: " + ConfigManager.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("=== Browser Closing ===");
        DriverManager.quitDriver();
    }
}