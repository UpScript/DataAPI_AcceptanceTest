Feature: Verify Patient details

Background:
  Given the login request for pfizer is send
  And the GET all patient details request is send

Scenario: Successfully retrieve the patient details
  When the GET patient details request is send
  Then the request send successfully with 200 status code
#  And the patient details schema validated successfully
  And the patient details should be returned
#  And the "<patient_dob>" retrieved successfully
#Examples:
#  | patient_dob |
#  | 2000        |