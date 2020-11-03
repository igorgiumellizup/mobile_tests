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

import br.com.zup.beagle.cucumber.elements.BUTTON_SCREEN_HEADER
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By

class ButtonScreen(mobileDriver: AppiumDriver<*>?) : AbstractScreen(mobileDriver) {

    override fun <T : AbstractScreen> waitForScreenToLoad(): T {
        waitForScreenToLoad(arrayOf(By.xpath("//*[contains(@text,'$BUTTON_SCREEN_HEADER')]")))
        return this as T
    }
}