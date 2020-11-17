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

package br.com.zup.beagle.screens

import br.com.zup.beagle.setup.DEFAULT_ELEMENT_WAIT_TIME_IN_MILL
import br.com.zup.beagle.setup.DEFAULT_SCREEN_WAIT_TIME_IN_MILL
import br.com.zup.beagle.utils.AppiumUtil
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import org.openqa.selenium.By
import org.openqa.selenium.support.PageFactory
import java.time.Duration


abstract class AbstractScreen(mobileDriver: AppiumDriver<*>?) {

    protected var driver: AppiumDriver<*>? = mobileDriver

    init {
        PageFactory.initElements(
            AppiumFieldDecorator(
                driver,
                Duration.ofMillis(DEFAULT_ELEMENT_WAIT_TIME_IN_MILL)
            ), this
        )
        waitForScreenToLoad<AbstractScreen>()
    }

    /**
     * Implement here the conditions to guarantee a screen is loaded, eg. wait for the visibility of key screen elements
     * @param <T>
     * @return
    </T> */
    abstract fun <T : AbstractScreen> waitForScreenToLoad(): T

    /**
     * @param elementArray
     */
    protected open fun waitForScreenToLoad(elementArray: Array<MobileElement>) {
        AppiumUtil.waitForElementsToBeClickable(
            driver!!,
            elementArray,
            DEFAULT_SCREEN_WAIT_TIME_IN_MILL
        )
    }

    /**
     * @param elementArray
     */
    protected open fun waitForScreenToLoad(elementArray: Array<By>) {
        AppiumUtil.waitForElementsToBeClickable(
            driver!!,
            elementArray,
            DEFAULT_SCREEN_WAIT_TIME_IN_MILL
        )
    }


    protected fun waitForElementToBeClickable(element: MobileElement): MobileElement {
        return AppiumUtil.waitForElementToBeClickable(
            driver!!,
            element,
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL
        )
    }

    protected fun waitForElementToBeClickable(locator: By): MobileElement {
        return AppiumUtil.waitForElementToBeClickable(
            driver!!,
            locator,
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL,
            0
        )
    }

    protected fun waitForElementToBeClickable(
        parent: MobileElement,
        childLocator: By
    ): MobileElement {
        return AppiumUtil.waitForElementToBeClickable(
            driver!!,
            parent,
            childLocator,
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL
        )
    }

    protected fun waitForInvisibilityOf(locator: By) {
        AppiumUtil.waitForInvisibilityOf(driver!!, locator, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL)
    }

    protected fun waitForInvisibilityOf(element: MobileElement) {
        AppiumUtil.waitForInvisibilityOf(driver!!, element, DEFAULT_ELEMENT_WAIT_TIME_IN_MILL)
    }


    /**
     * Ensure a value is completely set to an element
     * @param element
     * @param value
     */
    protected fun setElementValue(element: MobileElement, value: String) {
        clearElementValue(waitForElementToBeClickable(element))
        element.sendKeys(value)
    }

    protected fun setElementValue(locator: By, value: String) {
        setElementValue(waitForElementToBeClickable(locator), value)
    }

    protected fun clearElementValue(element: MobileElement) {
        element.clear()
        element.sendKeys("")
        AppiumUtil.waitForElementTextToBe(driver!!, element, "", DEFAULT_ELEMENT_WAIT_TIME_IN_MILL)
        AppiumUtil.waitForElementAttributeToBe(
            driver!!,
            element,
            "value",
            "",
            DEFAULT_ELEMENT_WAIT_TIME_IN_MILL
        )
    }
}