package com.parabank.utilities;

import org.openqa.selenium.WebDriver;
import com.parabank.utils.BrowserFactory;
import com.parabank.config.ConfigManager;

public class DriverManager {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }
    
    private static void initializeDriver() {
        // Get configuration
        String browser = ConfigManager.getBrowser();
        boolean headless = ConfigManager.isHeadless();
        
        // Create driver
        WebDriver webDriver = BrowserFactory.createDriver(browser, headless);
        driver.set(webDriver);
        
        System.out.println("✅ " + browser.toUpperCase() + " Driver initialized");
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            System.out.println("✅ Browser closed");
        }
    }
}