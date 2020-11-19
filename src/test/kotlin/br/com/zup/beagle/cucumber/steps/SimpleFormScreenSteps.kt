/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.zup.beagle.cucumber.steps

import io.appium.java_client.MobileElement
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class SimpleFormScreenSteps : AbstractStep() {
    override var bffRelativeUrlPath = "/simpleform"

    @Before("@simpleform")
    fun setup() {
        loadBffScreenFromMainScreen()
    }

    @Given("^that I'm on the simple form screen$")
    fun checkBaseScreen() {
        // ScreenRobot().checkViewContainsText("SimpleForm", true)
        waitForElementWithTextToBeClickable("SimpleForm", false, false)
    }

    @Then("^checks that the textInput with the place holder (.*) is on the screen$")
    fun checkTextInputEmailInSimpleForm(field: String) {
        // ScreenRobot().checkViewContainsHint(field, true)
        waitForElementWithTextToBeClickable(field, false, false)
    }

    @Then("^checks that the button with the title (.*) is on the screen$")
    fun checkButtonOnScreen(textButton: String) {
        // ScreenRobot().checkViewContainsText(textButton)
        waitForElementWithTextToBeClickable(textButton, false, false)
    }

    @When("^I click on textInput for email with (.*) and insert my (.*)$")
    fun insertEmailInTextInput(hint: String, email: String) {
        /*
        ScreenRobot()
            .checkViewContainsHint(hint)
            .clickOnInputWithHint(hint)
            .typeText(hint, email)
            .hideKeyboard()
            */
        var element: MobileElement = waitForElementWithTextToBeClickable(hint, false, false)
        element.click()
        element.sendKeys(email)
        hideKeyboard()

    }

    @When("^I click on textInput for password with (.*) and insert my (.*)$")
    fun insertPasswordInTextInput(hint: String, password: String) {
        /*ScreenRobot()
            .checkViewContainsHint(hint)
            .clickOnInputWithHint(hint)
            .typeText(hint, password)
            .hideKeyboard()*/
        var element: MobileElement = waitForElementWithTextToBeClickable(hint, false, false)
        element.click()
        element.sendKeys(password)
        hideKeyboard()
    }

    @When("^I click to (.*)$")
    fun sendDataFromTextInputs(submit: String) {
        /*ScreenRobot()
            .checkViewContainsText(submit)
            .clickOnText(submit)*/
        waitForElementWithTextToBeClickable(submit, false, false).click()
    }

    @Then("^verify if (.*) appears correctly$")
    fun checkIfTextInputDataIsEqualAlert(alertData: String) {
        /*ScreenRobot()
            .checkViewContainsText("Registered data", true)
            .checkViewContainsText(alertData)*/
        waitForElementWithTextToBeClickable(alertData, false, false)
        waitForElementWithTextToBeClickable("Registered data", false, false)

    }
}