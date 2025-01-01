Feature: Verify DeIdentified Patient details

Background:
  Given the GetLogin request is called "pfizer" with valid credentials
@dev
Scenario: Successfully verify the DeIdentified Patient details with valid data
  Given the GET all patient details request is send
  When the GET deIdentified patient details request is send
  Then the deIdentified request send successfully with 200 status code
  And the deIdentified patient details should be match with deIdentified table data
