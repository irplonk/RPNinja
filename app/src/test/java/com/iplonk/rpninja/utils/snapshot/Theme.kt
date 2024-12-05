package com.iplonk.rpninja.utils.snapshot

/**
 * Represents the different color themes that we run our snapshot tests on.
 *
 * While this could be represented by a boolean, the enum is used in the file
 * name for the snapshot tests, which helps with readability, vs. a boolean with
 * no context.
 */
enum class Theme {
	LIGHT,
	DARK;

	val isDarkMode: Boolean
		get() = this == DARK
}