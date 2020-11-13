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
        val mainScreen = MainScreen(getDriver())
        mainScreen.setBffUrl(SuiteSetup.getBffBaseUrl() + bffRelativeUrlPath)
        mainScreen.clickOnGoButton()
    }

    private fun getSearchByTextXpath(elementText: String?, likeSearch: Boolean, ignoreCase: Boolean): By {
        val xpath: By
        val property = if (SuiteSetup.isAndroid())
            "text"
        else
            "name"

        return AppiumUtil.getPropertyXpath(property, elementText, likeSearch, ignoreCase)
    }

    private fun getSearchByHintXpath(elementHint: String?, likeSearch: Boolean, ignoreCase: Boolean): By {
        return AppiumUtil.getPropertyXpath("hint", elementHint, likeSearch, ignoreCase)
    }

    protected fun waitForElementToBeClickable(
        elementText: String?,
        likeSearch: Boolean,
        ignoreCase: Boolean
    ): MobileElement {
        val xpath: By = getSearchByTextXpath(elementText, likeSearch, ignoreCase)
        return AppiumUtil.waitForElementToBeClickable(
            getDriver(),
            xpath,
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL,
            0
        )

    }

    protected fun waitForInvisibilityOfElementWithText(elementText: String?, likeSearch: Boolean, ignoreCase: Boolean) {
        val xpath: By = getSearchByTextXpath(elementText, likeSearch, ignoreCase)
        AppiumUtil.waitForInvisibilityOf(getDriver(), xpath, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL)
    }

    protected fun screenContainsElementWithText(
        elementText: String?,
        likeSearch: Boolean,
        ignoreCase: Boolean
    ): Boolean {
        return screenContainsElementWithText(elementText, likeSearch, ignoreCase, 2000)
    }

    protected fun screenContainsElementWithText(
        elementText: String?,
        likeSearch: Boolean,
        ignoreCase: Boolean,
        timeout: Long
    ): Boolean {
        val xpath: By = getSearchByTextXpath(elementText, likeSearch, ignoreCase)
        return AppiumUtil.elementExists(getDriver(), xpath, timeout)
    }

    protected fun clickOnElementWithText(elementText: String?, likeSearch: Boolean, ignoreCase: Boolean) {
        waitForElementToBeClickable(elementText, likeSearch, ignoreCase).click()

    }

    protected fun clickOnElementWithHint(hintText: String?, likeSearch: Boolean, ignoreCase: Boolean) {
        AppiumUtil.waitForElementToBeClickable(
            getDriver(),
            getSearchByHintXpath(hintText, likeSearch, ignoreCase),
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL,
            0
        ).click()

    }

    protected fun scrollToElementWithText(
        elementText: String?,
        likeSearch: Boolean,
        ignoreCase: Boolean
    ): MobileElement {
        val xpath: By = getSearchByTextXpath(elementText, likeSearch, ignoreCase)
        val element: MobileElement = AppiumUtil.waitForPresenceOfElement(
            getDriver(),
            xpath,
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL
        )

        // TODO: check if the below code works for both iOS and Android
        val action = TouchActions(getDriver())
        action.scroll(element, 10, 100)
        action.perform()

        return element
    }

    protected fun hideKeyboard() {
        getDriver()?.hideKeyboard()
    }

    /**
     * Gets elements by text and returns true if element1 is above element2
     */
    protected fun isElementAbove(
        elementText1: String?,
        elementText2: String?,
        likeSearch: Boolean,
        ignoreCase: Boolean
    ): Boolean {
        val element1: MobileElement = waitForElementToBeClickable(elementText1, likeSearch, ignoreCase)
        val element2: MobileElement = waitForElementToBeClickable(elementText2, likeSearch, ignoreCase)
        return AppiumUtil.isElementAboveElement(element1, element2)
    }

    protected fun sleep(milliseconds: Long) {
        try {
            Thread.sleep(milliseconds)
        } catch (e: Exception) {
        }
    }

    // for experimentation purposes
    protected fun printElementLocationAndSize(string1: String?, string2: String?, string3: String?) {
        val element1: MobileElement = AppiumUtil.waitForElementToBeClickable(
            getDriver(),
            By.xpath("//*[contains(@text,'$string1')]"),
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL,
            0
        )

        val element2: MobileElement = AppiumUtil.waitForElementToBeClickable(
            getDriver(),
            By.xpath("//*[contains(@text,'$string2')]"),
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL,
            0
        )

        val element3: MobileElement = AppiumUtil.waitForElementToBeClickable(
            getDriver(),
            By.xpath("//*[contains(@text,'$string3')]"),
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL,
            0
        )

        println("Element1 '$string1': location[${element1.location}] | size[${element1.size}]")
        println("Element1 '$string2': location[${element2.location}] | size[${element2.size}]")
        println("Element1 '$string3': location[${element3.location}] | size[${element3.size}]")
    }


}