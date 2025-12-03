// File: RunAllTests.java (Optional)
package com.parabank.tests;

import org.testng.annotations.Test;

public class RunAllTests {
    
    @Test
    public void runAllScenarios() {
        System.out.println("=== RUNNING ALL TEST SCENARIOS ===");
        
        // This is just a placeholder - actual tests will run via TestNG configuration
        System.out.println("1. Login Tests");
        System.out.println("2. Account Balance Tests");
        System.out.println("3. Fund Transfer Tests");
        System.out.println("4. Bill Pay Tests");
        System.out.println("5. Logout Tests");
        
        System.out.println("=== ALL TESTS COMPLETED ===");
    }
}