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

import br.com.zup.beagle.cucumber.steps.constants.IMAGE_SCREEN_HEADER
import br.com.zup.beagle.cucumber.steps.constants.IMAGE_TEXT_1
import br.com.zup.beagle.cucumber.steps.constants.IMAGE_TEXT_2
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then

class ImageScreenSteps: AbstractStep() {
    override var bffRelativeUrlPath = "/image"

    @Before("@image")
    fun setup() {
        // TestUtils.startActivity(activityTestRule, CONDITIONAL_SCREEN_BFF_URL)
        loadBffScreenFromMainScreen()
    }

    @Given("^that I'm on the image screen$")
    fun checkImageScreen() {
        //ScreenRobot().checkViewContainsText(IMAGE_SCREEN_HEADER, true)
        waitForElementToBeClickable(IMAGE_SCREEN_HEADER, false, false)
    }

    @Then("^image screen should render all image attributes correctly$")
    fun checkImageScreenTexts() {
        /*
        ScreenRobot()
            .checkViewContainsText(IMAGE_TEXT_1)
            .checkViewContainsText(IMAGE_TEXT_2)
            .scrollViewDown()
        */
        waitForElementToBeClickable(IMAGE_TEXT_1, false, false)
        waitForElementToBeClickable(IMAGE_TEXT_2, false, false)

    }
}