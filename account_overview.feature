Feature: Account Overview
  As a logged in user
  I want to view my account overview
  So that I can check my balance and transactions

  Background:
    Given I am logged into Parabank with username "john" and password "demo"

  @SmokeTest @Account
  Scenario: View account summary
    When I navigate to accounts overview page
    Then I should see my account number
    And I should see my account balance
    And I should see transaction history

  @RegressionTest @Account
  Scenario: Check account navigation links
    Then I should see following navigation links:
      | Open New Account     |
      | Transfer Funds      |
      | Bill Pay            |
      | Find Transactions   |
      | Update Contact Info |
      | Request Loan        |