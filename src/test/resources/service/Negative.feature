Feature: Negative Testing

  Background:
    Given I'm a hacker

  Scenario Outline: Access service without wa_key
    When I request "<service>" list
    Then The request get "401" response
    Examples:
      | service    |
      | Makers     |
      | Models     |
      | Build Date |

  Scenario Outline: Try POST\PUT request
    Given I'm a customer
    And I have some locale
    When I make "<Method>" request to "<service>" list
    Then The request get "<StatusCode>" response
    Examples:
      | service    | Method | StatusCode |
      | Makers     | POST   | 401        |
      | Models     | POST   | 401        |
      | Build Date | POST   | 401        |
      | Makers     | PUT    | 403        |
      | Models     | PUT    | 403        |
      | Build Date | PUT    | 403        |

  Scenario Outline: Make request without mandatory parameters
    Given I'm a customer
    And I have some locale
    When I request "<service>" list
    Then The request get "400" response
    And The messages in response is "Required String parameter 'manufacturer' is not present"
    Examples:
      | service    |
      | Models     |
      | Build Date |

  Scenario: Try to request Car Makers list with incorrect locale
    Given I'm a customer
    And I have incorrect locale
    When I request "Makers" list
    Then The request get "500" response





