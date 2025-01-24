
Feature: Verify Product details by Product Id

Scenario Outline: Successfully verify the Product details with valid data
  Given the GetLogin request is called "<partner>" with valid credentials
  And the GET all product details request is send
  When the GET GetProductById product details request is send
  Then the GetProductById request send successfully with 200 status code
  And the product details should be match with product table data
Examples:
  | partner |
  | lombard |
  | bosley  |
  | nerivio |
  | pfizer  |