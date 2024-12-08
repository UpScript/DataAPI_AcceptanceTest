Feature: Verify Login GET Call

Scenario Outline: Successfully Login with valid credentials
  Given the GetLogin request set with valid details for "<vendor>"
  When the GetLogin request is called
  Then the request send successfully with 200 status code
  And the GetLogin should return valid JWT token
Examples:
  | vendor  |
  | pfizer  |
  | lombard |
  | bosley  |
  | nerivio |
