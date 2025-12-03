package com.parabank.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateTestData {
    
    public static void main(String[] args) {
        createTestDataExcel();
    }
    
    public static void createTestDataExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LoginTestData");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Username");
        headerRow.createCell(1).setCellValue("Password");
        headerRow.createCell(2).setCellValue("ExpectedResult");
        headerRow.createCell(3).setCellValue("TestDescription");
        
        // Create test data rows
        Object[][] testData = {
            {"john", "demo", "SUCCESS", "Valid credentials"},
            {"mary", "demo", "SUCCESS", "Another valid user"},
            {"invalid", "invalid", "ERROR", "Invalid credentials"},
            {"", "", "ERROR", "Empty credentials"},
            {"john", "wrong", "ERROR", "Wrong password"},
            {"wrong", "demo", "ERROR", "Wrong username"}
        };
        
        for (int i = 0; i < testData.length; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(testData[i][0].toString());
            row.createCell(1).setCellValue(testData[i][1].toString());
            row.createCell(2).setCellValue(testData[i][2].toString());
            row.createCell(3).setCellValue(testData[i][3].toString());
        }
        
        // Auto-size columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // Save file
        try (FileOutputStream fileOut = new FileOutputStream("src/test/resources/testdata/testdata.xlsx")) {
            workbook.write(fileOut);
            System.out.println("✅ Test data Excel file created: testdata.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}