package com.iplonk.rpninja.domain

/**
 * Represents all of the actions that a user can take to interact with the calculator
 */
sealed interface CalculatorAction {
	data class Number(val number: Int) : CalculatorAction
	data class Operation(val operator: Operator) : CalculatorAction
	object Decimal : CalculatorAction
	object AllClear : CalculatorAction
	object DeleteLastCharacter : CalculatorAction
	object Enter : CalculatorAction
}
