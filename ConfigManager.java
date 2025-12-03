package com.parabank.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    
    private static Properties properties;
    
    static {
        properties = new Properties();
        loadProperties();
    }
    
    private static void loadProperties() {
        try {
            // Try loading from classpath first (recommended)
            InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties");
            
            if (input != null) {
                properties.load(input);
                System.out.println("✅ Configuration loaded from classpath");
            } else {
                // Try loading from file system
                String filePath = "src/main/resources/config.properties";
                FileInputStream fis = new FileInputStream(filePath);
                properties.load(fis);
                fis.close();
                System.out.println("✅ Configuration loaded from file: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("❌ Cannot load config.properties, using defaults");
            setDefaultProperties();
        }
    }
    
    private static void setDefaultProperties() {
        // Browser settings
        properties.setProperty("browser", "chrome");
        properties.setProperty("headless", "false");
        
        // URLs
        properties.setProperty("base.url", "https://parabank.parasoft.com");
        properties.setProperty("login.url", "https://parabank.parasoft.com/parabank/index.htm");
        
        // Timeouts
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("page.load.timeout", "30");
        properties.setProperty("script.timeout", "60");
        
        // Screenshots
        properties.setProperty("screenshot.path", "screenshots/");
        properties.setProperty("screenshot.on.failure", "true");
        properties.setProperty("screenshot.on.success", "false");
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    // Browser Configuration
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    // URL Configuration
    public static String getBaseUrl() {
        return getProperty("base.url", "https://parabank.parasoft.com");
    }
    
    public static String getLoginUrl() {
        return getProperty("login.url", getBaseUrl() + "/parabank/index.htm");
    }
    
    // Timeout Configuration
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }
    
    public static int getScriptTimeout() {
        return Integer.parseInt(getProperty("script.timeout", "60"));
    }
    
    // Screenshot Configuration
    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "screenshots/");
    }
    
    public static boolean takeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }
    
    public static boolean takeScreenshotOnSuccess() {
        return Boolean.parseBoolean(getProperty("screenshot.on.success", "false"));
    }
}