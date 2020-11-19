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

private const val TABVIEW_SCREEN_HEADER = "Beagle Tab View"
private const val TAB_1 = "TAB 1"
private const val TAB_1_TEXT = "Welcome to Tab 1"
private const val TAB_1_TEXT_2 = "This is Tab1's second text"
private const val TAB_2 = "TAB 2"
private const val TAB_2_TEXT = "Welcome to Tab 2"
private const val TAB_2_TEXT_2 = "This is Tab2's second text"
private const val TAB_3 = "TAB 3"
private const val TAB_3_TEXT = "Welcome to Tab 3"
private const val TAB_3_TEXT_2 = "This is Tab3's second text"
private const val TAB_4 = "TAB 4"
private const val TAB_4_TEXT = "Welcome to Tab 4"
private const val TAB_4_TEXT_2 = "This is Tab4's second text"

class TabViewScreenSteps : AbstractStep() {
    override var bffRelativeUrlPath = "/tabview"

    @Before("@tabview")
    fun setup() {
        loadBffScreenFromMainScreen()
    }

    @Given("^that I'm on the tabview screen$")
    fun checkTabViewScreen() {
        // ScreenRobot().checkViewContainsText(TABVIEW_SCREEN_HEADER, true)
        waitForElementWithTextToBeClickable(TABVIEW_SCREEN_HEADER, false, false)
    }

    @Then("^my tabview components should render their respective tabs attributes correctly$")
    fun checkTabViewRendersTabs() {
        /*
        ScreenRobot()
            .checkViewContainsText(TAB_1)
            .checkViewContainsText(TAB_1_TEXT)
            .checkViewContainsText(TAB_1_TEXT_2)
            .swipeLeftOnView()

            .checkViewContainsText(TAB_2)
            .checkViewContainsText(TAB_2_TEXT)
            .checkViewContainsText(TAB_2_TEXT_2)
            .swipeLeftOnView()

            .checkViewContainsText(TAB_3)
            .checkViewContainsText(TAB_3_TEXT)
            .checkViewContainsText(TAB_3_TEXT_2)
            .swipeLeftOnView()

            .checkViewContainsText(TAB_4)
            .checkViewContainsText(TAB_4_TEXT)
            .checkViewContainsText(TAB_4_TEXT_2)

            .swipeRightOnView()
            .swipeRightOnView()
            .swipeRightOnView()

         */

        waitForElementWithTextToBeClickable(TAB_1, false, false)
        waitForElementWithTextToBeClickable(TAB_1_TEXT, false, false)
        waitForElementWithTextToBeClickable(TAB_1_TEXT_2, false, false)
        swipeLeft()

        waitForElementWithTextToBeClickable(TAB_2, false, false)
        waitForElementWithTextToBeClickable(TAB_2_TEXT, false, false)
        waitForElementWithTextToBeClickable(TAB_2_TEXT_2, false, false)
        swipeLeft()

        waitForElementWithTextToBeClickable(TAB_3, false, false)
        waitForElementWithTextToBeClickable(TAB_3_TEXT, false, false)
        waitForElementWithTextToBeClickable(TAB_3_TEXT_2, false, false)
        swipeLeft()

        waitForElementWithTextToBeClickable(TAB_4, false, false)
        waitForElementWithTextToBeClickable(TAB_4_TEXT, false, false)
        waitForElementWithTextToBeClickable(TAB_4_TEXT_2, false, false)
        swipeLeft()
    }

    @Then("^my tab should render the text (.*) and (.*) correctly$")
    fun renderTextCorrectly(string1: String, string2: String) {
        /*
        ScreenRobot()
            .checkViewContainsText(string1)
            .checkViewContainsText(string2)

         */
        waitForElementWithTextToBeClickable(string1, false, false)
        waitForElementWithTextToBeClickable(string2, false, false)
    }
}