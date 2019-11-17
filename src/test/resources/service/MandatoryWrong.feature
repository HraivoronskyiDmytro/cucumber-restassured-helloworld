Feature: Wrong Mandatory Parameters

  Background:
    Given I'm a customer
    And I have some locale
    And I request "Makers" list
    And I get "Makers" list


  Scenario: Try to request Build Date without wrong mandatory parameters
    Given I choose incorrect "Maker"
    And I choose incorrect "Model"
    When I request "Build Date" list
    Then The request is successful
    And Response contains empty wkda


  Scenario: Try to request Build Date without wrong mandatory parameters
    Given I choose "Maker"
    And I choose incorrect "Model"
    When I request "Build Date" list
    Then The request is successful
    And Response contains empty wkda

  Scenario: Try to request Models without wrong mandatory parameters
    Given I choose incorrect "Maker"
    When I request "Models" list
    Then The request is successful
    And Response contains empty wkda

