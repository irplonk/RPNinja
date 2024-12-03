package com.iplonk.rpninja.domain

/**
 * Represents all of the available operators that can be applied on two or more numbers
 */
sealed class Operator(val symbol: String) {

	abstract fun apply(operand1: Double, operand2: Double): Double?

	object Divide : Operator("÷") {
		override fun apply(operand1: Double, operand2: Double) =
			if (operand2 != 0.0) operand1 / operand2 else null
	}

	object Multiply : Operator("x") {
		override fun apply(operand1: Double, operand2: Double) = operand1 * operand2
	}

	object Subtract : Operator("-") {
		override fun apply(operand1: Double, operand2: Double) = operand1 - operand2
	}

	object Add : Operator("+") {
		override fun apply(operand1: Double, operand2: Double) = operand1 + operand2
	}
}
