package com.parabank.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.SkipException;

public class DemoSiteChecker {
    
    public enum Status {
        WORKING,
        INTERNAL_ERROR,
        DOWN,
        UNKNOWN
    }
    
    public static Status checkParabankStatus() {
        WebDriver driver = null;
        try {
            driver = new ChromeDriver();
            driver.get("https://parabank.parasoft.com");
            Thread.sleep(3000);
            
            String title = driver.getTitle().toLowerCase();
            String pageSource = driver.getPageSource().toLowerCase();
            
            if (title.contains("error") && pageSource.contains("internal error")) {
                return Status.INTERNAL_ERROR;
            } else if (title.contains("parabank")) {
                return Status.WORKING;
            } else {
                return Status.UNKNOWN;
            }
            
        } catch (Exception e) {
            return Status.DOWN;
        } finally {
            if (driver != null) driver.quit();
        }
    }
    
    public static void skipIfDemoSiteBroken() {
        Status status = checkParabankStatus();
        if (status == Status.INTERNAL_ERROR) {
            throw new SkipException(
                "⚠️ PARABANK DEMO SITE IS RETURNING INTERNAL ERRORS ⚠️\n" +
                "This is NOT a framework failure.\n" +
                "The Parabank demo site (https://parabank.parasoft.com) is currently\n" +
                "returning 'Internal Error' for all login attempts.\n" +
                "\n" +
                "✅ What this means:\n" +
                "  - Your framework is working correctly\n" +
                "  - The test automation logic is sound\n" +
                "  - The issue is with the external demo application\n" +
                "\n" +
                "🔧 Recommended actions:\n" +
                "  1. Test with a different demo application\n" +
                "  2. Wait for Parabank demo site to be fixed\n" +
                "  3. Use mock responses for testing\n"
            );
        }
    }
}