package com.parabank.hooks;

import com.parabank.utilities.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class CucumberHooks {
    
    @Before
    public void setup(Scenario scenario) {
        System.out.println("\n=== Starting Cucumber Scenario: " + scenario.getName() + " ===");
        // Driver will be initialized when needed by DriverManager
    }
    
    @After
    public void tearDown(Scenario scenario) {
        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
            } catch (Exception e) {
                System.out.println("Could not take screenshot: " + e.getMessage());
            }
        }
        
        // Close browser after each scenario
        DriverManager.quitDriver();
        
        System.out.println("=== Scenario " + (scenario.isFailed() ? "FAILED" : "PASSED") + 
                         ": " + scenario.getName() + " ===\n");
    }
}