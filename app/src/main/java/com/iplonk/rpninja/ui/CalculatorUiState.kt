package com.iplonk.rpninja.ui

data class CalculatorUiState(
	val currentExpression: String = "",
	val result: ExpressionResult? = null,
)

sealed interface ExpressionResult {
	data class Number(val number: String) : ExpressionResult
	object DivideByZeroError : ExpressionResult
}
