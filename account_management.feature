Feature: Account Management
  As a Parabank user
  I want to manage my account
  So that I can perform banking operations
  
  Background:
    Given I am logged in to Parabank
    
  @AccountTest
  Scenario: View account balance
    When I navigate to accounts page
    Then I should see my account balance
    
  @AccountTest  
  Scenario: View account details
    When I click on account number
    Then I should see account transaction history
    
  @AccountTest
  Scenario: Logout from application
    When I click logout button
    Then I should be redirected to login page