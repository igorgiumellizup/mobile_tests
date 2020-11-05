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
a main screen activity that takes a BFF URL and loads the request page. This was necessary because 
Appium can't manipulate or create an app source code in orther to create a custom app. Appium only 
works with already compiled apps.

- Run the emulator with the new app installed.
Example in Windows: ``C:\Users\YOUR-USER\AppData\Local\Android\Sdk\emulator\emulator.exe -avd Pixel_3a_API_30_x86``

 
### iOS (in progress...)

Not tested yet
  
## Running

``gradle cucumber -Dplatform=android`` (or ios)

