package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TransferFundsPage {
    
    private WebDriver driver;
    
    // Simple element locators
    @FindBy(name = "amount")
    private WebElement amountField;
    
    @FindBy(xpath = "//input[@type='submit' and contains(@value,'Transfer')]")
    private WebElement transferButton;
    
    // Constructor
    public TransferFundsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Simple methods
    public void enterAmount(String amount) {
        try {
            if (amountField.isDisplayed()) {
                amountField.clear();
                amountField.sendKeys(amount);
                System.out.println("Entered amount: " + amount);
            }
        } catch (Exception e) {
            System.out.println("Could not enter amount: " + e.getMessage());
        }
    }
    
    public void clickTransfer() {
        try {
            if (transferButton.isDisplayed()) {
                transferButton.click();
                System.out.println("Clicked transfer button");
            }
        } catch (Exception e) {
            System.out.println("Could not click transfer button: " + e.getMessage());
            // Try alternative locator
            try {
                driver.findElement(org.openqa.selenium.By.xpath("//input[@value='Transfer']")).click();
            } catch (Exception ex) {
                System.out.println("Alternative also failed: " + ex.getMessage());
            }
        }
    }
    
    public boolean isPageLoaded() {
        try {
            return driver.getCurrentUrl().contains("transfer") || 
                   driver.getTitle().contains("Transfer");
        } catch (Exception e) {
            return false;
        }
    }
}