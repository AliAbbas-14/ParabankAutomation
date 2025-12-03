package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BillPayPage {
    
    private WebDriver driver;
    
    // Page Elements
    @FindBy(linkText = "Bill Pay")
    private WebElement billPayLink;
    
    @FindBy(name = "payee.name")
    private WebElement payeeNameField;
    
    @FindBy(name = "payee.address.street")
    private WebElement addressField;
    
    @FindBy(name = "payee.address.city")
    private WebElement cityField;
    
    @FindBy(name = "payee.address.state")
    private WebElement stateField;
    
    @FindBy(name = "payee.address.zipCode")
    private WebElement zipCodeField;
    
    @FindBy(name = "payee.phoneNumber")
    private WebElement phoneField;
    
    @FindBy(name = "payee.accountNumber")
    private WebElement accountNumberField;
    
    @FindBy(name = "verifyAccount")
    private WebElement verifyAccountField;
    
    @FindBy(name = "amount")
    private WebElement amountField;
    
    @FindBy(xpath = "//input[@value='Send Payment']")
    private WebElement sendPaymentButton;
    
    @FindBy(xpath = "//h1[text()='Bill Payment Complete']")
    private WebElement paymentCompleteMessage;
    
    @FindBy(xpath = "//span[@class='error']")
    private WebElement errorMessage;
    
    @FindBy(xpath = "//td[@id='payeeName']")
    private WebElement payeeNameConfirmation;
    
    @FindBy(xpath = "//td[@id='amount']")
    private WebElement amountConfirmation;
    
    @FindBy(xpath = "//td[@id='fromAccountId']")
    private WebElement fromAccountConfirmation;
    
    // Constructor
    public BillPayPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Navigation
    public void navigateToBillPay() {
        billPayLink.click();
    }
    
    // Action Methods
    public void enterPayeeName(String name) {
        payeeNameField.clear();
        payeeNameField.sendKeys(name);
    }
    
    public void enterAddress(String address) {
        addressField.clear();
        addressField.sendKeys(address);
    }
    
    public void enterCity(String city) {
        cityField.clear();
        cityField.sendKeys(city);
    }
    
    public void enterState(String state) {
        stateField.clear();
        stateField.sendKeys(state);
    }
    
    public void enterZipCode(String zipCode) {
        zipCodeField.clear();
        zipCodeField.sendKeys(zipCode);
    }
    
    public void enterPhone(String phone) {
        phoneField.clear();
        phoneField.sendKeys(phone);
    }
    
    public void enterAccountNumber(String accountNumber) {
        accountNumberField.clear();
        accountNumberField.sendKeys(accountNumber);
    }
    
    public void verifyAccountNumber(String accountNumber) {
        verifyAccountField.clear();
        verifyAccountField.sendKeys(accountNumber);
    }
    
    public void enterAmount(String amount) {
        amountField.clear();
        amountField.sendKeys(amount);
    }
    
    public void clickSendPayment() {
        sendPaymentButton.click();
    }
    
    public void payBill(String payeeName, String accountNumber, String amount) {
        enterPayeeName(payeeName);
        enterAddress("123 Main Street");
        enterCity("New York");
        enterState("NY");
        enterZipCode("10001");
        enterPhone("1234567890");
        enterAccountNumber(accountNumber);
        verifyAccountNumber(accountNumber);
        enterAmount(amount);
        clickSendPayment();
    }
    
    // Verification Methods
    public boolean isPaymentSuccessful() {
        try {
            return paymentCompleteMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "No error message found";
        }
    }
    
    public String getPayeeNameConfirmation() {
        try {
            return payeeNameConfirmation.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getAmountConfirmation() {
        try {
            return amountConfirmation.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isPageLoaded() {
        try {
            return driver.getTitle().contains("Bill Pay") || 
                   payeeNameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}