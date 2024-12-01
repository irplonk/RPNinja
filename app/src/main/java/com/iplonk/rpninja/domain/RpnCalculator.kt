package com.iplonk.rpninja.domain

import com.iplonk.rpninja.ui.ExpressionResult
import javax.inject.Inject


class RpnCalculator @Inject constructor() {

	// TODO: Should I break this up more?
	fun calculate(expression: String, delimiter: Char): ExpressionResult {
		if (expression.isBlank()) return ExpressionResult.EmptyExpression
		val splitExpression = expression.split(delimiter)

		val numberStack: ArrayDeque<Double> = ArrayDeque()

		for (token in splitExpression) {
			val tokenAsDouble = token.toDoubleOrNull()
			if (tokenAsDouble != null) {
				numberStack.add(tokenAsDouble)
			} else {
				val operand2 = numberStack.removeLastOrNull()
				val operand1 = numberStack.removeLastOrNull()
				if (operand1 == null || operand2 == null) {
					return ExpressionResult.InvalidExpression
				}

				val result = when (token) {
					Operator.ADD.symbol -> operand1 + operand2
					Operator.SUBTRACT.symbol -> operand1 - operand2
					Operator.MULTIPLY.symbol -> operand1 * operand2
					Operator.DIVIDE.symbol -> {
						if (operand2 == 0.0) {
							return ExpressionResult.DivideByZeroError
						} else {
							operand1 / operand2
						}
					}

					else -> return ExpressionResult.InvalidExpression
				}
				numberStack.add(result)
			}
		}

		return if (numberStack.size == 1) {
			ExpressionResult.Number(numberStack.first())
		} else {
			ExpressionResult.InvalidExpression
		}
	}
}