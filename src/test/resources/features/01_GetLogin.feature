Feature: Verify Login GET Request for all the partners

Scenario Outline: Successfully Login with valid credentials
  When the GetLogin request is called "<partner>" with valid credentials
  Then the login request send successfully with 200 status code
Examples:
  | partner |
  | pfizer  |
  | lombard |
  | bosley  |
  | nerivio |
