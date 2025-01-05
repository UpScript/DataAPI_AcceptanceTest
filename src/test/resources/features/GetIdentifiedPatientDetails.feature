Feature: Verify Identified Patient details

Scenario Outline: Successfully Verify Identified Patient details
  Given the GetLogin request is called "<partner>" with valid credentials
  And the GET all patient details request is send
  When the GET identified patient details request is send
  Then the identified request send successfully with 200 status code
  And the identified patient details should be match with deIdentified table data
Examples:
  | partner |
  | lombard |
  | bosley  |
  | nerivio |
