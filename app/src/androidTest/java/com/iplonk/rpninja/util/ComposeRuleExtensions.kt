package com.iplonk.rpninja.util

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText

fun AndroidComposeTestRule<*, *>.onNodeWithText(@StringRes resId: Int) =
	onNodeWithText(activity.getString(resId))

fun AndroidComposeTestRule<*, *>.onNodeWithContentDescription(@StringRes resId: Int) =
	onNodeWithContentDescription(activity.getString(resId))