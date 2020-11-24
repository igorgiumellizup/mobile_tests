# Beagle mobile tests

This project uses [Appium](http://appium.io/) and [Cucumber](https://cucumber.io/) frameworks to run both Android and iOS user interface tests

## Requirements before running the tests

- Appium
- Android SDK and Tools
- iOS emulator

### Global

- Appium server must be running at http://0.0.0.0:4723. Check [this guide](http://appium.io/docs/en/about-appium/getting-started/) to do so
- BFF must be running: ``cd beagle-backend-dir && gradlew bootRun -p automated-tests``

### Android

This project uses a different version of the automated-tests app. The new app has 
a main screen activity that takes a BFF URL and loads the requestd bff end-point. This was necessary because 
Appium can't manipulate or create an app source code in orther to create a custom app. Appium only 
works with already compiled apps.

Ir order to install the new app in the Android image for the tests, run [this project](https://github.com/ZupIT/beagle/tree/feature/igor_appium) pointing to
android/automated-tests module. The emulator will load with the app already installed 

In case the emulator is already set with the new app, it's possible to run the emulator without opening Android Studio.
Example in Windows: ``C:\Users\YOUR-USER\AppData\Local\Android\Sdk\emulator\emulator.exe -avd Pixel_3a_API_30_x86``

The emulator must be running before running the tests
 
### iOS (in progress...)

Just like the Android section description and for the same reasons, this project also uses a custom test app for iOS. 
The custom app can be found in the same branch described in the Android section. 
TODO:
 - Explain how to run the iOS emulator with the app already installed
 - Explain how to obtain the iOS app URL that is set up in SuiteSetup.kt
  
## Running

``gradle cucumber -Dplatform=android`` (or ios)


## Debugging

Debugging is possible running Cucumber as a JUnit runner. This method is recommended to avoid breakpoint skipping 
problems during debugging a gradle task.
Refer to ``br.com.zup.beagle.cucumber.steps.Runner`` as the official runner. Right click on it 
and select "Run Runner" (using IntelliJ IDEA). This first run will fail because some params were 
missing. Edit the run plan and add the following to the 'Arguments' section: -Dplatform=android (or ios).
Now, just run \ debug the plan 

## Screenshots on error

Whenever a feature or step fails, a screenshot is generated at build/screenshots

## Limitations

Currently, Appium is not able to:

- get an element's color and font properties (except for Webviews)
- get an element's alignment (LEFT, CENTER etc)
- tell if the keyboard is numeric

 