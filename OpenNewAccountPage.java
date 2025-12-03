package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OpenNewAccountPage {
    
    private WebDriver driver;
    
    @FindBy(linkText = "Open New Account")
    private WebElement openNewAccountLink;
    
    @FindBy(id = "type")
    private WebElement accountTypeDropdown;
    
    @FindBy(id = "fromAccountId")
    private WebElement fromAccountDropdown;
    
    @FindBy(xpath = "//input[@value='Open New Account']")
    private WebElement openNewAccountButton;
    
    @FindBy(xpath = "//h1[text()='Account Opened!']")
    private WebElement accountOpenedMessage;
    
    @FindBy(id = "newAccountId")
    private WebElement newAccountNumber;
    
    @FindBy(xpath = "//td[@id='accountType']")
    private WebElement accountTypeText;
    
    public OpenNewAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void navigateToOpenNewAccount() {
        openNewAccountLink.click();
    }
    
    public void selectAccountType(String type) {
        Select select = new Select(accountTypeDropdown);
        select.selectByVisibleText(type);
    }
    
    public void selectFromAccount(String accountId) {
        Select select = new Select(fromAccountDropdown);
        select.selectByValue(accountId);
    }
    
    public void clickOpenNewAccount() {
        openNewAccountButton.click();
    }
    
    public boolean isAccountOpened() {
        try {
            return accountOpenedMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getNewAccountNumber() {
        try {
            return newAccountNumber.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getAccountType() {
        try {
            return accountTypeText.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public void openNewAccount(String type, String fromAccount) {
        navigateToOpenNewAccount();
        selectAccountType(type);
        selectFromAccount(fromAccount);
        clickOpenNewAccount();
    }
}