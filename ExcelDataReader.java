package com.parabank.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataReader {
    
    public static List<Map<String, String>> getTestData(String filePath, String sheetName) {
        List<Map<String, String>> testData = new ArrayList<>();
        
        System.out.println("🔍 Attempting to read Excel file from: " + filePath);
        
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("⚠️ Excel file not found at: " + filePath);
            System.out.println("🔄 Trying alternative paths...");
            
            // Try alternative paths
            String[] alternativePaths = {
                System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\testdata.xlsx",
                System.getProperty("user.dir") + "/src/test/resources/testdata/testdata.xlsx",
                "src/test/resources/testdata/testdata.xlsx",
                "./src/test/resources/testdata/testdata.xlsx"
            };
            
            for (String altPath : alternativePaths) {
                file = new File(altPath);
                if (file.exists()) {
                    filePath = altPath;
                    System.out.println("✅ Found Excel file at: " + altPath);
                    break;
                }
            }
            
            // If still not found, return empty list
            if (!file.exists()) {
                System.out.println("❌ Excel file not found at any location.");
                return testData;
            }
        }
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.out.println("⚠️ Sheet '" + sheetName + "' not found. Using first sheet.");
                sheet = workbook.getSheetAt(0); // Use first sheet
            }
            
            // Get header row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("No header row found in Excel sheet");
            }
            
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell).toLowerCase());
            }
            
            System.out.println("📊 Headers found: " + headers);
            
            // Read data rows
            int dataCount = 0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData.put(headers.get(j), getCellValueAsString(cell));
                }
                testData.add(rowData);
                dataCount++;
            }
            
            System.out.println("✅ Loaded " + dataCount + " test cases from Excel");
            
        } catch (IOException e) {
            System.out.println("❌ Error reading Excel file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return testData;
    }
    
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Remove .0 if it's an integer
                    double num = cell.getNumericCellValue();
                    if (num == (int) num) {
                        return String.valueOf((int) num);
                    }
                    return String.valueOf(num);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return "";
        }
    }
    
    public static Object[][] getTestDataAsArray(String filePath, String sheetName) {
        List<Map<String, String>> testDataList = getTestData(filePath, sheetName);
        
        if (testDataList.isEmpty()) {
            return null;
        }
        
        Object[][] testDataArray = new Object[testDataList.size()][4];
        
        for (int i = 0; i < testDataList.size(); i++) {
            Map<String, String> row = testDataList.get(i);
            
            // Handle different possible column names
            String username = row.get("username") != null ? row.get("username") : 
                             row.get("user") != null ? row.get("user") : 
                             row.get("user name") != null ? row.get("user name") : "";
            
            String password = row.get("password") != null ? row.get("password") : 
                             row.get("pass") != null ? row.get("pass") : 
                             row.get("pass word") != null ? row.get("pass word") : "";
            
            String expected = row.get("expected") != null ? row.get("expected") : 
                             row.get("expectedresult") != null ? row.get("expectedresult") : 
                             row.get("result") != null ? row.get("result") : "";
            
            String description = row.get("description") != null ? row.get("description") : 
                                row.get("testdescription") != null ? row.get("testdescription") : 
                                "Test case " + (i + 1);
            
            testDataArray[i][0] = username;
            testDataArray[i][1] = password;
            testDataArray[i][2] = expected.toUpperCase(); // Convert to uppercase
            testDataArray[i][3] = description;
        }
        
        return testDataArray;
    }
    
    public static Object[][] getLoginTestData() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/testdata.xlsx";
        System.out.println("📂 Getting login test data from: " + filePath);
        
        Object[][] data = getTestDataAsArray(filePath, "LoginTestData");
        
        if (data == null || data.length == 0) {
            System.out.println("⚠️ No data found in Excel. Using default test data.");
            return getDefaultTestData();
        }
        
        return data;
    }
    
    private static Object[][] getDefaultTestData() {
        System.out.println("📋 Using default test data");
        return new Object[][] {
            {"john", "demo", "SUCCESS", "Valid login with correct credentials"},
            {"wrong", "wrong", "ERROR", "Invalid login with wrong credentials"},
            {"", "demo", "ERROR", "Login with empty username"},
            {"john", "", "ERROR", "Login with empty password"}
        };
    }
    
    // For quick testing
    public static void main(String[] args) {
        System.out.println("Testing ExcelDataReader...");
        Object[][] data = getLoginTestData();
        if (data != null) {
            System.out.println("Test data loaded successfully! Found " + data.length + " test cases.");
        } else {
            System.out.println("No test data loaded.");
        }
    }
}