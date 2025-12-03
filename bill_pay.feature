Feature: Bill Payment Functionality
  As a Parabank customer
  I want to pay my bills online
  So that I can manage my finances conveniently

  Background:
    Given I am logged in to Parabank as "john" with password "demo"

  @SmokeTest @BillPay
  Scenario: Pay bill to a new payee
    When I navigate to Bill Pay page
    And I enter payee details:
      | Field         | Value           |
      | Payee Name    | John Doe        |
      | Address       | 123 Main St     |
      | City          | New York        |
      | State         | NY              |
      | Zip Code      | 10001           |
      | Phone         | 1234567890      |
    And I enter account number "12345"
    And I enter amount "100"
    When I click Send Payment
    Then I should see payment confirmation
    And I should see payee name "John Doe"

  @RegressionTest @BillPay
  Scenario Outline: Pay bill with different amounts
    When I navigate to Bill Pay page
    And I pay bill to "Test Payee" with account "<account>" amount "<amount>"
    Then payment should be <result>

    Examples:
      | account | amount | result   |
      | 12345   | 50     | successful |
      | 12345   | 0      | fail     |
      | invalid | 100    | fail     |

  @DataDriven @BillPay
  Scenario: Verify bill payment validations
    When I navigate to Bill Pay page
    And I click Send Payment without entering details
    Then I should see validation errors