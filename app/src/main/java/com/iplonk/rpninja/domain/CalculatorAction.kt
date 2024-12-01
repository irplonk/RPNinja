package com.iplonk.rpninja.domain

sealed interface CalculatorAction {
	data class Number(val number: Int) : CalculatorAction
	data class Operation(val operator: Operator) : CalculatorAction
	object Clear : CalculatorAction
	object Space : CalculatorAction
	object Delete : CalculatorAction
	object Calculate : CalculatorAction
}