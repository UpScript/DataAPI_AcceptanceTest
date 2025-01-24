
Feature: Verify Patient details

Scenario Outline: Successfully Verify Patient details
  Given the GetLogin request is called "<partner>" with valid credentials
  And the GET all patient details request is send
  When the GET GetPatientById patient details request is send
  Then the GetPatientById request send successfully with 200 status code
  And the patient details should be match with patient table data
Examples:
  | partner |
  | lombard |
  | bosley  |
  | nerivio |
  | pfizer  |