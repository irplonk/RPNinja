package com.iplonk.rpninja.ui

import androidx.annotation.StringRes
import com.iplonk.rpninja.R

/**
 * Represents the current state of the UI on the main calculator screen
 *
 * @property stackSnapshot Represents a list of the most recently added items in the stack
 * @property workingNumber Represents the number that the user is currently inputting but has not
 *  been placed onto the stack
 * @property calculatorError Represents any errors to display to the user
 */
data class CalculatorUiState(
	val stackSnapshot: List<Double> = emptyList(),
	val workingNumber: String? = null,
	val calculatorError: CalculatorError? = null,
)

sealed class CalculatorError(@StringRes val message: Int) {
	data object DivideByZero : CalculatorError(R.string.divide_by_zero_error_message)
	data object InsufficientOperands : CalculatorError(R.string.insufficient_operands_message)
	data object UnknownOperation : CalculatorError(R.string.unknown_operation_message)
	data object InvalidNumber : CalculatorError(R.string.invalid_number_message)
}
