# RPNinja: The Calculator with a Black Belt in Efficiency! 🥷💥

## Description
Unleash the power of Reverse Polish Notation with **RPNinja**, the slickest Reverse Polish Notation
(RPN) calculator (soon to be) on the Google Play Store. Say goodbye to the odious order of operations
and those pesky parentheses. With RPNinja you can chain operations, tackle long equations,
and perform high-level calculations with the grace and agility of a black belt warrior.

### Key Features
* **Delete Char Button**: Made a mistake? No problem! Delete a single character with ease—precision at your fingertips.
* **Clear All Button**: One tap and you're back to square one. Start fresh without the clutter.
* **Basic Arithmetic Operators**: Addition, subtraction, multiplication, and division—everything you need for day-to-day calculations.
* **Decimal Point Support**: Handle those tricky decimals with ease. Numbers in any form? No sweat.
* **Last Three Numbers on Stack**: View your last three entered numbers at a glance. Perfect for checking your progress.
* **Minimalist & Fast**: No distractions. Just you and your numbers. Calculate with speed and style.

## Technical Choices (WIP)
Reasoning behind your technical choices, including architectural

## Trade-offs (WIP)

### Localization
While the error messages and a few other strings are properly stored in strings.xml, the symbols
used on the calculator are hardcoded within `CalculorButtons.kt`. I did this for the sake of time
and simplicity since solving for this would include either
* Creating a wrapper class for string resources to inject into the ViewModel for safe access
* Translating between a model that represents the operands and the string resources in the UI layer

Additionally, it felt less important to solve for this during this first iteration since, after
checking the Google calculator app with a few different languages, I only noticed that sometimes
a comma is used in place of a period for the decimal point while the other symbols remain the same.

## How to run
Your emulator or physical Android mobile device must be running Android 10 or above.

### From clone project
1. Download the latest version of Android Studio. I used Android Studio Ladybug (2024.2.1 Patch 2).
2. Open this project in Android Studio and run.

### From APK
Download the APK located in the top-level directory onto your device.

## Take Home Prompt
https://github.com/snap-mobile/mobile-take-home-exercise