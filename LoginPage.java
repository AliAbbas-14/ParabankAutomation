package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    
    private WebDriver driver;
    
    // Page Elements using @FindBy annotations
    @FindBy(name = "username")
    private WebElement usernameField;
    
    @FindBy(name = "password")
    private WebElement passwordField;
    
    @FindBy(xpath = "//input[@value='Log In']")
    private WebElement loginButton;
    
    @FindBy(linkText = "Register")
    private WebElement registerLink;
    
    @FindBy(partialLinkText = "Forgot")
    private WebElement forgotLoginLink;
    
    @FindBy(className = "error")
    private WebElement errorMessage;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Action Methods
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    
    public void clickLoginButton() {
        loginButton.click();
    }
    
    public void clickRegisterLink() {
        registerLink.click();
    }
    
    public void clickForgotLoginLink() {
        forgotLoginLink.click();
    }
    
    // Combined method for login
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    // Verification Methods
    public boolean isLoginPageDisplayed() {
        return loginButton.isDisplayed();
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        if(isErrorMessageDisplayed()) {
            return errorMessage.getText();
        }
        return "No error message found";
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
}