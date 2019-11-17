Feature: Build Date Service Negative test

  Background:
    Given I'm a customer
    And I have some locale
    And I request "Makers" list
    And I get "Makers" list


  Scenario: Try to Build Date without mandatory parameters
    Given I choose "Maker"
    When I request "Build Date" list
    Then The request get "400" response
    And The messages in response is "Required String parameter 'main-type' is not present"





