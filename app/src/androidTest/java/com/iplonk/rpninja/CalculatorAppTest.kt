package com.iplonk.rpninja

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.iplonk.rpninja.util.onNodeWithContentDescription
import com.iplonk.rpninja.util.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CalculatorAppTest {

	@get:Rule(order = 1)
	val hiltTestRule = HiltAndroidRule(this)

	@get:Rule(order = 2)
	val composeTestRule = createAndroidComposeRule<MainActivity>()

	@Before
	fun init() {
		hiltTestRule.inject()
	}

	@Test
	fun happyPath() {
		// Enter the number three and check that it is displayed
		composeTestRule.onNodeWithText(R.string.three).performClick()
		composeTestRule.onNodeWithContentDescription(R.string.enter_content_description).performClick()
		composeTestRule.onNodeWithText("3.0").assertIsDisplayed()

		// Enter the number four and check that it is displayed
		composeTestRule.onNodeWithText(R.string.four).performClick()
		composeTestRule.onNodeWithContentDescription(R.string.enter_content_description).performClick()
		composeTestRule.onNodeWithText("4.0").assertIsDisplayed()

		// Add and check that the result is display
		composeTestRule.onNodeWithText(R.string.add).performClick()
		composeTestRule.onNodeWithText("7.0").assertIsDisplayed()

		// Also check that previous values no longer exist
		composeTestRule.onNodeWithText("3.0").assertDoesNotExist()
		composeTestRule.onNodeWithText("4.0").assertDoesNotExist()
	}
}
