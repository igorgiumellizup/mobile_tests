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

package br.com.zup.beagle.utils

import io.appium.java_client.MobileDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.functions.ExpectedCondition
import org.openqa.selenium.*

import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import java.time.Duration


object AppiumUtil {

    @Synchronized
    fun waitForPresenceOfElement(driver: MobileDriver<*>?, locator: By, timeoutInMilliseconds: Long): MobileElement {
        val wait: FluentWait<MobileDriver<*>> = FluentWait<MobileDriver<*>>(driver)
        wait.pollingEvery(Duration.ofMillis(500))
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        wait.ignoring(NoSuchElementException::class.java)
        wait.ignoring(StaleElementReferenceException::class.java)
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator)) as MobileElement
    }

    @Synchronized
    fun waitForElementToBeClickable(
        driver: MobileDriver<*>?,
        parent: MobileElement,
        childLocator: By,
        timeoutInMilliseconds: Long
    ): MobileElement {
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        wait.ignoring(NoSuchElementException::class.java)
        wait.ignoring(StaleElementReferenceException::class.java)
        wait.ignoring(ElementNotInteractableException::class.java)
        wait.ignoring(ElementNotVisibleException::class.java)
        return wait.until { parent.findElement(childLocator) } as MobileElement
    }

    @Synchronized
    fun waitForElementToBeClickable(
        driver: MobileDriver<*>?,
        locator: By,
        timeoutInMilliseconds: Long,
        intervalInMilliseconds: Long
    ): MobileElement {
        val wait = FluentWait(driver)
        if (intervalInMilliseconds > 0) wait.pollingEvery(Duration.ofMillis(intervalInMilliseconds))
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        wait.ignoring(NoSuchElementException::class.java)
        wait.ignoring(StaleElementReferenceException::class.java)
        wait.ignoring(ElementNotInteractableException::class.java)
        wait.ignoring(ElementNotVisibleException::class.java)
        return wait.until(ExpectedConditions.elementToBeClickable(locator)) as MobileElement // clickable = verifies enabled e visibility
    }

    @Synchronized
    fun waitForElementToBeClickable(
        driver: MobileDriver<*>?,
        element: MobileElement,
        timeoutInMilliseconds: Long
    ): MobileElement {
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        wait.ignoring(NoSuchElementException::class.java)
        wait.ignoring(StaleElementReferenceException::class.java)
        wait.ignoring(ElementNotInteractableException::class.java)
        wait.ignoring(ElementNotVisibleException::class.java)
        return wait.until(ExpectedConditions.elementToBeClickable(element)) as MobileElement
    }

    @Synchronized
    fun waitForElementsToBeClickable(
        driver: MobileDriver<*>?,
        elements: Array<MobileElement>,
        timeoutInMilliseconds: Long
    ) {
        for (element in elements) {
            waitForElementToBeClickable(driver, element, timeoutInMilliseconds)
        }
    }

    @Synchronized
    fun waitForElementsToBeClickable(
        driver: MobileDriver<*>?,
        locators: Array<By>,
        timeoutInMilliseconds: Long
    ) {
        for (locator in locators) {
            waitForElementToBeClickable(driver, locator, timeoutInMilliseconds, 400)
        }
    }


    @Synchronized
    fun waitForElementTextToBe(
        driver: MobileDriver<*>?, element: MobileElement, text: String,
        timeoutInMilliseconds: Long
    ): Boolean {
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        return wait.until {
            return@until element.text == text
        }
    }

    @Synchronized
    fun waitForElementAttributeToBe(
        driver: MobileDriver<*>?, element: MobileElement, attribute: String, value: String,
        timeoutInMilliseconds: Long
    ): Boolean {
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        return wait.until {
            element.getAttribute(attribute) != null && element.getAttribute(attribute) == value
        }
    }


    @Synchronized
    fun waitForInvisibilityOf(driver: MobileDriver<*>?, locator: By, timeoutInMilliseconds: Long): Boolean {
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        wait.pollingEvery(Duration.ofMillis(500))
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator))
    }

    @Synchronized
    fun waitForInvisibilityOf(
        driver: MobileDriver<*>?,
        element: MobileElement,
        timeoutInMilliseconds: Long
    ): Boolean {
        val list = mutableListOf<MobileElement>()
        list.add(element)
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        wait.pollingEvery(Duration.ofMillis(500))
        return wait.until(ExpectedConditions.invisibilityOfAllElements(list as List<WebElement>))

    }

    /**
     *
     * @param tableElement
     * @param value
     * @return line elements (tr) that contains the given value (@param value) in its children's text() attribute
     */
    @Synchronized
    fun getTableLineElementsThatContainsText(
        tableElement: MobileElement,
        value: String
    ): List<MobileElement> {
        val results: List<MobileElement>
        val xpath = ".//tr[.//*[contains(text(),'$value')]]"
        results = tableElement.findElements(By.xpath(xpath))
        return results
    }

    @Synchronized
    fun elementExists(driver: MobileDriver<*>?, locator: By, timeoutInMilliseconds: Long): Boolean {
        try {
            waitForPresenceOfElement(driver, locator, timeoutInMilliseconds)
            return true // element found
        } catch (e: Exception) {
        }
        return false
    }


    /**
     * tries to set a value for timeoutInMilliseconds
     */
    @Synchronized
    fun setElementValue(
        driver: MobileDriver<*>?,
        element: MobileElement,
        value: String,
        timeoutInMilliseconds: Long
    ): Boolean {
        element.clear()
        element.sendKeys(value)
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        return wait.until(ExpectedCondition<Boolean> {
            val currentElementValue = element.getAttribute("value")
            if (currentElementValue != null && value.equals(currentElementValue))
                return@ExpectedCondition true

            element.clear()
            element.sendKeys(value)
            return@ExpectedCondition false
        })
    }

    /**
     * returns true if element1 is above element2
     */
    @Synchronized
    fun isElementAboveElement(element1: MobileElement, element2: MobileElement): Boolean{
        var element1LocationY: Int = element1.location.y // + element1.size.height
        var element2LocationY: Int = element2.location.y

        if (element2LocationY > element1LocationY)
            return true
        
        return false
    }
}