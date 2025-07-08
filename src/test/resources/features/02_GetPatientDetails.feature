@regression
Feature: Verify Patient details by Patient ID

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

Scenario Outline: Failed to load Patient details with invalid patient Id
  Given the GetLogin request is called "<partner>" with valid credentials
  When the GET GetPatientById patient details request is send with invalid patient Id
  Then the GetPatientById request send failed with 404 status code
Examples:
  | partner |
  | lombard |
  | bosley  |
  | nerivio |
  | pfizer  |

Scenario Outline: Failed to load Patient details with invalid authentication
  Given the GetLogin request is called "<partner>" with valid credentials
  When the GET GetPatientById patient details request is send with invalid authentication
  Then the GetPatientById request send failed with 401 status code
Examples:
  | partner |
  | lombard |
  | bosley  |
  | nerivio |
  | pfizer  |