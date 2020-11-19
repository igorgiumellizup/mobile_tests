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

import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class SendRequestScreenSteps : AbstractStep() {
    override var bffRelativeUrlPath = "/send-request"

    @Before("@sendrequest")
    fun setup() {
        loadBffScreenFromMainScreen()
    }

    @Given("^the Beagle application did launch with the send request screen url$")
    fun checkTitleScreen() {
        // ScreenRobot().checkViewContainsText("Send Request Screen", true)
        waitForElementWithTextToBeClickable("Send Request Screen", false, false)
    }

    @When("^I press the (.*) button$")
    fun clickOnButtonSendRequestSuccess(string: String) {
        // ScreenRobot().clickOnText(string).sleep(2)
        waitForElementWithTextToBeClickable(string, false, false).click()
    }

    @Then("^the screen should show some alert with (.*) title$")
    fun verifyAlertTitle(string: String) {
        // ScreenRobot().checkViewContainsText(string, true).sleep(2)
        waitForElementWithTextToBeClickable(string, false, false)
    }

    @When("^I click on sendRequestError button (.*)")
    fun clickOnButtonSendRequestError(string: String) {
        // ScreenRobot().clickOnText(string).sleep(2)
        waitForElementWithTextToBeClickable(string, false, false).click()
    }

    @Then("^the pressed button changes it's (.*) title to didFinish$")
    fun verifyChangeTitle(string: String) {
        // ScreenRobot().checkViewDoesNotContainsText(string).sleep(2)
        waitForInvisibilityOfElementWithText(string, false, false)
    }
}