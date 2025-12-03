package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class AccountOverviewPage {
    
    private WebDriver driver;
    
    // Page Elements
    @FindBy(xpath = "//h1[contains(text(),'Accounts Overview')]")
    private WebElement pageTitle;
    
    @FindBy(xpath = "//td[@class='ng-binding']")
    private List<WebElement> accountDetails;
    
    @FindBy(xpath = "//a[contains(@href, 'activity.htm?id=')]")
    private List<WebElement> accountLinks;
    
    @FindBy(xpath = "//tr[@ng-repeat='account in accounts']/td[1]/a")
    private List<WebElement> accountNumberLinks;
    
    @FindBy(linkText = "Accounts Overview")
    private WebElement accountsOverviewLink;
    
    @FindBy(partialLinkText = "Log Out")
    private WebElement logoutLink;
    
    @FindBy(xpath = "//td[@headers='balance']")
    private List<WebElement> balanceCells;
    
    @FindBy(xpath = "//div[@id='rightPanel']/div/div/h1")
    private WebElement accountHeader;
    
    // Constructor
    public AccountOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    // Verification Methods
    public boolean isPageLoaded() {
        try {
            return pageTitle.isDisplayed();
        } catch (Exception e) {
            // Alternative check
            String currentUrl = driver.getCurrentUrl();
            String pageTitleText = driver.getTitle();
            return currentUrl.contains("overview") || pageTitleText.contains("Accounts Overview");
        }
    }
    
    // Get account number - FIXED METHOD
    public String getAccountNumber() {
        try {
            // Method 1: Try to find account number from links
            if (!accountNumberLinks.isEmpty()) {
                String accountLinkText = accountNumberLinks.get(0).getText();
                // Extract numbers from text
                if (accountLinkText.matches(".*\\d+.*")) {
                    return accountLinkText.replaceAll("[^0-9]", "");
                }
                return accountLinkText;
            }
            
            // Method 2: Try account links
            if (!accountLinks.isEmpty()) {
                String href = accountLinks.get(0).getAttribute("href");
                if (href.contains("id=")) {
                    return href.split("id=")[1];
                }
                return accountLinks.get(0).getText();
            }
            
            // Method 3: Check page source for account number
            String pageSource = driver.getPageSource();
            if (pageSource.contains("Account #")) {
                int startIndex = pageSource.indexOf("Account #") + 9;
                int endIndex = pageSource.indexOf("<", startIndex);
                if (endIndex > startIndex) {
                    return pageSource.substring(startIndex, endIndex).trim();
                }
            }
            
            return "Account number not found";
            
        } catch (Exception e) {
            System.out.println("Error getting account number: " + e.getMessage());
            return "Error retrieving account number";
        }
    }
    
    // Get balance - FIXED METHOD
    public String getBalance() {
        try {
            // Method 1: Try balance cells
            if (!balanceCells.isEmpty()) {
                String balanceText = balanceCells.get(0).getText().trim();
                if (!balanceText.isEmpty() && !balanceText.equals("$0.00")) {
                    return balanceText;
                }
            }
            
            // Method 2: Try account details
            if (!accountDetails.isEmpty()) {
                for (WebElement detail : accountDetails) {
                    String text = detail.getText().trim();
                    if (text.contains("$") || text.matches(".*\\d+\\.\\d{2}.*")) {
                        return text;
                    }
                }
            }
            
            // Method 3: Check page for balance pattern
            String pageSource = driver.getPageSource();
            if (pageSource.contains("$")) {
                int dollarIndex = pageSource.indexOf("$");
                int endIndex = Math.min(dollarIndex + 20, pageSource.length());
                String balanceSection = pageSource.substring(dollarIndex, endIndex);
                // Extract the balance amount
                String[] parts = balanceSection.split(" ");
                if (parts.length > 0) {
                    return parts[0];
                }
            }
            
            return "Balance not found";
            
        } catch (Exception e) {
            System.out.println("Error getting balance: " + e.getMessage());
            return "Error retrieving balance";
        }
    }
    
    // Navigation Methods
    public void navigateToAccountsOverview() {
        try {
            accountsOverviewLink.click();
        } catch (Exception e) {
            driver.get("https://parabank.parasoft.com/parabank/overview.htm");
        }
    }
    
    public void clickFirstAccount() {
        try {
            if (!accountNumberLinks.isEmpty()) {
                accountNumberLinks.get(0).click();
            } else if (!accountLinks.isEmpty()) {
                accountLinks.get(0).click();
            }
        } catch (Exception e) {
            System.out.println("Could not click account: " + e.getMessage());
        }
    }
    
    public void clickLogout() {
        try {
            logoutLink.click();
        } catch (Exception e) {
            System.out.println("Could not click logout: " + e.getMessage());
        }
    }
    
    public boolean isLogoutSuccessful() {
        try {
            Thread.sleep(2000); // Wait for logout
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("index.htm") || 
                   currentUrl.contains("login") || 
                   driver.getTitle().contains("ParaBank | Welcome");
        } catch (Exception e) {
            return false;
        }
    }
    
    // Additional helper methods
    public int getNumberOfAccounts() {
        try {
            return Math.max(accountNumberLinks.size(), accountLinks.size());
        } catch (Exception e) {
            return 0;
        }
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    // Check if any accounts are displayed
    public boolean hasAccounts() {
        try {
            return !accountNumberLinks.isEmpty() || !accountLinks.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}