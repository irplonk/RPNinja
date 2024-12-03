package com.iplonk.rpninja.domain

sealed class CalculatorAction(val symbol: String) {
	data class Number(val number: Int) : CalculatorAction(number.toString())
	data class Operation(val operator: Operator) : CalculatorAction(operator.symbol)
	object Decimal : CalculatorAction(".")
	object AllClear : CalculatorAction("AC")
	object DeleteLastCharacter : CalculatorAction("<")
	object Enter : CalculatorAction("=")
}
