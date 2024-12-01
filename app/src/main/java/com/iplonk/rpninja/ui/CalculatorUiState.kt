package com.iplonk.rpninja.ui

data class CalculatorUiState(
	val currentExpression: String = "",
	val result: ExpressionResult? = null,
)

sealed interface ExpressionResult {
	data class Number(val number: Double) : ExpressionResult
	object DivideByZeroError : ExpressionResult
	object InvalidExpression : ExpressionResult
	object EmptyExpression : ExpressionResult
}
