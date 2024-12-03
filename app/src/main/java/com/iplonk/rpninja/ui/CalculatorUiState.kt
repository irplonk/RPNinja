package com.iplonk.rpninja.ui

import androidx.annotation.StringRes
import com.iplonk.rpninja.R

data class CalculatorUiState(
	val stackSnapshot: List<Double> = emptyList(),
	val workingNumber: String = "",
	val calculatorError: CalculatorError? = null,
)

sealed class CalculatorError(@StringRes val message: Int) {
	object DivideByZero : CalculatorError(R.string.divide_by_zero_error_message)
	object InsufficientOperands : CalculatorError(R.string.insufficient_operands_message)
	// TODO Add correct error messages here
	object UnknownOperation : CalculatorError(R.string.enter_content_description)
	object InvalidNumber : CalculatorError(R.string.enter_content_description)
}
