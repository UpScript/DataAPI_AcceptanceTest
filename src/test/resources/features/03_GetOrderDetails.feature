@regression
Feature: Verify Order details by Order Id

Scenario Outline: Successfully verify the Order details with valid data
  Given the GetLogin request is called "<partner>" with valid credentials
  And the GET all order details request is send
  When the GET GetOrderById order details request is send
  Then the GetOrderById request send successfully with 200 status code
  And the order details should be match with order table data
Examples:
  | partner |
  | lombard |
  | bosley  |
  | nerivio |
  | pfizer  |

Scenario Outline: Failed verify the Order details with invalid Order Id
  Given the GetLogin request is called "<partner>" with valid credentials
  And the GET all order details request is send
  When the GET GetOrderById order details request is send with invalid order Id
  Then the GetOrderById request send successfully with 400 status code
Examples:
  | partner |
  | lombard |
  | bosley  |
  | nerivio |
  | pfizer  |

Scenario Outline: Failed verify the Order details with invalid authentication credentials
  Given the GetLogin request is called "<partner>" with valid credentials
  And the GET all order details request is send
  When the GET GetOrderById order details request is send with invalid credentials
  Then the GetOrderById request send successfully with 401 status code
Examples:
  | partner |
  | lombard |
  | bosley  |
  | nerivio |
  | pfizer  |

