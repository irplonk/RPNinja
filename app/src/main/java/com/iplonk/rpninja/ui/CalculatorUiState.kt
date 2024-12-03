package com.iplonk.rpninja.ui

import androidx.annotation.StringRes
import com.iplonk.rpninja.R

data class CalculatorUiState(
	val stackSnapshot: List<Double> = emptyList(),
	val workingNumber: String = "",
	val error: Error? = null,
)

sealed class Error(@StringRes val message: Int) {
	object DivideByZero : Error(R.string.divide_by_zero_error_message)
	object InsufficientOperands : Error(R.string.insufficient_operands_message)
}
