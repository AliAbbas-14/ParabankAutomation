Feature: Parabank Login Tests

  @SmokeTest
  Scenario: Valid login test
    Given I am on the Parabank login page
    When I enter username "john" and password "demo"
    And I click the login button
    Then I should see account overview page

  @RegressionTest
  Scenario: Invalid login test
    Given I am on the Parabank login page
    When I enter username "wrong" and password "wrong"
    And I click the login button
    Then I should see error message

  @DataDriven @Excel
  Scenario Outline: Login with data from Excel
    Given I am on the Parabank login page
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see "<expected_result>"
    
    Examples:
      | username | password | expected_result |
      | john     | demo     | SUCCESS         |
      | mary     | demo     | SUCCESS         |
      | invalid  | invalid  | ERROR           |