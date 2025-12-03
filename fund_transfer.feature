Feature: Transfer Funds Functionality
  As a Parabank customer
  I want to transfer funds between my accounts
  So that I can manage my money efficiently

  Background:
    Given I am logged in to Parabank as "john" with password "demo"
    And I have at least two accounts

  @SmokeTest @Transfer
  Scenario: Transfer funds between accounts
    When I navigate to Transfer Funds page
    And I select from account "12345"
    And I select to account "67890"
    And I enter amount "50"
    And I click Transfer button
    Then I should see transfer success message
    And I should see amount "50.00" transferred

  @RegressionTest @Transfer
  Scenario: Attempt transfer with insufficient funds
    When I navigate to Transfer Funds page
    And I select from account "12345"
    And I select to account "67890"
    And I enter amount "10000"
    And I click Transfer button
    Then I should see insufficient funds error

  @DataDriven @Transfer
  Scenario Outline: Transfer different amounts
    When I transfer $<amount> from checking to savings
    Then transfer should be <result>

    Examples:
      | amount | result   |
      | 10     | successful |
      | 100    | successful |
      | 1000   | successful |
      | 10000  | fail     |