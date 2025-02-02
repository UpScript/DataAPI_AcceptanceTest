@regression
Feature: Verify Login GET Request for all the partners
@dev
Scenario Outline: Successfully Login with valid credentials
  When the GetLogin request is called "<partner>" with valid credentials
  Then the login request send successfully with 200 status code
Examples:
  | partner |
  | pfizer  |
  | lombard |
  | bosley  |
  | nerivio |

Scenario Outline: Failed to Login with invalid credentials
  When the GetLogin request is called "<partner>" with invalid credentials
  Then the login request send failed with 401 status code
Examples:
  | partner |
  | pfizer  |
  | lombard |
  | bosley  |
  | nerivio |

Scenario Outline: Failed to Login with empty credentials
  When the GetLogin request is called "<partner>" with empty credentials
  Then the login request send failed with 401 status code
Examples:
  | partner |
  | pfizer  |
  | lombard |
  | bosley  |
  | nerivio |
