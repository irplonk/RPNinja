# RPNinja: The Calculator with a Black Belt in Efficiency! 🥷💥

## Description
Unlock the power of Reverse Polish Notation with **RPNinja**, the sleek RPN calculator
(coming soon to Google Play). Forget the hassle of parentheses and order of operations.
With RPNinja, you can chain operations and solve complex equations effortlessly like a true master.

### Features
- **Keypad**: Everything you need for daily calculations:
  - Decimal point
  - Undo (delete last character)
  - Reset all
  - Basic arithmetic: addition, subtraction, multiplication, division, negation, and exponentiation
- **Last Three Numbers**: View the last three entered numbers at a glance — perfect for tracking progress.
- **Light/Dark Mode**: Switch between themes to suit your style.
- **Minimalist & Fast**: A distraction-free experience designed for speed and efficiency.

## Technical Choices (WIP)
Reasoning behind your technical choices, including architectural

## Trade-offs (WIP)

Here are some trade-offs I made for the sake of time.

### Dimension and font size values
In the UI layer I am using the same dp and sp values across screen sizes. While I did test out the
app on the Pixel 9 and the small phone emulators, the values I selected may not work for every device.
Ideally, these would live in `dimens.xml` or a custom dimens file to allow us to use the appropriate
values based on screen size and resolution.

### Locked into portrait mode
In `AndroidManifest.xml` I locked the screen orientation to portrait mode. This is not recommended.
From the Android [developer docs](https://developer.android.com/develop/ui/compose/layouts/adaptive/adaptive-dos-and-donts#orientation), it says
 
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