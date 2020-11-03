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

import br.com.zup.beagle.screens.MainScreen
import br.com.zup.beagle.setup.DEFAULT_ELEMENT_WAIT_TIME_IN_MILL
import br.com.zup.beagle.setup.SuiteSetup
import br.com.zup.beagle.utils.AppiumUtil
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.interactions.touch.TouchActions


abstract class AbstractStep {

    abstract var bffRelativeUrlPath: String

    private var driver: AppiumDriver<*>? = null

    protected fun getDriver(): AppiumDriver<*>? {
        if (driver == null) {
            driver = SuiteSetup.getDriver()
        }
        return driver
    }

    protected fun loadBffScreenFromMainScreen() {
        var mainScreen: MainScreen = MainScreen(getDriver())
        mainScreen.setBffUrl(SuiteSetup.getBffBaseUrl() + bffRelativeUrlPath)
        mainScreen.clickOnGoButton()
    }

    fun checkScreenContainsElementWithText(text: String?): Boolean {
        return AppiumUtil.elementExists(getDriver(), By.xpath("//*[contains(@text,'$text')]"), 1000)
    }

    fun clickOnElementWithText(text: String?) {
        AppiumUtil.waitForElementToBeClickable(
            getDriver(),
            By.xpath("//*[contains(@text,'$text')]"),
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL,
            0
        ).click()

    }

    fun clickOnElementWithHint(hintText: String?) {
        AppiumUtil.waitForElementToBeClickable(
            getDriver(),
            By.xpath("//*[contains(@hint,'$hintText')]"),
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL,
            0
        ).click()

    }

    fun scrollToElementWithText(text: String?): MobileElement {
        var element: MobileElement = AppiumUtil.waitForPresenceOfElement(
            getDriver(),
            By.xpath("//*[contains(@text,'$text')]"),
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL
        )

        // TODO: check if the below code works for both iOS and Android
        val action = TouchActions(driver)
        action.scroll(element, 10, 100)
        action.perform()

        return element;
    }

    fun hideKeyboard() {
        getDriver()?.hideKeyboard()
    }


}