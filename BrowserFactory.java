package com.parabank.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {
    
    public static WebDriver createDriver(String browserType) {
        return createDriver(browserType, false);
    }
    
    public static WebDriver createDriver(String browserType, boolean headless) {
        WebDriver driver = null;
        String browser = browserType.toLowerCase();
        
        try {
            switch(browser) {
                case "chrome":
                    driver = setupChromeDriver(headless);
                    break;
                    
                case "firefox":
                    driver = setupFirefoxDriver(headless);
                    break;
                    
                case "edge":
                    driver = setupEdgeDriver(headless);
                    break;
                    
                default:
                    System.out.println("⚠️ Unknown browser: " + browser + ". Using Chrome.");
                    driver = setupChromeDriver(headless);
            }
            
            // Common settings
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            
        } catch (Exception e) {
            System.err.println("❌ Failed to create WebDriver for " + browser + ": " + e.getMessage());
            throw new RuntimeException("WebDriver initialization failed", e);
        }
        
        return driver;
    }
    
    private static WebDriver setupChromeDriver(boolean headless) {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        
        return new ChromeDriver(options);
    }
    
    private static WebDriver setupFirefoxDriver(boolean headless) {
        org.openqa.selenium.firefox.FirefoxOptions options = new org.openqa.selenium.firefox.FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--disable-notifications");
        
        return new FirefoxDriver(options);
    }
    
    private static WebDriver setupEdgeDriver(boolean headless) {
        org.openqa.selenium.edge.EdgeOptions options = new org.openqa.selenium.edge.EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        
        return new EdgeDriver(options);
    }
}