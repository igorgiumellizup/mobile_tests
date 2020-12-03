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

package br.com.zup.beagle.setup

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

object SuiteSetup {

    val ERROR_SCREENSHOTS_FOLDER = "./build/screenshots"
    private var platform: String? = null
    private var driver: AppiumDriver<*>? = null

    //private var service: AppiumDriverLocalService? = null
    private var bffBaseUrl: String? = null


    fun getDriver(): AppiumDriver<*> {

        if (driver == null)
            throw Exception("Test suite not initialized correctly!")

        return driver!!
    }

    fun isAndroid(): Boolean {
        return platform.equals("android", ignoreCase = true)
    }

    fun isIos(): Boolean {
        return platform.equals("ios", ignoreCase = true)
    }

    fun getBffBaseUrl(): String {
        return bffBaseUrl!!
    }

    fun initSuit() {

        //startAppiumServer()

        if (platform == null) {
            platform = System.getProperty("platform") // command-line argument
        }

        println("#### Initializing suite setup with platform $platform...")

        if (bffBaseUrl == null) {
            if (isAndroid())
                bffBaseUrl = "http://10.0.2.2:8080"
            else
                bffBaseUrl = "http://localhost:8080"
        }

        val capabilities = DesiredCapabilities()

        if (isAndroid()) {
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11")
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_3a_API_30_x86")
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2")
            // MobileCapabilityType.APP capability is not required for Android if you specify appPackage and appActivity
            // In this case, the Android image already contains the app
            capabilities.setCapability("appPackage", "br.com.zup.beagle.automatedTests")
            capabilities.setCapability("appActivity", ".activity.MainActivity")

            driver = AndroidDriver<MobileElement>(/*service?.url*/URL(APPIUM_URL), capabilities)
            //driver?.launchApp()
        } else {
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS")
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.4")
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11")
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest")
            capabilities.setCapability("app", "/Users/luisgustavooliveirasilva/Library/Developer/Xcode/DerivedData/Beagle-gnqdhkpaxlbwgnbcpaltnxvwyeum/Build/Products/Debug-iphonesimulator/AutomatedTests.app")
            capabilities.setCapability("waitForQuiescence", false)

            driver = IOSDriver<MobileElement>(URL(APPIUM_URL),capabilities)
        }
    }

    fun resetApp() {
        try {
            driver?.resetApp();
        } catch (e: Exception) {
            println("ERROR resetting app: ${e.message}")
        }
    }

    fun closeDriver() {
        try {
            driver?.closeApp();
        } catch (e: Exception) {
            println("ERROR closing app: ${e.message}")
        } finally {
            driver?.quit()
        }
    }

    /*
    @Throws(IOException::class)
    private fun startAppiumServer() {
        val serviceBuilder = AppiumServiceBuilder()
        //serviceBuilder.usingDriverExecutable(File("/path/to/node/executable"));
        //serviceBuilder.withAppiumJS(new File("/path/to/appium"));
        //service = AppiumDriverLocalService.buildService(serviceBuilder);
        service = AppiumDriverLocalService.buildDefaultService()
        service?.start()
    }
    */


}