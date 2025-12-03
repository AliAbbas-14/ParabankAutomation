package com.parabank.hooks;

import com.parabank.utilities.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestHooks {
    
    @Before
    public void setup(Scenario scenario) {
        System.out.println("\n=== Starting Scenario: " + scenario.getName() + " ===");
        System.out.println("Tags: " + scenario.getSourceTagNames());
    }
    
    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        
        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }
        
        // Close driver
        DriverManager.quitDriver();
        
        System.out.println("=== Scenario " + (scenario.isFailed() ? "FAILED" : "PASSED") + 
                         ": " + scenario.getName() + " ===\n");
    }
}