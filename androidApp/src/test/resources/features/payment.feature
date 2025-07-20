Feature: Payment Processing
  As a user
  I want to make payments
  So that I can send money to others

  Scenario: Successful payment with valid details
    Given a user enters valid payment details
    When they submit the payment
    Then the payment should be processed successfully

  Scenario: Payment fails with invalid details
    Given a user enters invalid payment details
    When they submit the payment
    Then validation errors should be shown