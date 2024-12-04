package com.iplonk.rpninja.domain

import com.iplonk.rpninja.domain.Operator.BinaryOperator
import com.iplonk.rpninja.domain.Operator.UnaryOperator
import kotlin.math.pow

/**
 * Represents all of the available operators that can be applied to do mathematical calculations
 */
sealed interface Operator {
	/**
	 * Represents all of the available operators that can be applied on two numbers
	 */
	interface BinaryOperator : Operator {
		fun apply(operand1: Double, operand2: Double): Double?
	}

	/**
	 * Represents all of the available operators that can be applied on one number
	 */
	interface UnaryOperator : Operator {
		fun apply(operand1: Double): Double
	}

	data object Divide : BinaryOperator {
		override fun apply(operand1: Double, operand2: Double) =
			if (operand2 != 0.0) operand1 / operand2 else null
	}

	data object Multiply : BinaryOperator {
		override fun apply(operand1: Double, operand2: Double) = operand1 * operand2
	}

	data object Subtract : BinaryOperator {
		override fun apply(operand1: Double, operand2: Double) = operand1 - operand2
	}

	data object Add : BinaryOperator {
		override fun apply(operand1: Double, operand2: Double) = operand1 + operand2
	}

	data object Negate : UnaryOperator {
		override fun apply(operand1: Double) = -operand1
	}

	data object Exponentiate : BinaryOperator {
		override fun apply(operand1: Double, operand2: Double) = operand1.pow(operand2)
	}
}
