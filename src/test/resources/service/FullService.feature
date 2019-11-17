Feature: How the services work together

  Background:
    Given I'm a customer
    And I have some locale

  Scenario: Car Makers List
    When I request "Makers" list
    Then The request is successful
    And Response is valid
    And I get "Makers" list


  Scenario: Car Models List
    Given I choose "Maker"
    When I request "Models" list
    Then The request is successful
    And Response is valid
    And I get "Models" list


  Scenario: Car Build Date List
    Given I choose "Maker"
    And I choose "Model"
    When I request "Build Date" list
    Then The request is successful
    And Response is valid


