# RPNinja: The Calculator with a Black Belt in Efficiency! 🥷💥

## Description
Unleash the power of Reverse Polish Notation with **RPNinja**, the slickest Reverse Polish Notation
(RPN) calculator (soon to be) on the Google Play Store. Say goodbye to the odious order of operations
and those pesky parentheses. With RPNinja you can chain operations, tackle long equations,
and perform high-level calculations with the grace and agility of a black belt warrior.

### Key Features
* **Delete Char Button**: Made a mistake? No problem! Delete a single character with ease—precision at your fingertips.
* **Clear All Button**: One tap and you're back to square one. Start fresh without the clutter.
* **Basic Arithmetic Operators**: Addition, subtraction, multiplication, division, negation, and exponentiation—everything you need for day-to-day calculations.
* **Decimal Point Support**: Handle those tricky decimals with ease. Numbers in any form? No sweat.
* **Last Three Numbers on Stack**: View your last three entered numbers at a glance. Perfect for checking your progress.
* **Dark Mode**: 
* **Minimalist & Fast**: No distractions. Just you and your numbers. Calculate with speed and style.

## Technical Choices (WIP)
Reasoning behind your technical choices, including architectural

## Trade-offs (WIP)

Here are some trade-offs I made for the sake of time.

### Dimension and font size values
In the UI layer I am using the same dp and sp values across screen sizes. While I did test out the
app on the Pixel 9 and the small phone emulators, and it looked good, the values I selected may not
work for every device. Ideally, these would live in `dimens.xml` or a custom dimens file to allow us
to use the appropriate values based on screen size and resolution.

### Locked into portrait mode
In `AndroidManifest.xml` I locked the screen orientation to portrait mode. This is generally not
recommended. From the Android [developer docs](https://developer.android.com/develop/ui/compose/layouts/adaptive/adaptive-dos-and-donts#orientation),
it says
 
> Don't restrict activity orientation. Apps that lock orientation are letterboxed on large screen devices and incompatible window sizes. 
Letterboxed apps are subject to decreased discoverability on Google Play for tablets, foldables, and ChromeOS devices.

If I had more time, I would support landscape mode by rearranging the buttons into more columns to
fit the increased width of the screen, moving the stack to be on the left-hand side of the buttons
while keeping the current position of the input and error messages, and decreasing the size of the
UI elements as needed. I could then toggle between this layout and the portrait mode layout as I
observe orientation changes.

### Few instrumented tests
This app only contains one instrumented UI test that verifies a simple addition can be performed.
Give more time, I would add a few more to cover the essential paths. Additionally, I would create
a model that represents elements on the CalculatorScreen to obscure some of the `onNode...` functions
and make it reusable across UI tests.

### Localization
While most of the strings displayed in the UI are properly stored in `strings.xml`, the decimal
symbol that is displayed as user input is hardcoded within `CalculatorViewModel.kt`. Many languages
use a comma instead of a period to represent a decimal point, so ideally this would be handled
to fully support localization.

### No persistence
There is no persistence of the calculations outside of the ViewModel. In the case of process death,
all of the user's data would be lost. Since the user could potentially store many values on the stack,
ideally, this would be stored in a local database on the device like Room. From there, we could
even add a history feature to that app that would allow the user to look through and interact with
their previous calculations.

## How to run
Your emulator or physical Android mobile device must be running Android 10 or above.

### From clone project
1. Download the latest version of Android Studio. I used Android Studio Ladybug (2024.2.1 Patch 2).
2. Open this project in Android Studio and run.

### From APK
Download the APK located in the top-level directory onto your device.

## Original Take Home Assignment Prompt
https://github.com/snap-mobile/mobile-take-home-exercise