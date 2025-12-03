package com.parabank.utilities;

import java.util.*;

public class DatabaseReader {
    
    public static List<Map<String, String>> getTestData() {
        System.out.println("📊 Using Mock Database Reader");
        
        List<Map<String, String>> testData = new ArrayList<>();
        
        // Add test cases
        addTestCase(testData, 1, "john", "demo", "SUCCESS", "Valid login");
        addTestCase(testData, 2, "mary", "demo", "SUCCESS", "Valid login for Mary");
        addTestCase(testData, 3, "wrong", "wrong", "ERROR", "Invalid credentials");
        addTestCase(testData, 4, "", "demo", "ERROR", "Empty username");
        addTestCase(testData, 5, "john", "", "ERROR", "Empty password");
        
        System.out.println("✅ Generated " + testData.size() + " mock test cases");
        return testData;
    }
    
    private static void addTestCase(List<Map<String, String>> testData, 
                                   int id, String user, String pass, 
                                   String expected, String desc) {
        Map<String, String> row = new HashMap<>();
        row.put("id", String.valueOf(id));
        row.put("username", user);
        row.put("password", pass);
        row.put("expected_result", expected);
        row.put("description", desc);
        testData.add(row);
    }
    
    public static Object[][] getTestDataAsArray() {
        List<Map<String, String>> testData = getTestData();
        Object[][] array = new Object[testData.size()][4];
        
        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> row = testData.get(i);
            array[i][0] = row.get("username");
            array[i][1] = row.get("password");
            array[i][2] = row.get("expected_result");
            array[i][3] = row.get("description");
        }
        
        return array;
    }
}