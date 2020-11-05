# Beagle mobile tests

This project uses [Appium](http://appium.io/) and [Cucumber](https://cucumber.io/) frameworks to run both Android and iOS user interface tests

## Requirements before running

- Appium
- Android SDK and Tools
- iOS emulator

### Global

- Appium server must be running at http://0.0.0.0:4723. Check [this guide](http://appium.io/docs/en/about-appium/getting-started/) to do so
- BFF must be running: ``cd beagle-backend-dir && gradlew bootRun -p automated-tests``

### Android

A different version of the automated-tests app was implemented for this project. The new app has 
a main screen activity that takes a BFF URL and loads the requestd bff end-point. This was necessary because 
Appium can't manipulate or create an app source code in orther to create a custom app. Appium only 
works with already compiled apps.

Ir order to install the new app in the Android image for the tests, run [this project](https://github.com/ZupIT/beagle/tree/feature/igor_appium) pointing to
android/automated-tests module. The emulator will load with the app already installed 

In case the emulator is already set with the new app, it's possible to run the emulator without opening Android Studio.
Example in Windows: ``C:\Users\YOUR-USER\AppData\Local\Android\Sdk\emulator\emulator.exe -avd Pixel_3a_API_30_x86``

The emulator must be running before running the tests
 
### iOS (in progress...)

Not tested yet
  
## Running

``gradle cucumber -Dplatform=android`` (or ios)

