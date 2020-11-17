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
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.AndroidTouchAction
import io.appium.java_client.functions.ExpectedCondition
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import org.openqa.selenium.*
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import java.time.Duration
import java.util.*


object AppiumUtil {

    @Synchronized
    fun scrollToElement(
        driver: MobileDriver<*>,
        element: MobileElement,
        direction: SwipeDirection,
        timeoutInMilliseconds: Long
    ): MobileElement {

        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        return wait.until(ExpectedCondition<MobileElement> {
            if (!element.isDisplayed) {
                swipeScreenTo(driver, direction)
                return@ExpectedCondition null;
            } else
                return@ExpectedCondition element
        })


        //val action = TouchActions(driver)
        //action.scroll(element, 10, 100)
        //action.perform()

        //val actions = Actions(driver)
        // actions.

        //var androidActions = AndroidTouchAction(driver)
        // androidActions.

        /*
        val scrollObject = HashMap<String, String>()
        scrollObject["direction"] = "$direction"
        scrollObject["element"] = (element as RemoteWebElement).id
        print(scrollObject)
        (driver as JavascriptExecutor).executeScript("mobile: scroll", scrollObject)
         */
    }

    @Synchronized
    fun swipeScreenTo(driver: MobileDriver<*>, swipeDirection: SwipeDirection) {
        if (driver is AndroidDriver<*>)
            androidSwipeScreenTo(driver, swipeDirection)
        else
            iosSwipeScreenTo(driver, swipeDirection)
    }

    /**
     *
     * Performs swipe from the center of screen
     * Adapted from http://appium.io/docs/en/writing-running-appium/tutorial/swipe/ios-mobile-screen/
     */
    private fun iosSwipeScreenTo(driver: MobileDriver<*>, swipeDirection: SwipeDirection) {

        val ANIMATION_TIME = 200 // ms
        val scrollObject = HashMap<String, String>()
        when (swipeDirection) {
            SwipeDirection.DOWN -> scrollObject["direction"] = "down"
            SwipeDirection.UP -> scrollObject["direction"] = "up"
            SwipeDirection.LEFT -> scrollObject["direction"] = "left"
            SwipeDirection.RIGHT -> scrollObject["direction"] = "right"
            else -> throw IllegalArgumentException("mobileSwipeScreenIOS(): dir: '$swipeDirection' NOT supported")
        }

        (driver as JavascriptExecutor).executeScript("mobile:swipe", scrollObject)
        //driver.execute("mobile:swipe", scrollObject)

        Thread.sleep(ANIMATION_TIME.toLong()) // always allow swipe action to complete
    }


    /**
     *
     * Performs swipe from the center of screen
     * Adapted from http://appium.io/docs/en/writing-running-appium/tutorial/swipe/simple-screen/
     */
    private fun androidSwipeScreenTo(driver: MobileDriver<*>, swipeDirection: SwipeDirection) {

        val ANIMATION_TIME = 200 // ms
        val PRESS_TIME = 200 // ms
        val EDGE_BORDER = 10 // better avoid edges
        val pointOptionStart: PointOption<*>
        val pointOptionEnd: PointOption<*>

        // init screen variables
        val dims: Dimension = driver.manage().window().getSize()

        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2)
        pointOptionEnd = when (swipeDirection) {
            SwipeDirection.DOWN -> PointOption.point(dims.width / 2, dims.height - EDGE_BORDER)
            SwipeDirection.UP -> PointOption.point(dims.width / 2, EDGE_BORDER)
            SwipeDirection.LEFT -> PointOption.point(EDGE_BORDER, dims.height / 2)
            SwipeDirection.RIGHT -> PointOption.point(dims.width - EDGE_BORDER, dims.height / 2)
            else -> throw IllegalArgumentException("swipeScreen(): dir: '$swipeDirection' NOT supported")
        }

        // execute swipe using TouchAction
        AndroidTouchAction(driver)
            .press(pointOptionStart) // a bit more reliable when we add small wait
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME.toLong())))
            .moveTo(pointOptionEnd)
            .release().perform()


        Thread.sleep(ANIMATION_TIME.toLong())

    }

    @Synchronized
    fun waitForPresenceOfElement(driver: MobileDriver<*>, locator: By, timeoutInMilliseconds: Long): MobileElement {
        val wait: FluentWait<MobileDriver<*>> = FluentWait<MobileDriver<*>>(driver)
        wait.pollingEvery(Duration.ofMillis(500))
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        wait.ignoring(NoSuchElementException::class.java)
        wait.ignoring(StaleElementReferenceException::class.java)
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator)) as MobileElement
    }

    @Synchronized
    fun waitForElementToBeClickable(
        driver: MobileDriver<*>,
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
        driver: MobileDriver<*>,
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
        driver: MobileDriver<*>,
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
        driver: MobileDriver<*>,
        elements: Array<MobileElement>,
        timeoutInMilliseconds: Long
    ) {
        for (element in elements) {
            waitForElementToBeClickable(driver, element, timeoutInMilliseconds)
        }
    }

    @Synchronized
    fun waitForElementsToBeClickable(
        driver: MobileDriver<*>,
        locators: Array<By>,
        timeoutInMilliseconds: Long
    ) {
        for (locator in locators) {
            waitForElementToBeClickable(driver, locator, timeoutInMilliseconds, 400)
        }
    }


    @Synchronized
    fun waitForElementTextToBe(
        driver: MobileDriver<*>, element: MobileElement, text: String,
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
        driver: MobileDriver<*>, element: MobileElement, attribute: String, value: String,
        timeoutInMilliseconds: Long
    ): Boolean {
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        return wait.until {
            element.getAttribute(attribute) != null && element.getAttribute(attribute) == value
        }
    }


    @Synchronized
    fun waitForInvisibilityOf(driver: MobileDriver<*>, locator: By, timeoutInMilliseconds: Long): Boolean {
        val wait = FluentWait(driver)
        wait.withTimeout(Duration.ofMillis(timeoutInMilliseconds))
        wait.pollingEvery(Duration.ofMillis(500))
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator))
    }

    @Synchronized
    fun waitForInvisibilityOf(
        driver: MobileDriver<*>,
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
    fun elementExists(driver: MobileDriver<*>, locator: By, timeoutInMilliseconds: Long): Boolean {
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
        driver: MobileDriver<*>,
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
    fun isElementAboveElement(element1: MobileElement, element2: MobileElement): Boolean {
        var element1LocationY: Int = element1.location.y // + element1.size.height
        var element2LocationY: Int = element2.location.y

        if (element2LocationY > element1LocationY)
            return true

        return false
    }

    @Synchronized
    fun getPropertyXpath(property: String, propertyValue: String, likeSearch: Boolean, ignoreCase: Boolean): By {
        val xpath: By

        if (ignoreCase) {
            if (likeSearch) {
                xpath =
                    By.xpath("//*[contains(translate(@$property, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),\"${propertyValue?.toLowerCase()}\")]")
            } else {
                xpath =
                    By.xpath("//*[translate(@$property, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')=\"${propertyValue?.toLowerCase()}\"]")
            }
        } else {
            if (likeSearch) {
                xpath = By.xpath("//*[contains(@$property,\"$propertyValue\")]")

            } else {
                xpath = By.xpath("//*[@$property=\"$propertyValue\"]")
            }
        }

        return xpath
    }
}