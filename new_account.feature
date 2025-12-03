Feature: Open New Account
  As a Parabank customer
  I want to open new accounts
  So that I can organize my finances better

  Background:
    Given I am logged in to Parabank as "john" with password "demo"

  @SmokeTest @NewAccount
  Scenario: Open a new checking account
    When I navigate to Open New Account page
    And I select account type "CHECKING"
    And I select from account "12345"
    And I click Open New Account button
    Then I should see account opened successfully
    And I should see new account number

  @RegressionTest @NewAccount
  Scenario: Open a new savings account
    When I navigate to Open New Account page
    And I select account type "SAVINGS"
    And I select from account "12345"
    And I click Open New Account button
    Then I should see account opened successfully
    And I should see account type "SAVINGS"

  @DataDriven @NewAccount
  Scenario Outline: Open different account types
    When I open new <accountType> account from account "<fromAccount>"
    Then new account should be opened

    Examples:
      | accountType | fromAccount |
      | CHECKING    | 12345       |
      | SAVINGS     | 12345       |
      | CHECKING    | 67890       |