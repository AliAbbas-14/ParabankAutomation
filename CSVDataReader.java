package com.parabank.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVDataReader {
    
    public static Object[][] getLoginTestData() {
        System.out.println("📂 Reading CSV test data...");
        List<Object[]> testData = new ArrayList<>();
        
        try {
            String csvFile = System.getProperty("user.dir") + "/src/test/resources/testdata/testdata.csv";
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String username = values[0].trim();
                    String password = values[1].trim();
                    String expected = values[2].trim().toUpperCase();
                    String description = values.length > 3 ? values[3].trim() : "Test case";
                    
                    testData.add(new Object[]{username, password, expected, description});
                }
            }
            br.close();
            
            System.out.println("✅ Loaded " + testData.size() + " test cases from CSV");
            
        } catch (Exception e) {
            System.out.println("⚠️ Error reading CSV: " + e.getMessage());
            System.out.println("🔄 Using default test data...");
            
            // Default test data
            testData.add(new Object[]{"john", "demo", "SUCCESS", "Valid login with correct credentials"});
            testData.add(new Object[]{"wrong", "wrong", "ERROR", "Invalid login with wrong credentials"});
        }
        
        // Convert to Object[][]
        Object[][] dataArray = new Object[testData.size()][4];
        for (int i = 0; i < testData.size(); i++) {
            dataArray[i] = testData.get(i);
        }
        
        return dataArray;
    }
}