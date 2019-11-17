package service;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Stepdefs {
    private Bewertung bewertungService = new Bewertung();

    @Given("I'm a customer")
    public void i_m_a_customer() {
        bewertungService.getCustomerKey();


    }

    @When("I request {string} list")
    public void i_request_feature_list(String feature) {
        bewertungService.getRequest(feature, "GET");
    }

    @Then("The request is successful")
    public void the_request_is_successfull() {
        bewertungService.checkStatus();
    }

    @Then("Response is valid")
    public void responce_is_valid() {

        bewertungService.checkJsonByScheme();

    }

    @Then("I get {string} list")
    public void i_get_feature_list(String feature) {
        bewertungService.getDataListFromResponse(feature);
    }

    @Given("I choose {string}")
    public void i_choose(String feature) {

        bewertungService.selectServiceParameter(feature, true);
    }


    @Given("I'm a hacker")
    public void iMAHacker() {
        //today - no preconditions
    }

    @Then("The request get {string} response")
    public void theRequestGetResponse(String arg0) {
        bewertungService.checkStatus(arg0);
    }


    @When("I make {string} request to {string} list")
    public void iMakeRequestToList(String requestType, String feature) {

        bewertungService.getRequest(feature, requestType);
    }

    @And("I have some locale")
    public void iHaveSomeLocale() {
        bewertungService.getLocale();
    }

    @And("I have incorrect locale")
    public void iHaveIncorrectLocale() {
        bewertungService.setIncorrectLocale();
    }

    @And("The messages in response is {string}")
    public void theMessagesInResponceIs(String messages) {
        bewertungService.checkResponseMessage(messages);
    }

    @And("I choose incorrect {string}")
    public void iChooseIncorrect(String feature) {
        bewertungService.selectServiceParameter(feature, true);
    }

    @And("Response contains empty wkda")
    public void responseContainsEmptyWkda() {
        bewertungService.checkWKDAResponceIsEmpty();
    }
}
